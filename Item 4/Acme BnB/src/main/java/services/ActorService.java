
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialIdentity;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;


	//Supported Services--------------------------------------------------------------------

	//Constructor--------------------------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		result = actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	//Simple CRUD methods------------------------------------------------------------
	public Actor findOne(int actorId) {
		return actorRepository.findOne(actorId);
	}

	// Other Business Methods -------------------------------------------------------------

	public void setActorCollections(Actor actor) {
		actor.setSocialIdentities(new HashSet<SocialIdentity>());
	}

	public double getMinimumSocialIdentitiesPerActor() {
		//Dashboard-17
		double res = actorRepository.getMinimumSocialIdentitiesPerActor();
		return res;
	}

	public double getAverageSocialIdentitiesPerActor() {
		//Dashboard-17
		double res = actorRepository.getAverageSocialIdentitiesPerActor();
		return res;
	}

	public double getMaximumSocialIdentitiesPerActor() {
		//Dashboard-17
		double res = actorRepository.getMaximumSocialIdentitiesPerActor();
		return res;
	}

	public void reconstruct(Actor result, Actor origin, ActorForm actorForm) {
		UserAccount userAccount;
		
		userAccount = new UserAccount();
		// Setear lo que viene del formulario:
		
		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		userAccount.setUsername(actorForm.getUserAccount().getUsername());

		result.setUserAccount(userAccount);
		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPicture(actorForm.getPicture());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());
		
		// Setear lo que no viene del formulario:
		
		userAccount.setId(origin.getUserAccount().getId());
		userAccount.setVersion(origin.getUserAccount().getVersion());
		userAccount.setAuthorities(origin.getUserAccount().getAuthorities());
		
		result.setId(origin.getId());
		result.setVersion(origin.getVersion());
		result.setSocialIdentities(origin.getSocialIdentities());
		
	}

}
