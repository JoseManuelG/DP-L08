
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;
import domain.SocialIdentity;

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

}
