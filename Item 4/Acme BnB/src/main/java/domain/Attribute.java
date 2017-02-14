package domain;

import java.util.Collection;

import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

public class Attribute extends DomainEntity{
	// Attributes -------------------------------------------------------------
	private String name;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	  
	//Relationships
	private Collection<AttributeValue> attributeValues;
	@OneToMany
	public Collection<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(Collection<AttributeValue> attributeValues) {
		this.attributeValues= attributeValues;
	}
	
	
	
}
