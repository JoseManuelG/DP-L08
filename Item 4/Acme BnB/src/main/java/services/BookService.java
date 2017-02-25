
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BookRepository;
import domain.Actor;
import domain.Book;
import domain.CreditCard;
import domain.Lessor;
import domain.Property;
import domain.Tenant;
import forms.BookForm;

@Service
@Transactional
public class BookService {

	// Managed Repository --------------------------------------
	@Autowired
	private BookRepository	bookRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private LessorService	lessorService;

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private Validator		validator;


	// Simple CRUD methods --------------------------------------
	public Book create(Property property, Tenant tenant) {
		Book result;

		result = new Book();
		result.setProperty(property);
		result.setTenant(tenant);
		result.setState("PENDING");
		result.setPropertyAddress(property.getAddress());
		result.setPropertyName(property.getName());
		result.setLessor(property.getLessor());

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
		checkDayAfter(book);
		checkOwnerTenantIsPrincipal(book);
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

	public boolean existsCreditCardForAnyBook(CreditCard creditCard) {
		boolean result = false;
		result = bookRepository.existsCreditCardForAnyBook(creditCard.getId());
		return result;
	}

	public void acceptBook(int bookId) {
		Book book;

		book = this.findOne(bookId);
		checkOwnerLessorIsPrincipal(book);
		checkStateIsPending(book);
		//TODO: ¿Checkear que las fechas sean futuras? (Qué sentido tiene aceptar un book que se ha pasado de fecha...)
		book.setState("ACCEPTED");
		bookRepository.save(book);
		lessorService.addFee();

	}

	public void denyBook(int bookId) {
		Book book;

		book = this.findOne(bookId);
		checkOwnerLessorIsPrincipal(book);
		checkStateIsPending(book);

		book.setState("DENIED");
		bookRepository.save(book);
	}

	public Book reconstruct(BookForm bookForm, BindingResult bindingResult) {
		Book book;
		Property property;
		Tenant tenant;

		property = propertyService.findOne(bookForm.getPropertyId());
		tenant = tenantService.findByPrincipal();

		book = this.create(property, tenant);
		book.setCheckInDate(bookForm.getCheckInDate());
		book.setCheckOutDate(bookForm.getCheckOutDate());
		book.setCreditCard(bookForm.getCreditCard());
		book.setSmoker(bookForm.getSmoker());
		calculateTotalAmount(book);
		validator.validate(book, bindingResult);
		return book;
	}

	public Collection<Book> findBooksForProperty(Property property) {
		Collection<Book> result = bookRepository.findBooksForPropertyId(property.getId());
		return result;
	}

	private void checkStateIsPending(Book book) {
		Assert.isTrue(book.getState().equals("PENDING"));
	}

	public void checkOwnerLessorIsPrincipal(Book book) {
		Actor principal;
		Lessor owner;

		principal = lessorService.findByPrincipal();
		owner = book.getLessor();

		Assert.isTrue(owner.equals(principal));
	}

	public void checkOwnerTenantIsPrincipal(Book book) {
		Actor principal;
		Tenant owner;

		principal = tenantService.findByPrincipal();
		owner = book.getTenant();

		Assert.isTrue(owner.equals(principal));
	}

	public double getAverageAcceptedBooksPerLessor() {
		//Dashboard-01
		double res;

		res = bookRepository.countAcceptedBooks() / lessorService.count();

		return res;
	}

	public double getAverageDeniedBooksPerLessor() {
		//Dashboard-01
		double res;

		res = bookRepository.countDeniedBooks() / lessorService.count();

		return res;
	}

	public double getAverageAcceptedBooksPerTenant() {
		//Dashboard-02
		double res;

		res = bookRepository.countAcceptedBooks() / tenantService.count();

		return res;
	}

	public double getAverageDeniedBooksPerTenant() {
		//Dashboard-02
		double res;

		res = bookRepository.countDeniedBooks() / tenantService.count();

		return res;
	}

	private void checkDayAfter(Book book) {
		long checkIn, checkOut, aDay;

		checkIn = book.getCheckInDate().getTime();
		checkOut = book.getCheckOutDate().getTime();
		aDay = 24 * 60 * 60 * 100;

		Assert.isTrue(checkOut - checkIn >= aDay, "book.error.checkDate");
	}

	private void calculateTotalAmount(Book book) {
		int days;
		long out, in;

		out = book.getCheckOutDate().getTime();
		in = book.getCheckInDate().getTime();
		days = (int) (out - in) / (1000 * 60 * 60 * 24);

		book.setTotalAmount(days * book.getProperty().getRate());
	}
}
