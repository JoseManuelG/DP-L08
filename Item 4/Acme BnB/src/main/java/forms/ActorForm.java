package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ActorForm {
	
	private String  typeOfActor;
	private String  username;
	private String  password;
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;
	private boolean conditionsAccepted;
	

// Constructor -------------------------------------------------------
	public ActorForm(){
		super();
	}
//Attributes -------------------------------------------------------
	
	@Pattern(regexp ="^LESSOR$|^USER$|^TENANT$|^AUDITOR$")
	public String getTypeOfActor() {
		return typeOfActor;
	}

	public void setTypeOfActor(String typeOfActor) {
		this.typeOfActor = typeOfActor;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}

	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^\\+([0-9][0-9][0-9])([0-9A-Za-z])+$")
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@URL
	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public boolean isConditionsAccepted() {
		return conditionsAccepted;
	}


	public void setConditionsAccepted(boolean conditionsAccepted) {
		this.conditionsAccepted = conditionsAccepted;
	}

	




	
}
