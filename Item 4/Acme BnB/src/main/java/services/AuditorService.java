
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Auditor;
import domain.Comment;
import domain.Auditor;
import domain.Tenant;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository	auditorRepository;


	@Autowired
	private LoginService	loginService;

	

	public Auditor findActorByPrincial() {
		Auditor result;
		result = auditorRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public void setAuditorCollections(Auditor customer) {
		customer.setComments(new HashSet<Comment>());
		customer.setPostedComments(new HashSet<Comment>());
	}
}
