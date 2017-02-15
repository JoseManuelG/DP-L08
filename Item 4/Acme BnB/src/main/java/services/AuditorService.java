
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Book;
import domain.Comment;

import domain.Property;
import domain.Tenant;

@Service
@Transactional
public class AuditorService {
	
	// Managed Repository --------------------------------------
	@Autowired
	private AuditorRepository	auditorRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private LoginService	loginService;

	// Simple CRUD methods --------------------------------------
	
	public Auditor create() {
		Auditor result;
		result = new Auditor();		

		return result;
	}
	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Auditor findOne(int auditorId) {
		Auditor result;

		result = auditorRepository.findOne(auditorId);

		return result;
	}

	public Auditor save(Auditor auditor) {
		Auditor result;

		Assert.notNull(auditor, "auditor.error.null");
		result = auditorRepository.save(auditor);
		Assert.notNull(result, "auditor.error.commit");

		return result;
	}

	public void delete(Auditor auditor) {
		Assert.notNull(auditor, "auditor.error.null");

		Assert.isTrue(auditorRepository.exists(auditor.getId()), "auditor.error.exists");

		auditorRepository.delete(auditor);
	}

	// Other business methods --------------------------------------

	public Auditor findActorByPrincial() {
		Auditor result;
		result = auditorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	

}
