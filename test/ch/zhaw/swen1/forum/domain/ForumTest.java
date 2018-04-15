package ch.zhaw.swen1.forum.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;


import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Combined tests for forum domain logic. Because domain classes are quite simple,
 * only integration tests have been written.
 */
public class ForumTest {
    private Forum forum;


    @Before
    public void setUp() {
        forum = new Forum();
    }

    @Test
    public void addTopicToForumAndAssertIt() {
        forum.addTopic("Topic", "Description");
        assertEquals(1, forum.getTopics().size());
        Topic topic = forum.getTopics().get(0);
        assertEquals("Topic", topic.getName());
        assertEquals("Description", topic.getDescription());
    }

    @Test(expected = RuntimeException.class)
    public void addTopicWithDuplicateNameToForumAndAssertException() {
        forum.addTopic("Topic", "Description");
        assertEquals(1, forum.getTopics().size());
        forum.addTopic("Topic", "Description");
    }

    @Test
    public void addDiscussionToTopicAndCountIt() {
        Topic topic = forum.addTopic("Topic", "Description");
        Discussion discussion = topic.addDiscussion("Discussion");
        assertEquals(1, topic.getDiscussions().size());
        assertEquals("Discussion", discussion.getName());
    }

    @Test(expected = RuntimeException.class)
    public void addDiscussionToTopicWithDuplicateNameAndAssertException() {
        Topic topic = forum.addTopic("Topic", "Description");
        topic.addDiscussion("Discussion");
        topic.addDiscussion("Discussion");
    }

    @Test
    public void addContributionToDiscussionAndCountIt() {
        // user, topic and discussion must be created first
        User user = forum.addUser("User", new byte[]{0});
        Topic topic = forum.addTopic("Topic", "Description");
        Discussion discussion = topic.addDiscussion("Discussion");

        // add contribution to discussion
        discussion.addContribution(new Contribution("Content", user, Instant.now()));

        // assert newly added contribution
        assertEquals(1, discussion.getContributions().size());
        Contribution contribution = discussion.getContributions().get(0);
        assertNotNull(contribution);
        assertEquals("Content", contribution.getContent());
        assertSame(user, contribution.getUser());

        // assert total number of contributions in forum
        int totalContributions = forum.getUsers().stream()
                .map(User::getName)
                .mapToInt(forum::getNbrOfContributionsPerUser)
                .sum();
        assertEquals(1, totalContributions);
    }

    @Test(expected = IllegalStateException.class)
    public void addUserContributionWhenNotAuthenticated() {
        User user = forum.addUser("User", "secret".getBytes());
        forum.addUserContribution(user.getName(), new byte[]{0}, "any", "any", "any");
    }

    @Test
    public void addUserContributionAndAssertIt() {
        byte[] hash = "secret".getBytes();
        User user = forum.addUser("User", hash);
        Topic topic = forum.addTopic("Topic", "Description");

        // add contribution
        forum.addUserContribution(user.getName(), hash, topic.getName(), "Discussion", "Content");

        // assert it
        assertEquals(1, topic.getDiscussions().size());
        assertNotNull(topic.getDiscussionForName("Discussion"));
        assertEquals(1, user.getNbrOfContributions());
        Contribution contribution = user.getContributions().get(0);
        assertEquals("Content", contribution.getContent());
    }

    @Test
    public void getUserForNotExistingNameAndAssertIt() {
        assertNull(forum.getUserForName("NotExisting"));
    }

}