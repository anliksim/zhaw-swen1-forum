package ch.zhaw.swen1.forum.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

 

import org.junit.Before;
import org.junit.Test;

/**
 * Combined tests for forum domain logic. Because domain classes are quite simple, 
 * only integration tests have been written. 
  *
 */
public class ForumTest {
	private Forum forum;
	 	

	@Before
	public void setUp() throws Exception {
		// TODO create forum instance
		 	
	}

	@Test
	public void addTopicToForumAndAssertIt() {
		forum.addTopic("Topic", "Description");
		assertEquals(1, forum.getTopics().size());
		Topic topic = forum.getTopics().get(0); 
		assertEquals("Topic", topic.getName());
		assertEquals("Description", topic.getDescription());
	}

	@Test(expected=RuntimeException.class)
	public void addTopicWithDuplicateNameToForumAndAssertException() {
		forum.addTopic("Topic", "Description");
		assertEquals(1, forum.getTopics().size());
		forum.addTopic("Topic", "Description");
	}

	@Test
	public void addDiscussionToTopicAndCountIt(){
		Topic topic = forum.addTopic("Topic", "Description");
		Discussion discussion = topic.addDiscussion("Discussion");
		assertEquals(1, topic.getDiscussions().size());
		assertEquals("Discussion", discussion.getName());
	}
	
	@Test(expected=RuntimeException.class)
	public void addDiscussionToTopicWithDuplicateNameAndAssertException(){
		Topic topic = forum.addTopic("Topic", "Description");
		topic.addDiscussion("Discussion");
		topic.addDiscussion("Discussion");
	}
	
	@Test
	public void addContributionToDiscussionAndCountIt(){
		// user, topic and discussion must be created first
		User user = forum.addUser("User", new byte[]{0});
		Topic topic = forum.addTopic("Topic", "Description");
		Discussion discussion = topic.addDiscussion("Discussion");
		
		// add contribution to discussion
		// TODO 
				
		// assert newly added contribution
		assertEquals(1, discussion.getContributions().size());
		Contribution contribution = discussion.getContributions().get(0);
		assertNotNull(contribution);
		assertEquals("Content", contribution.getContent());
		assertSame(user, contribution.getUser());
		 
		
		// assert total number of contributions in forum
		// TODO 
	}
	
	@Test
	public void addUserContributionAndAssertIt(){
		// TODO 
	}

	@Test
	public void getUserForNotExistingNameAndAssertIt(){
		assertNull(forum.getUserForName("NotExisting"));
	}

}