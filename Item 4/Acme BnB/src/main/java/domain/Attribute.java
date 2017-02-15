
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Attribute extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//Relationships
	private Collection<AttributeValue>	attributeValues;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "attribute")
	public Collection<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(Collection<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

}
