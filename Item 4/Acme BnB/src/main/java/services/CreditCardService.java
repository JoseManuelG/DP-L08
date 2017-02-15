package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {
	
	// Managed Repository -------------------------------------------------------------
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	// Supporting Services ------------------------------------------------------------
	
	
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
		CreditCard result;
		result = creditCardRepository.save(creditCard);
		return result;
	}
	
	// Other Bussiness Methods --------------------------------------------------------

}
