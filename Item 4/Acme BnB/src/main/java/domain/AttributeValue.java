package domain;

import java.util.Collection;

import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

public class AttributeValue extends DomainEntity {
	
	// Attributes -------------------------------------------------------------
		private String value;

		@NotBlank		
		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}		
		  

		//Relationships
		
		private Collection<Attribute> attributes;
		private Collection<Property> properties;

		
		@ManyToOne
		public Collection<Attribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(Collection<Attribute> attributes) {
			this.attributes = attributes;
		}


		@ManyToOne
		public Collection<Property> getProperties() {
			return this.properties;
		}

		public void setProperties(Collection<property> properties) {
			this.properties = properties;
		}


}
