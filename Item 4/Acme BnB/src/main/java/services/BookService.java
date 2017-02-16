
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BookRepository;
import domain.Book;
import domain.CreditCard;
import domain.Property;
import domain.Tenant;

@Service
@Transactional
public class BookService {

	// Managed Repository --------------------------------------
	@Autowired
	private BookRepository	bookRepository;


	// Supporting Services --------------------------------------

	// Simple CRUD methods --------------------------------------
	public Book create(Property property, Tenant tenant) {
		Book result;

		result = new Book();
		result.setProperty(property);
		result.setTenant(tenant);
		result.setState("PENDING");

		return result;
	}
	public Collection<Book> findAll() {
		Collection<Book> result;

		result = bookRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Book findOne(int bookId) {
		Book result;

		result = bookRepository.findOne(bookId);

		return result;
	}

	public Book save(Book book) {
		Book result;

		Assert.notNull(book, "book.error.null");
		result = bookRepository.save(book);
		Assert.notNull(result, "book.error.commit");

		return result;
	}

	public void delete(Book book) {
		Assert.notNull(book, "book.error.null");

		Assert.isTrue(bookRepository.exists(book.getId()), "book.error.exists");

		bookRepository.delete(book);
	}

	// Other business methods --------------------------------------
	
	public boolean existsCreditCardForAnyBook(CreditCard creditCard){
		boolean result = false;
		result = bookRepository.existsCreditCardForAnyBook(creditCard.getId());
		return result;
	}
}
