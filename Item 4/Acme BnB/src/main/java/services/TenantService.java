
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TenantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Book;
import domain.Lessor;
import domain.Tenant;
import forms.ActorForm;

@Service
@Transactional
public class TenantService {

	// Managed Repository --------------------------------------
	@Autowired
	private TenantRepository	tenantRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService		actorService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FinderService		finderService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------
	public Tenant create() {
		Tenant result;

		result = new Tenant();
		actorService.setActorCollections(result);
		customerService.setCustomerCollections(result);
		result.setBooks(new HashSet<Book>());
		result.setFinder(finderService.create());

		return result;
	}
	public Collection<Tenant> findAll() {
		Collection<Tenant> result;

		result = tenantRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tenant findOne(int tenantId) {
		Tenant result;

		result = tenantRepository.findOne(tenantId);

		return result;
	}

	public Tenant save(Tenant tenant) {
		Tenant result;

		Assert.notNull(tenant, "tenant.error.null");
		result = tenantRepository.save(tenant);
		Assert.notNull(result, "tenant.error.commit");

		return result;
	}

	public void delete(Tenant tenant) {
		Assert.notNull(tenant, "tenant.error.null");

		Assert.isTrue(tenantRepository.exists(tenant.getId()), "tenant.error.exists");

		tenantRepository.delete(tenant);
	}
	// Other business methods --------------------------------------

	public Tenant findByPrincipal() {
		Tenant result;
		result = tenantRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}
	public Collection<Book> findAllBooksByPrincipal() {
		Tenant principal;
		Collection<Book> result;

		principal = this.findByPrincipal();
		result = tenantRepository.findAllBooksByPrincipal(principal.getId());
		return result;
	}

	public boolean tenantHaveBooksWithLessor(Tenant tenant, Lessor lessor) {
		boolean res = false;
		if (tenantRepository.findAllBooksByTennantAndLessor(tenant.getId(), lessor.getId()) > 0) {
			res = true;
		}
		return res;
	}

	public Tenant reconstruct(ActorForm actorForm, BindingResult binding) {
		Tenant result = create();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = new UserAccount();
		userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
		userAccount.setUsername(actorForm.getUserName());
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(actorForm.getTypeOfActor());
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPicture(actorForm.getPicture());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());

		result.setUserAccount(userAccount);

		validator.validate(result, binding);
		return null;
	}
}
