
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Attributes-----------------------------------
	private double	fee;
	private String	VAT;


	public double getFee() {
		return this.fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	@NotBlank
	public String getVAT() {
		return this.VAT;
	}

	public void setVAT(String VAT) {
		this.VAT = VAT;
	}

}
