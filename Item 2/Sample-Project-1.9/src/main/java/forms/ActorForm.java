
package forms;

public class ActorForm {

	private String	typeOfActor;
	private String	password;
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;
	private Boolean	acepted;
	private String	userName;
	private String	companyName;


	//Constructor
	public ActorForm() {
		super();
	}
	//attributes------------

	public String getTypeOfActor() {
		return typeOfActor;
	}

	public void setTypeOfActor(String typeOfActor) {
		this.typeOfActor = typeOfActor;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getAcepted() {
		return acepted;
	}
	public void setAcepted(Boolean acepted) {
		this.acepted = acepted;
	}
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}