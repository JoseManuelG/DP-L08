
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import domain.Book;
import domain.Invoice;

@Service
@Transactional
public class InvoiceService {

	// Managed Repository --------------------------------------
	@Autowired
	private InvoiceRepository		invoiceRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	// Simple CRUD methods --------------------------------------
	public Invoice create(Book book) {
		Invoice result;

		result = new Invoice();
		result.setVAT(configurationService.getVAT());
		result.setBook(book);
		result.setDetails("??????"); //TODO: Ask for what kind of details save.
		result.setInformation("???????"); //TODO: Ask for what kind of information save.

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
