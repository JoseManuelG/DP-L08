
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Comment;
import domain.Customer;
import domain.Tenant;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;


	//	@Autowired
	//	private LoginService	loginService;
	//	@Autowired
	//	private TenantService	tenantService;
	//	@Autowired
	//	private LessorService	lessorService;

	//	@SuppressWarnings("static-access")
	//	public Customer findActorByPrincial() {
	//		UserAccount userAcc = null;
	//		try {
	//			userAcc = loginService.getPrincipal();
	//		} catch (Throwable t) {
	//
	//		}
	//		Customer result = null;
	//		if (userAcc != null) {
	//			Authority[] authorities = new Authority[userAcc.getAuthorities().size()];
	//			authorities = userAcc.getAuthorities().toArray(authorities);
	//
	//			if (authorities[0].getAuthority().equals(Authority.TENANT)) {
	//				result = tenantService.findByPrincipal();
	//			} else if (authorities[0].getAuthority().equals(Authority.LESSOR)) {
	//				result = lessorService.findByPrincipal();
	//			}
	//		}
	//		return result;
	//	}

	public Customer findActorByPrincial() {
		Tenant result;
		result = customerRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public void setCustomerCollections(Customer customer) {
		customer.setComments(new HashSet<Comment>());
		customer.setPostedComments(new HashSet<Comment>());
	}
}
