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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageTopicSender {

	public static void main(String[] args) throws NamingException, JMSException {
		
		//  1) Set the properties and looked up for connection factory
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
		prop.put(Context.PROVIDER_URL,"t3://localhost:7001");
		
		Context context = new InitialContext(prop);
		
		TopicConnectionFactory factory =  (TopicConnectionFactory) context.lookup("com/my/myConnectionFactory");
		System.out.println("Successfully Lookup Connection Factory");
		
		//2. Creating queue connection
		TopicConnection connection = factory.createTopicConnection();
		
		connection.start();
		
		//3. creating queue and session
		Topic topic = (Topic) context.lookup("com/my/myTopic");
		TopicSession session = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		
		System.out.println("Success Looked up topic as well");
		
		TopicPublisher topicPublisher = session.createPublisher(topic);
		topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("Hi All , Message from Guest -- There is holiday tomorrow on account of Dipawali");
		
		topicPublisher.publish(textMessage);
		
		System.out.println("Message Published....");
	}

}
