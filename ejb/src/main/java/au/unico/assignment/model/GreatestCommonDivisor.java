package au.unico.assignment.model;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Calculated greatest common divisor numbers.
 * @author Pankaj Bishnoi
 *
 */
@Entity
@Table(name = "gcd")
public class GreatestCommonDivisor implements Serializable {
        public static final String ID = "id"; 
        public static final String COMPUTED_GCD = "COMPUTED_GCD"; 
	private static final long serialVersionUID = 1L;
	
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID)
	private Integer id;

        
        @Column(name = COMPUTED_GCD)
	private Integer computedGCD;
        
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
        
        public Integer getComputedGCD() {
		return computedGCD;
	}

	public void setComputedGCD(Integer computedGCD) {
		this.computedGCD = computedGCD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GreatestCommonDivisor other = (GreatestCommonDivisor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
