Application - unicoproject

Environment:
-------------
JDK / JRE 1.7.0_75
Java EE 6 SDK 
GlassFish Server 4.1
Derby DB 10.8.3.2
apache-activemq-5.10.0 
SoapUI 5.1.3
Deployment steps:
--------------
1. Execute the DB scripts given below on the Derby database:

CREATE TABLE ENTEREDNUMBERS (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,INTEGERVALUE INTEGER);
CREATE TABLE GCD (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, COMPUTED_GCD INTEGER);

2. For JMS Queue i have used ActiveMQ, ActiveMQ ™ is the most popular and powerful open source messaging and Integration Patterns server. Download binary release for apache-activemq-5.10.0 from http://activemq.apache.org/download.html and extract in local desktop. Navigate to activemq install directory 
and start activemq. Refer steps given at http://activemq.apache.org/getting-started.html#GettingStarted-StartingActiveMQ

Activemq admin console url is http://127.0.0.1:8161/admin/

Create a new queue on activemq with name "gcdQueue"

2. Deploy the ear on the Glassfish container.

	
2. Database JDBC Resource: 
	JNDI name - jdbc/__default


ASSUMPTIONS:
------------
Input arguments to Rest web service are integers
	
URL's For Accessing the Application:
-------------------------------------
REST Services:
1. Push Operation:
http://<your_IP>:<your_port>/unicoweb/push/1/2 

Here 1 and 2 are example integer values that would be added to the queue

2. Listing all the integers ever added to the queue in order:
http://<your_IP>:<your_port>/unicoweb/ 


SOAP Services:

The WSDL location is:
http://<your_IP>:<your_port>/unicoweb/GCDJAXWSService?wsdl  

These services can also be tested by using the URL:
http://<your_IP>:<your_port>/unicoweb/GCDJAXWSService?Tester 

NOTE:
---------

To validate queue messages go to ActiveMq admin console by clicking on below link.

http://localhost:8161/admin/queues.jsp


Testing:
---------
Testing of Rest and Soap operations has been done using SOAPUI tool. Load testing with 20 users also has been completed and successful.



