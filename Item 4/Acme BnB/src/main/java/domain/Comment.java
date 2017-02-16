
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

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
	@Range(min = 0, max = 5)
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostMoment() {
		return postMoment;
	}
	public void setPostMoment(Date dateCreation) {
		this.postMoment = dateCreation;
	}


	//Relationships---------------------------
	private Customer	sender;
	private Comentable	recipient;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getSender() {
		return this.sender;
	}
	public void setSender(Customer sender) {
		this.sender = sender;
	}
	@NotNull
	@Valid
	@ManyToOne(optional = false, targetEntity = DomainEntity.class)
	public Comentable getRecipient() {
		return recipient;
	}
	public void setRecipient(Comentable recipient) {
		this.recipient = recipient;
	}

}
