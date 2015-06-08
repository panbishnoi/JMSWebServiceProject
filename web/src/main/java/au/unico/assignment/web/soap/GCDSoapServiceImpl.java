package au.unico.assignment.web.soap;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jws.WebService;

import au.unico.assignment.service.GCDService;
/**
 * JAX-WS service implementation for GCDSoapService
 * @author Pankaj Bishnoi
 *
 */
@WebService(serviceName = "GCDJAXWSService", portName = "GCDService", endpointInterface = "au.unico.assignment.web.soap.GCDSoapService", targetNamespace = "http://www.unico.com/gcd")
public class GCDSoapServiceImpl implements GCDSoapService {

	@Inject
	private GCDService gcdService;

	@Override
	public int gcd() {

		try {
			return gcdService.gcd();
		} catch (JMSException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<Integer> gcdList() {
		return gcdService.gcdList();
	}

	@Override
	public int gcdSum() {
		return gcdService.gcdSum();
	}

}
