
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	//Attributes------------------------------
	private String	title;
	private String	text;
	private int		stars;
	private Date	postMoment;


	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@NotNull
	@Range(min = 0, max = 5)
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	public Date getPostMoment() {
		return postMoment;
	}
	public void setPostMoment(Date dateCreation) {
		this.postMoment = dateCreation;
	}


	//Relationships---------------------------
	private Customer	sender;
	private Customer	recipient;


	@NotNull
	@ManyToOne(optional = false)
	public Customer getSender() {
		return this.sender;
	}
	public void setSender(Customer sender) {
		this.sender = sender;
	}
	@NotNull
	@ManyToOne(optional = false)
	public Customer getRecipient() {
		return recipient;
	}
	public void setRecipient(Customer recipient) {
		this.recipient = recipient;
	}

}
