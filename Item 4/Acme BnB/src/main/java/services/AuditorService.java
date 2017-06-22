
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import forms.ActorForm;

@Service
@Transactional
public class AuditorService {

	// Managed Repository --------------------------------------
	@Autowired
	private AuditorRepository	auditorRepository;

	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	// Supporting Services --------------------------------------
	// Simple CRUD methods --------------------------------------

	public Auditor create() {
		Auditor result;
		result = new Auditor();
		this.actorService.setActorCollections(result);
		result.setAudits(new ArrayList<Audit>());
		return result;
	}
	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);

		return result;
	}

	public Auditor save(final Auditor auditor) {
		Auditor result;

		Assert.notNull(auditor, "auditor.error.null");
		result = this.auditorRepository.save(auditor);
		Assert.notNull(result, "auditor.error.commit");

		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor, "auditor.error.null");

		Assert.isTrue(this.auditorRepository.exists(auditor.getId()), "auditor.error.exists");

		this.auditorRepository.delete(auditor);
	}

	// Other business methods --------------------------------------

	public Auditor findActorByPrincial() {
		Auditor result;
		result = this.auditorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}
	public Auditor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Auditor result = this.create();

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final UserAccount userAccount = new UserAccount();
		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		userAccount.setUsername(actorForm.getUserAccount().getUsername());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority("AUDITOR");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPicture(actorForm.getPicture());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());
		result.setCompanyName(actorForm.getCompanyName());

		result.setUserAccount(userAccount);

		this.validator.validate(result, binding);
		userAccount.setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}

	public Auditor reconstruct(final ActorForm actorForm, final Auditor auditor, final BindingResult binding) {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		Auditor result;

		result = new Auditor();

		this.actorService.reconstruct(result, auditor, actorForm);

		result.setAudits(auditor.getAudits());
		result.setCompanyName(auditor.getCompanyName());

		this.validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}

}
