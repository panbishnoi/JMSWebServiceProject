package au.unico.assignment.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.naming.InitialContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import au.unico.assignment.dao.GreatestCommonDivisorDao;
import au.unico.assignment.dao.EnteredNumberDao;
import au.unico.assignment.model.GreatestCommonDivisor;
import au.unico.assignment.model.EnteredNumber;

/**
 * Service processing numbers to calculate the greatest common divisor.
 * @author Pankaj Bishnoi
 *
 */
@Stateless
public class GCDService {

	ConnectionFactory connectionFactory;

	private InitialContext initialContext;

	@Inject
	private EnteredNumberDao numberDao;

	@Inject
	private GreatestCommonDivisorDao greatestCommonDivisorDao;

	@PostConstruct
	public void initialize() {

		try {
                        this.initialContext  = new InitialContext();
                        connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL); 
                } catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@PreDestroy
	public void destroyBean() {
		try {
                    numberDao = null;
                    greatestCommonDivisorDao = null;	
                    initialContext.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Push numbers to the JMS queue.
	 * @param numbers - List of Integer numbers received to the server for processing in their perspective order. 
	 * @throws JMSException - if integers received can't be pushed in to the JMS Queue.
	 */
	public void pushNumbersToJMSQueue(List<Integer> numbers)
			throws JMSException {
                Connection connectionProducer = null;
	        Session sessionProducer = null;
                MessageProducer producer =  null;
                try{        
                 connectionProducer = connectionFactory.createConnection();          
                 connectionProducer.start();  
                 sessionProducer = connectionProducer.createSession(false, Session.AUTO_ACKNOWLEDGE);  
		for (Integer i : numbers) {
			TextMessage message = sessionProducer.createTextMessage(String
					.valueOf(i.intValue()));
                Destination destination = sessionProducer.createQueue("gcdQueue");             
                producer = sessionProducer.createProducer(destination);       
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);            
                producer.send(message); 
                producer.close();
                numberDao.saveNumber(i);
                }
                }catch(Exception ex){
                       throw new JMSException(ex.getMessage());
                }finally{
                       if(sessionProducer != null)
                        sessionProducer.close(); 
                       if(connectionProducer != null)
                        connectionProducer.close();
                 }
	}

        
        /**
	 * Pop 2 numbers from JMS queue. 
         * @return returns messages from gcd queue 
	 * @throws JMSException - if integers received can't be pushed in to the JMS Queue.
	 */
	public List<Message> popNumberFromJMSQueue()
			throws JMSException {
                List<Message> messageList = new ArrayList<Message>();
                Connection connectionConsumer = null;
                Session sessionConsumer = null;
                MessageConsumer messageConsumer = null;
                try{        
                    connectionConsumer = connectionFactory.createConnection();          
                    sessionConsumer = connectionConsumer.createSession(true, Session.AUTO_ACKNOWLEDGE);  
                    Destination gcdQueue = sessionConsumer.createQueue("gcdQueue");
                    connectionConsumer.start();  
                    messageConsumer = sessionConsumer.createConsumer(gcdQueue);
                    messageList.add(messageConsumer.receive(2000));
                    messageList.add(messageConsumer.receive(2000));
                    return messageList;
                } catch(Exception ex){
                    throw new JMSException(ex.getMessage());
                }finally{
                       if(messageConsumer!=null)
                           messageConsumer.close();
                       if(sessionConsumer != null){
                           sessionConsumer.commit();
                           sessionConsumer.close(); 
                       }
                       if(connectionConsumer != null)
                           connectionConsumer.close();
                 }
	}

        
	/**
	 * Returns a List of Integers successfully submitted to the JMS queue
	 * @return List of integers successfully added to the JMS queue.
	 */
	public List<Integer> getNumbersSuccesfullyAddedToJMSQueue() {

		List<EnteredNumber> numbers = numberDao.findAll();

		List<Integer> submitedNumbers = new ArrayList<Integer>();

		for (EnteredNumber n : numbers) {
			submitedNumbers.add(n.getIntegerValue());
		}

		return submitedNumbers;
	}

	/**
	 * Returns greatest common divisor number calculated of the first two numbers in line JMS queue.
	 * When this operation returns the two numbers are removed from the queue.
	 * @return Greatest common divisor calculated of the first two numbers of the JMS queue.
	 * @throws JMSException
	 */
	public int gcd() throws JMSException {

		int gcd = -1;

		List<Message> msgList = popNumberFromJMSQueue();
                if(msgList.size() < 2)
                {
                    throw new JMSException("Please input numbers to GCD Queue for GCD calculation");
                }
		if (msgList.size() == 2) {

			TextMessage message1 = (TextMessage) msgList.get(0);
			TextMessage message2 = (TextMessage) msgList.get(1);

			BigInteger number1 = BigInteger
					.valueOf(Integer.parseInt(message1.getText()));

			BigInteger number2 = BigInteger
					.valueOf(Integer.parseInt(message2.getText()));

			gcd = number1.gcd(number2).intValue();

			greatestCommonDivisorDao.saveGCD(gcd);

		}

		return gcd;

	}

	/**
	 * Returns the greatest common divisor numbers list ever calculated.
	 * @return List of greatest common divisor numbers ever calculated.
	 */
	public List<Integer> gcdList() {

		List<Integer> gcdNumbers = new ArrayList<Integer>();

		List<GreatestCommonDivisor> gcds = greatestCommonDivisorDao.findAll();

		for (GreatestCommonDivisor gcd : gcds) {
			gcdNumbers.add(gcd.getComputedGCD());
		}

		return gcdNumbers;
	}

	/**
	 * Calculates the integer sum of greatest common divisor numbers ever calculated.
	 * @return sum of all greatest common divisor numbers ever calculated
	 */
	public int gcdSum() {
		int sum =0;
		List<GreatestCommonDivisor> gcds = greatestCommonDivisorDao.findAll();
		for (GreatestCommonDivisor gcd : gcds) {
			sum = sum + gcd.getComputedGCD();
		}
		return sum;
	}
}
