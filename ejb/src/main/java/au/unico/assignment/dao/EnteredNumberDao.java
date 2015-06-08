package au.unico.assignment.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import au.unico.assignment.model.EnteredNumber;
/**
 * This DAO class performs DB operations on EnteredNumber
 * calculation.
 * @author Pankaj Bishnoi
 *
 */
@ApplicationScoped
public class EnteredNumberDao {

	@Produces
	@PersistenceContext
	private EntityManager em;

	/**
	 * Saves an integer received for greatest common divisor calculation
	 * @param value - Integer received for greatest common divisor calculation
	 */
	public void saveNumber(Integer value) {
		EnteredNumber number = new EnteredNumber();
		number.setIntegerValue(value);
		em.persist(number);
	}
	/**
	 * Returns the {@link EnteredNumber} instances saved for processing in their ID property in ascending order. 
	 * @return - List of {@link EnteredNumber} instances saved in submitting order to the server.
	 */
	public List<EnteredNumber> findAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<EnteredNumber> criteriaQuery = criteriaBuilder
				.createQuery(EnteredNumber.class);
		Root<EnteredNumber> number = criteriaQuery.from(EnteredNumber.class);
		criteriaQuery.select(number);
		criteriaQuery.orderBy(criteriaBuilder.asc(number.get(EnteredNumber.ID)));
		TypedQuery<EnteredNumber> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
