package com.capgemini.integration.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class Producer {
 
	public static void main(String args[]) {
		/*if(args.length==0)
		{
			System.out.println("Must suuply a message");
			System.out.println("HelloWorld");
			return;
		}
		else {
			System.out.println(args[0]);
		}*/
		//Creating connectionFactory object
		ConnectionFactory connectionFactory;
		//creating connection object
		Connection connection=null;
		
		try {
			//initialcontext-for naming and setting property
			InitialContext initialContext=new InitialContext();
			//creationg queue object pointing to jms/P2PQueue in glassfish server
			Queue queue=(Queue)initialContext.lookup("jms/P2PQueue");
			
			//fteching connectionfactory from glassfish server default connectionfactory
					connectionFactory=(QueueConnectionFactory)initialContext.lookup("jms/__defaultConnectionFactory");
					//getting connection from connection factory 
						connection=connectionFactory.createConnection();
						//creationg session object by help of connection object
					Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
					//creating message producer from session
				MessageProducer messageProducer= session.createProducer(queue);
				//creati`ng text message from session
				TextMessage textMessage= session.createTextMessage("sameera");
				messageProducer.send(textMessage);
				System.out.println("Message produced");
		}
					catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection!=null)
			try {
				connection.close();
			}
			catch(JMSException e)
			{
				e.printStackTrace();
			}
		}
	}
}
