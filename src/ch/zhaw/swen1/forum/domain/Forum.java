package ch.zhaw.swen1.forum.domain;

 
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the whole forum with its list of topics and users
 *
 */
public class Forum {
	 
	private List<User> users = new ArrayList<>();
	private List<Topic> topics = new ArrayList<>();

	 

	public List<User> getUsers() {
		return users;
	}
	
	public List<Topic> getTopics() {
		return topics;
	}

	 
	
	/**
	 * Creates and adds a new topic with the specified attributes. 
	 */
	public Topic addTopic(String name, String description){
		if (name == null){
			throw new RuntimeException("Name must be not null");
		}
		Topic existingTopic = getTopicForName(name);
		if (existingTopic != null){
			throw new RuntimeException("A topic with the name " + name + " already exists");
		}
		Topic topic = new Topic(name, description);
		topics.add(topic);
		return topic;
	}
	
	/**
	 * Returns the topic with the specified name or null if there is no such topic. 
	 */
	protected Topic getTopicForName(String name){
		for(Topic topic : topics){
			if (topic.getName().equals(name)){
				return topic;
			}
		}
		return null;
	}
	
	/**
	 * Creates and adds a new user with the specified attributes. 
	 */
	public User addUser(String name, byte[] passwordHash){
		if (name == null){
			throw new RuntimeException("Name must be not null");
		}
		User existingUser = getUserForName(name);
		if (existingUser != null){
			throw new RuntimeException("A topic with the name " + name + " already exists");
		}
		User user = new User(name, passwordHash);
		users.add(user);
		return user;
	}
	
	protected User getUserForName(String name){
		for(User user : users){
			if (user.getName().equals(name)){
				return user;
			}
		}
		return null;
	}
}