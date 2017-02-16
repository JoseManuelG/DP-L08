package forms;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import domain.Lessor;

public class ActorForm {
	
	private String  typeOfActor;
	private Lessor lessor;
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
	
	@Valid
	public Lessor getLessor() {
		return lessor;
	}

	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}

	public boolean isConditionsAccepted() {
		return conditionsAccepted;
	}


	public void setConditionsAccepted(boolean conditionsAccepted) {
		this.conditionsAccepted = conditionsAccepted;
	}

	




	
}
