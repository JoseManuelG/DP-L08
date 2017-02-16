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
		
	
	// Constructor --------------------------------------------------------------------
	
	public LessorService(){
		super();
	}
		
	// Simple CRUD Methods ------------------------------------------------------------
	
	public Lessor create(){
		Lessor result=new Lessor();
		result.setProperties(new ArrayList<Property>());
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
		Assert.notNull(lessor,"SAVE: El sponsor no puede ser null");
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

}
