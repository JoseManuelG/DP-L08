
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Attribute extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;

	@SafeHtml
	@NotBlank
	public String getName() {
		return this.name;
	}
	@SafeHtml
	public void setName(String name) {
		this.name = name;
	}

}
