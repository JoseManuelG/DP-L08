
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Customer extends Actor {

	//Attributes---------
	//Relationships-----
	private Collection<Comment>	postedComments;
	private Collection<Comment>	comments;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Comment> getPostedComments() {
		return postedComments;
	}

	public void setPostedComments(Collection<Comment> postedComments) {
		this.postedComments = postedComments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "recipient")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
