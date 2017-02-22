
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Comment;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;


	public Customer findActorByPrincial() {
		Customer result;
		result = customerRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public void setCustomerCollections(Customer customer) {
	}
}
