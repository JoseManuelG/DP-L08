
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

	@Autowired
	private ActorRepository	actorRepository;


	public Actor findByPrincipal() {
		Actor result;
		result = actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Actor findOne(int actorId) {
		return actorRepository.findOne(actorId);
	}

	public void setActorCollections(Actor actor) {
		actor.setSocialIdentities(new HashSet<SocialIdentity>());
	}

}
