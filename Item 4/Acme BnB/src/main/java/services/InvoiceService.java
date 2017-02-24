
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import domain.Book;
import domain.Invoice;
import domain.Tenant;

@Service
@Transactional
public class InvoiceService {

	// Managed Repository --------------------------------------
	@Autowired
	private InvoiceRepository		invoiceRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CreditCardService	creditCardService;


	// Simple CRUD methods --------------------------------------
	public Invoice create(Book book) {
		Invoice result;
		Tenant tenant;
		String details, information;
		
		try {
			creditCardService.maskCreditCard(book.getCreditCard());
		}catch (TransactionSystemException e) {
		}
		tenant = book.getTenant();
		details = "Checkin: " + book.getCheckInDate() + "\n" +
			"Checkout: " + book.getCheckOutDate() + "\n" +
			"Credit Card: " + book.getCreditCard() + "\n" +
			"Amount: " + book.getTotalAmount() + "\n";
		if (book.getSmoker()){
			details += "Smoker \n";
		} else {
			details += "No smoker \n";
		}			
		
		information = tenant.getName() + " " + tenant.getSurname(); 

		result = new Invoice();
		result.setCreationMoment(new Date(System.currentTimeMillis()));
		result.setVAT(configurationService.findOne().getVAT());
		result.setBook(book);
		result.setDetails(details);
		result.setInformation(information);
		
		result = this.save(result);
		
		return result;
	}
	public Collection<Invoice> findAll() {
		Collection<Invoice> result;

		result = invoiceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Invoice findOne(int invoiceId) {
		Invoice result;

		result = invoiceRepository.findOne(invoiceId);

		return result;
	}

	public Invoice save(Invoice invoice) {
		Invoice result;

		Assert.notNull(invoice, "invoice.error.null");
		result = invoiceRepository.save(invoice);
		Assert.notNull(result, "invoice.error.commit");

		return result;
	}

	public void delete(Invoice invoice) {
		Assert.notNull(invoice, "invoice.error.null");

		Assert.isTrue(invoiceRepository.exists(invoice.getId()), "invoice.error.exists");

		invoiceRepository.delete(invoice);
	}

	// Other business methods --------------------------------------
}
