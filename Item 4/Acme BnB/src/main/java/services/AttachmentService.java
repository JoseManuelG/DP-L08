package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.LoginService;
import domain.CreditCard;

@Service
@Transactional
public class AttachmentService {
	
	// Managed Repository -------------------------------------------------------------
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	// Supporting Services ------------------------------------------------------------
	
//	@Autowired
//	private LoginService loginService;
//	
//	@Autowired
//	private LessorService lessorService;
	
	// Constructor --------------------------------------------------------------------
	
	public AttachmentService(){
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
	
//	@SuppressWarnings("static-access")
//	public void delete(CreditCard creditCard) {
//		Assert.isTrue(creditCard.get)
//		Assert.notNull(creditCard,"La tarjeta de crédito no puede ser nula");
//		Assert.isTrue(creditCard.getId() != 0,"La tarjeta de crédito debe estar antes en la base de datos");
//		creditCardRepository.exists(creditCard.getId());
//		Assert.isTrue(loginService.getPrincipal().equals(lessorService.getLessorByCreditCard(creditCard).getUserAccount()));
//		Lessor lessor=lessorService.getLessorByCreditCard(creditCard);
//		lessor.setCreditCard(null);
//		lessorService.save(lessor);
//		creditCardRepository.delete(creditCard);
//		
//	}
	
	// Other Bussiness Methods --------------------------------------------------------

}
