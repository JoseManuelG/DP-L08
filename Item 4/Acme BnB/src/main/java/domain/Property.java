
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private double	rate;
	private String	description;
	private String	address;


	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Min(0)
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	// Relationships -------------------------------------------------------------

	private Collection<Book>			books;
	private Lessor						lessor;
	private Collection<Audit>			audits;
	private Collection<AttributeValue>	attributeValues;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Book> getBooks() {
		return books;
	}
	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Lessor getLessor() {
		return lessor;
	}
	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<AttributeValue> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(Collection<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}
}
