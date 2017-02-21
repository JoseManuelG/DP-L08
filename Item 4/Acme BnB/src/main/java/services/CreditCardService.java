package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Lessor;

@Service
@Transactional
public class CreditCardService {
	
	// Managed Repository -------------------------------------------------------------
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	// Supporting Services ------------------------------------------------------------
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructor --------------------------------------------------------------------
	
	public CreditCardService(){
		super();
	}
	
	// Simple CRUD methods ------------------------------------------------------------
	
	public CreditCard create(){
		CreditCard result;
		result = new CreditCard();
		return result;
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> result;
		result = creditCardRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public CreditCard findOne(int creditCardId){
		CreditCard result;
		result = creditCardRepository.findOne(creditCardId);
		return result;
	}
	
	public CreditCard save(CreditCard creditCard){
		Assert.notNull(creditCard,"La tarjeta de crédito no puede ser nula");
		Assert.isTrue((bookService.existsCreditCardForAnyBook(creditCard) && !lessorService.existsCreditCardForAnyLessor(creditCard)) || (!bookService.existsCreditCardForAnyBook(creditCard) && lessorService.existsCreditCardForAnyLessor(creditCard)));
		Lessor lessor = (Lessor) customerService.findActorByPrincial();
		Assert.isTrue((creditCard.getId()!=0 &&  lessor.getCreditCard().getId()==creditCard.getId()) || creditCard.getId()==0); 
		CreditCard result;
		result = creditCardRepository.save(creditCard);
		return result;
	}
	
	// Other Bussiness Methods --------------------------------------------------------

}
