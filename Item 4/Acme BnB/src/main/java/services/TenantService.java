
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TenantRepository;
import security.LoginService;
import domain.Book;
import domain.Tenant;

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

}
