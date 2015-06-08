package au.unico.assignment.web.restful;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import au.unico.assignment.service.GCDService;
/**
 * JAX-RS web service exposing methods for GCD rest operations
 * @author Pankaj Bishnoi
 *
 */
@Path("/")
public class GCDRestResource {

	private static final String INTEGER1 = "integer1";

	private static final String INTEGER2 = "integer2";

	@Inject
	private GCDService gcdService;

	@POST
	@Path("push/{integer1}/{integer2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String push(@PathParam(INTEGER1) int integer1,
			@PathParam(INTEGER2) int integer2) {
		List<Integer> values = new ArrayList<Integer>();
		values.add(integer1);
		values.add(integer2);
		try {
			gcdService.pushNumbersToJMSQueue(values);
		} catch (JMSException e) {
			return "Error in submiting numbers for GCD calculation";
		}

		return "Success";
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Integer> list() {
		return gcdService.getNumbersSuccesfullyAddedToJMSQueue();
	}
}
