package com.lnt.training.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageSender {

	public static void main(String[] args) throws NamingException, JMSException {

		// 1) Set the properties and looked up for connection factory
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		props.setProperty("java.naming.provider.url", "localhost:1099");

		Context context = new InitialContext(props);

		QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		System.out.println("Successfully Lookup happened");

		// 2. Creating queue connection
		QueueConnection connection = factory.createQueueConnection();

		connection.start();

		// 3. creating queue and session
		Queue queue = (Queue) context.lookup("/queue/MyQueue");
		QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueSender sender = session.createSender(queue);

		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("###Hello from JBOSS CLIENT###");
		// send the message
		sender.send(textMessage);
		System.out.println("Message sent");

	}

}
