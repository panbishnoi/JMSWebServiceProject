package au.unico.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Domain class for number received
 * @author Pankaj 
 *
 */
@Entity
@Table(name = "enterednumbers")
public class EnteredNumber implements Serializable {

	public static final String ID = "id";
        public static final String INTEGERVALUE = "INTEGERVALUE";
	private static final long serialVersionUID = 1L;

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID)
	private Integer id;

	@Column(name = INTEGERVALUE)
	private Integer integerValue;

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public void setId(Integer numberId) {
		this.id = numberId;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((integerValue == null) ? 0 : integerValue.hashCode());
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
		EnteredNumber other = (EnteredNumber) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (integerValue == null) {
			if (other.integerValue != null)
				return false;
		} else if (!integerValue.equals(other.integerValue))
			return false;
		return true;
	}

}
