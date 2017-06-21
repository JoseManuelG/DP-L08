
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
import domain.Invoice;
import domain.Lessor;
import domain.Property;
import domain.Tenant;
import forms.BookForm;

@Service
@Transactional
public class BookService {

	// Managed Repository --------------------------------------
	@Autowired
	private BookRepository		bookRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private LessorService		lessorService;

	@Autowired
	private TenantService		tenantService;

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------
	public Book create(final Property property, final Tenant tenant) {
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

		result = this.bookRepository.findAll();

		return result;
	}

	public Book findOne(final int bookId) {
		Book result;

		result = this.bookRepository.findOne(bookId);

		return result;
	}

	public Book save(final Book book) {
		Book result;

		Assert.notNull(book, "book.null.error");

		this.checkDayAfter(book);
		this.checkOwnerTenantIsPrincipal(book);
		this.checkBookDate(book);
		this.creditCardService.checkCreditCard(book.getCreditCard());

		this.calculateTotalAmount(book);
		result = this.bookRepository.save(book);
		Assert.notNull(result, "book.commit.error");

		return result;
	}
	public void delete(final Book book) {
		Assert.notNull(book, "book.null.error");

		Assert.isTrue(this.bookRepository.exists(book.getId()), "book.exists.error");

		this.bookRepository.delete(book);
	}

	// Other business methods --------------------------------------

	public boolean existsCreditCardForAnyBook(final CreditCard creditCard) {
		boolean result = false;
		result = this.bookRepository.existsCreditCardForAnyBook(creditCard.getId());
		return result;
	}

	public void acceptBook(final int bookId) {
		Book book;

		book = this.findOne(bookId);

		Assert.notNull(book.getLessor().getCreditCard(), "book.null.credit.card.error");
		this.checkOwnerLessorIsPrincipal(book);
		this.checkStateIsPending(book);
		this.checkBookDate(book);
		this.creditCardService.checkCreditCard(book.getLessor().getCreditCard());

		book.setState("ACCEPTED");
		this.bookRepository.save(book);
		this.lessorService.addFee();

	}

	public void denyBook(final int bookId) {
		Book book;

		book = this.findOne(bookId);
		this.checkOwnerLessorIsPrincipal(book);
		this.checkStateIsPending(book);

		book.setState("DENIED");
		this.bookRepository.save(book);
	}

	public Book reconstruct(final BookForm bookForm, final BindingResult bindingResult) {
		Book book;
		Property property;
		Tenant tenant;

		property = this.propertyService.findOne(bookForm.getPropertyId());
		tenant = this.tenantService.findByPrincipal();

		book = this.create(property, tenant);
		book.setCheckInDate(bookForm.getCheckInDate());
		book.setCheckOutDate(bookForm.getCheckOutDate());
		book.setCreditCard(bookForm.getCreditCard());
		book.setSmoker(bookForm.getSmoker());
		this.validator.validate(book, bindingResult);
		return book;
	}

	public Collection<Book> findBooksForProperty(final Property property) {
		final Collection<Book> result = this.bookRepository.findBooksForPropertyId(property.getId());
		return result;
	}

	private void checkStateIsPending(final Book book) {
		Assert.isTrue(book.getState().equals("PENDING"), "book.pending.error");
	}

	public void checkOwnerLessorIsPrincipal(final Book book) {
		Actor principal;
		Lessor owner;

		Assert.notNull(book, "book.exists.error");
		principal = this.lessorService.findByPrincipal();
		Assert.notNull(principal, "book.principal.error");
		owner = book.getLessor();

		Assert.isTrue(owner.equals(principal), "book.principal.error");
	}

	public void checkOwnerTenantIsPrincipal(final Book book) {
		Actor principal;
		Tenant owner;

		Assert.notNull(book, "book.exists.error");
		principal = this.tenantService.findByPrincipal();
		Assert.notNull(principal, "book.principal.error");
		owner = book.getTenant();

		Assert.isTrue(owner.equals(principal), "book.principal.error");
	}

	public Double getAverageAcceptedBooksPerLessor() {
		//Dashboard-01
		Double res, c1;
		Long c2;

		c1 = this.bookRepository.countAcceptedBooks();
		c2 = this.lessorService.count();

		if (c1 != null && c2 != 0)
			res = c1 / c2;
		else
			res = 0.;
		return res;
	}

	public Double getAverageDeniedBooksPerLessor() {
		//Dashboard-01
		Double res, c1;
		Long c2;

		c1 = this.bookRepository.countDeniedBooks();
		c2 = this.lessorService.count();

		if (c1 != null && c2 != 0)
			res = c1 / c2;
		else
			res = 0.;
		return res;
	}

	public Double getAverageAcceptedBooksPerTenant() {
		//Dashboard-02
		Double res, c1;
		Long c2;

		c1 = this.bookRepository.countAcceptedBooks();
		c2 = this.tenantService.count();

		if (c1 != null && c2 != 0)
			res = c1 / c2;
		else
			res = 0.;
		return res;
	}

	public Double getAverageDeniedBooksPerTenant() {
		//Dashboard-02
		Double res, c1;
		Long c2;

		c1 = this.bookRepository.countDeniedBooks();
		c2 = this.tenantService.count();

		if (c1 != null && c2 != 0)
			res = c1 / c2;
		else
			res = 0.;
		return res;
	}

	private void checkDayAfter(final Book book) {
		long checkIn, checkOut, aDay;

		checkIn = book.getCheckInDate().getTime();
		checkOut = book.getCheckOutDate().getTime();
		aDay = 24 * 60 * 60 * 100;

		Assert.isTrue(checkOut - checkIn >= aDay, "book.checkDayAfter.error");
	}

	private void checkBookDate(final Book book) {
		long checkIn, now;

		checkIn = book.getCheckInDate().getTime();
		now = System.currentTimeMillis();

		Assert.isTrue(checkIn > now, "book.checkDate.error");
	}

	private void calculateTotalAmount(final Book book) {
		int days;
		long out, in;

		out = book.getCheckOutDate().getTime();
		in = book.getCheckInDate().getTime();
		days = (int) ((out - in) / (1000 * 60 * 60 * 24));

		book.setTotalAmount(days * book.getProperty().getRate());
	}

	public Double getAverageRequestsWithAuditsVersusNoAudits() {
		//Dashboard-20
		Double withAudits;
		Double withoutAudits;
		Double res;

		withAudits = this.bookRepository.getAverageRequestsWithAudits();
		withoutAudits = this.bookRepository.getAverageRequestsWithoutAudits();

		if (withAudits == null || withoutAudits == null || withoutAudits == 0.0)
			res = 0.0;
		else
			res = withAudits / withoutAudits;
		return res;
	}
	public Invoice addInvoice(final Book book, final Invoice invoice) {
		Book result;

		book.setInvoice(invoice);
		result = this.bookRepository.save(book);

		return result.getInvoice();
	}

	public void removeTenant(final Tenant tenant) {

		for (final Book book : tenant.getBooks()) {
			book.setTenant(null);
			this.bookRepository.save(book);
		}

	}

	public void removeLessor(final Lessor lessor) {

		for (final Book book : lessor.getBooks()) {
			book.setLessor(null);
			book.setProperty(null);
			this.bookRepository.save(book);
		}

	}
	public void removePropertyFromBooks(final Collection<Book> books) {
		for (final Book b : books) {
			b.setProperty(null);
			this.bookRepository.save(b);
		}

	}

}
