
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Book extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date	checkInDate;
	private Date	checkOutDate;
	private boolean	smoker;
	private String	state;
	private Double	totalAmount;


	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public boolean getSmoker() {
		return smoker;
	}

	public void setSmoker(Boolean smoker) {
		this.smoker = smoker;
	}

	@NotBlank
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^DENIED$")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	// Relationships ----------------------------------------------------------

	private CreditCard				creditCard;
	private Invoice					invoice;
	private Collection<Property>	properties;
	private Tenant					tenant;
	private Lessor					lessor;


	@NotNull
	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@OneToOne
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@NotNull
	@Valid
	@Size(min = 1, max = 2)
	@ManyToMany
	public Collection<Property> getProperties() {
		return properties;
	}

	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Valid
	@ManyToOne(optional = true)
	public Lessor getLessor() {
		return lessor;
	}

	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}

}
