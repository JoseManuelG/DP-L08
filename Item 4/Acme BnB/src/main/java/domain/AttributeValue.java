
package domain;

import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

public class AttributeValue extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	value;


	@NotBlank
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	//Relationships

	private Attribute	attribute;
	private Property	property;


	@ManyToOne
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@ManyToOne
	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}
