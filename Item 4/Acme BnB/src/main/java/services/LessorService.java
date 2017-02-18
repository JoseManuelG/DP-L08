package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LessorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Book;
import domain.CreditCard;
import domain.Lessor;
import domain.Property;

@Service
@Transactional
public class LessorService {
	
	// Managed Repository -------------------------------------------------------------
	
	@Autowired
	private LessorRepository lessorRepository;
		
	// Supporting Services ------------------------------------------------------------
		
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ConfigurationService configurationService;
		
	
	// Constructor --------------------------------------------------------------------
	
	public LessorService(){
		super();
	}
		
	// Simple CRUD Methods ------------------------------------------------------------
	
	public Lessor create(){
		Lessor result=new Lessor();
		customerService.setCustomerCollections(result);
		result.setLessorProperties(new ArrayList<Property>());
		result.setBooks(new ArrayList<Book>());
		return result;
	}
	
	public Collection<Lessor> findAll(){
		Collection<Lessor> result=lessorRepository.findAll();
		return result;
	}
	
	public Lessor findOne(Integer lessorId){
		Lessor result=lessorRepository.findOne(lessorId);
		return result;
	}
	
	public Lessor save(Lessor lessor){
		Assert.notNull(lessor,"SAVE: El lessor no puede ser null");
		Lessor result=lessorRepository.save(lessor);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Lessor lessor){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(lessor,"DELETE: El lessor no puede ser null");
		Assert.isTrue(lessor.getId()!=0,"El lessor debe haber sido guardado");
		Assert.isTrue(principal.equals(lessor.getUserAccount()),"UserAccount no valido");
		lessorRepository.delete(lessor);
	}
	
	
	// Other Bussiness Methods --------------------------------------------------------
	
	public boolean existsCreditCardForAnyLessor(CreditCard creditCard){
		boolean result = false;
		result = lessorRepository.existsCreditCardForAnyLessor(creditCard.getId());
		return result;
	}
	
	public Lessor findByPrincipal() {
		Lessor result;
		result = lessorRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public Collection<Book> findAllBooksByPrincipal() {
		Lessor principal;
		Collection<Book> result;
		
		principal = this.findByPrincipal();
		result = lessorRepository.findAllBooksByPrincipal(principal.getId());
		return result;
	}

	public void addFee() {
		Lessor principal;
		double actualFee;

		principal = this.findByPrincipal();
		actualFee = principal.getTotalFee();
		principal.setTotalFee(actualFee + configurationService.findOne().getFee());
	}

	public Lessor findByPrincipal() {
		Lessor result;
		result = lessorRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public Collection<Book> findAllBooksByPrincipal() {
		Lessor principal;
		Collection<Book> result;
		
		principal = this.findByPrincipal();
		result = lessorRepository.findAllBooksByPrincipal(principal.getId());
		return result;
	}

	public void addFee() {
		Lessor principal;
		double actualFee;

		principal = this.findByPrincipal();
		actualFee = principal.getTotalFee();
		principal.setTotalFee(actualFee + configurationService.findOne().getFee());
	}

}
