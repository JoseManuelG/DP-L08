/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.BookService;
import services.CommentService;
import services.FinderService;
import services.PropertyService;
import services.TenantService;
import utilities.AbstractTest;
import domain.Book;
import domain.Comment;
import domain.Finder;
import domain.Property;
import domain.Tenant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TenantTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private FinderService	finderService;

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private BookService		bookService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	// Configure his or her finder.
	// Todo correcto

	@Test
	public void finderPositiveTest() {
		this.template("test1", 0.0, 0.0, "test1", null);
	}

	//  Finder sin localización

	@Test
	public void finderNegativeTest() {

		this.template(null, 0.0, 0.0, "test1", ConstraintViolationException.class);
	}

	//Register to the system as a tenant.
	// Registro sin errores
	@Test
	public void RegisterPositiveTest() {

		this.template2("test1", "test1", "test@acme.com", "test1", "tested", "+34908900", null);
	}

	// Registro sin password
	@Test
	public void RegisterNegativeTest() {

		this.template2("test1", "", "test@acme.com", "test1", "tested", "+34908900", ConstraintViolationException.class);
	}

	// Make a request for a property.
	// Positive Test
	@Test
	public void RequestPositiveTest() {
		this.template3("tenant1", 50, null);
	}

	// Invalid property
	@Test
	public void RequestNegativeTest() {
		this.template3("tenant1", -1, NullPointerException.class);
	}

	// Post comments to his own profile or to the profile of any lessor whose properties he or she has requested.
	// Post comments to his own profile
	@Test
	public void CommentPositiveTest() {
		this.template4("tenant1", 41, "text", null);
	}

	// Bad user
	@Test
	public void CommentNegativeTest() {
		this.template4("tenant1", -1, "text", IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String destination, final Double minPrice, final Double maxPrice, final String keyword, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate("tenant1");
			Finder finder = this.tenantService.findByPrincipal().getFinder();
			finder.setDestination(destination);
			finder.setMinPrice(minPrice);
			finder.setMaxPrice(maxPrice);
			finder.setKeyword(keyword);
			finderService.save(finder);
			finderService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template2(final String userName, final String password, final String email, final String name, final String surname, final String phone, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Tenant customer = this.tenantService.create();
			final UserAccount userAccount = new UserAccount();
			final Collection<Authority> authorities = new ArrayList<Authority>();
			final Authority authority = new Authority();

			authority.setAuthority("TENANT");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);
			customer.setUserAccount(userAccount);
			customer.getUserAccount().setUsername(userName);
			customer.getUserAccount().setPassword(password);
			customer.setEmail(email);
			customer.setPicture("http://www.jose.com");
			customer.setName(name);
			customer.setSurname(surname);
			customer.setPhone(phone);
			this.tenantService.save(customer);
			this.tenantService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template3(final String tenant, final Integer property, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(tenant);
			Tenant tenan = this.tenantService.findByPrincipal();
			Property properti = this.propertyService.findOne(property);
			Book book = this.bookService.create(properti, tenan);
			DateTime dateTime = new DateTime(System.currentTimeMillis());
			Date checkInDate = dateTime.plusDays(1).toDate();
			Date checkOutDate = dateTime.plusDays(3).toDate();
			book.setCheckInDate(checkInDate);
			book.setCheckOutDate(checkOutDate);
			book.setCreditCard(properti.getLessor().getCreditCard());
			book.getCreditCard().setExpirationYear(2020);
			this.bookService.save(book);
			this.bookService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template4(String string, int i, String string2, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(string);
			Tenant tenant = this.tenantService.findByPrincipal();
			Comment comment = this.commentService.create();
			comment.setRecipient(this.actorService.findOne(i));
			comment.setSender(tenant);
			comment.setStars(5);
			comment.setTitle(string2);
			comment.setText(string2);
			comment.setPostMoment(new Date(System.currentTimeMillis()));
			this.commentService.save(comment);
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
