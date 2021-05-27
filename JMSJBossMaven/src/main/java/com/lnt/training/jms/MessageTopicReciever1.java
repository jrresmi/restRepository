package com.lnt.training.jms;

import java.util.Properties;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageTopicReciever1 {

	public static void main(String[] args) throws NamingException, JMSException {
		
		//  1) Set the properties and looked up for connection factory
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		
		Context context = new InitialContext(props);
		
		TopicConnectionFactory factory =  (TopicConnectionFactory) context.lookup("ConnectionFactory");
		System.out.println("Successfully Lookup Connection Factory");
		
		//2. Creating queue connection
		TopicConnection connection = factory.createTopicConnection();
		
		System.out.println("Reciever 1 started ....");
		connection.start();
		
		//3. creating topic and session
		Topic topic = (Topic) context.lookup("/topic/MyTopic");
		TopicSession session = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		
		System.out.println("Success Looked up topic as well");
		
		TopicSubscriber topicSubscriber = session.createSubscriber(topic);
		
		TextMessage textMessage = (TextMessage) topicSubscriber.receive();
		
		
		System.out.println("Reciever 1 recieved :"+textMessage.getText());
	}

}
