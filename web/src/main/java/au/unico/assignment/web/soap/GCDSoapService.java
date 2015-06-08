package au.unico.assignment.web.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
/**
 * JAX-WS web service to calculate and retrieve greatest common divisor.
 * @author Pankaj Bishnoi
 *
 */
@WebService(targetNamespace = "http://www.unico.com/java/assignment/jaxws/gcd")
public interface GCDSoapService {

	/**
	 * Returns the greatest common divisor number calculated from the 
	 * first two elements in the JMS queue.
	 * @return greatest common divisor number calculated form the internal JMS queue.
	 */
	@WebMethod
	public int gcd();

	/**
	 * Returns the list of common divisor number values ever calculated.
	 * @return List of greatest common divisor numbers ever calculated.
	 */
	@WebMethod
	public List<Integer> gcdList();

	/**
	 * Returns the sum of all greatest common divisor numbers ever calculated.
	 * @return sum of all greatest common divisor numbers ever calculated.
	 */
	@WebMethod
	public int gcdSum();

}
