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
		
		private Attribute attributes;
		private Property properties;

		
		@ManyToOne
		public Attribute getAttributes() {
			return attributes;
		}

		public void setAttributes(Attribute attributes) {
			this.attributes = attributes;
		}


		@ManyToOne
		public Property getProperties() {
			return this.properties;
		}

		public void setProperties(property properties) {
			this.properties = properties;
		}


}
