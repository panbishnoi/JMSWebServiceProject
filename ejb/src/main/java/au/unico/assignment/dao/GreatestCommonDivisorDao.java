package au.unico.assignment.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import au.unico.assignment.model.GreatestCommonDivisor;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
/**
 * Dao class to save and retrieve greatest common divisor numbers previously calculated.  
 * @author Pankaj Bishnoi
 *
 */
@ApplicationScoped
public class GreatestCommonDivisorDao {

	@Produces
	@PersistenceContext
	private EntityManager em;

	/**
	 * Saves a calculated greatest common divisor 
	 * @param value - greatest common divisor to save
	 */
	public void saveGCD(int value) {

		GreatestCommonDivisor gcd = new GreatestCommonDivisor();
		gcd.setComputedGCD(value);
		em.persist(gcd);
	}

        
        /**
	 * Returns the {@link GreatestCommonDivisor} instances saved for processing in their ID property in ascending order. 
	 * @return - List of {@link GreatestCommonDivisor} instances saved in submitting order to the server.
	 */
	public List<GreatestCommonDivisor> findAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<GreatestCommonDivisor> criteriaQuery = criteriaBuilder
				.createQuery(GreatestCommonDivisor.class);
		Root<GreatestCommonDivisor> number = criteriaQuery.from(GreatestCommonDivisor.class);
		criteriaQuery.select(number);
		criteriaQuery.orderBy(criteriaBuilder.asc(number.get(GreatestCommonDivisor.ID)));

		TypedQuery<GreatestCommonDivisor> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
