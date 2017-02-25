
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;
import domain.Comment;
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
		actor.setComments(new HashSet<Comment>());
	}
	
	public double getMinimumSocialIdentitiesPerActor(){
		double res = actorRepository.getMinimumSocialIdentitiesPerActor();
		return res;
	}
	
	public double getAverageSocialIdentitiesPerActor(){
		double res = actorRepository.getAverageSocialIdentitiesPerActor();
		return res;
	}
	
	public double getMaximumSocialIdentitiesPerActor(){
		double res = actorRepository.getMaximumSocialIdentitiesPerActor();
		return res;
	}
	

}
