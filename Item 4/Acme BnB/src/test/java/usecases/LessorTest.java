/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import services.BookService;
import services.LessorService;
import services.PropertyService;
import utilities.AbstractTest;
import domain.Property;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LessorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LessorService	lessorService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private PropertyService	propertyService;
	@Autowired
	private BookService		bookService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de crear retalium con titulo longitud minima:
	//test positivo
	@Test
	public void RegisterProperty1() {
		this.templateRegisterProperty("Lessor1", "NameTest", "DescriptionTest", "AddresTest", 1, null);
	}
	//Caso de uso de registrar property con name blanco:
	//test negativo
	@Test
	public void RegisterProperty2() {
		this.templateRegisterProperty("Lessor1", "", "DescriptionTest", "AddresTest", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con descripcion blanco:
	//test negativo
	@Test
	public void RegisterProperty3() {
		this.templateRegisterProperty("Lessor1", "NameTest", "", "AddresTest", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con Addres blanco:
	//test negativo
	@Test
	public void RegisterProperty4() {
		this.templateRegisterProperty("Lessor1", "NameTest", "DescriptionTest", "", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con rate negativo:
	//test negativo
	@Test
	public void RegisterProperty5() {
		this.templateRegisterProperty("Lessor1", "", "DescriptionTest", "AddresTest", -1, IllegalArgumentException.class);
	}
	//Caso de uso de crear retalium con titulo longitud minima:
	//test positivo
	@Test
	public void EditProperty1() {
		this.templateEditProperty("Lessor1", 50, "NameTest", "DescriptionTest", "AddresTest", 1, null);
	}
	//Caso de uso de registrar property con name blanco:
	//test negativo
	@Test
	public void EditProperty2() {
		this.templateEditProperty("Lessor1", 50, "", "DescriptionTest", "AddresTest", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con descripcion blanco:
	//test negativo
	@Test
	public void EditProperty3() {
		this.templateEditProperty("Lessor1", 50, "NameTest", "", "AddresTest", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con Addres blanco:
	//test negativo
	@Test
	public void EditProperty4() {
		this.templateEditProperty("Lessor1", 50, "NameTest", "DescriptionTest", "", 1, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con rate negativo:
	//test negativo
	@Test
	public void EditProperty5() {
		this.templateEditProperty("Lessor1", 50, "", "DescriptionTest", "AddresTest", -1, IllegalArgumentException.class);
	}
	//Caso de uso aceptar book:
	//test positivo
	@Test
	public void AcceptBook1() {
		this.templateAcceptBook("Lessor1", 82, null);
	}
	//La book no sea tuya
	@Test
	public void AcceptBook2() {
		this.templateAcceptBook("Lessor4", 82, IllegalArgumentException.class);
	}
	//Caso de uso aceptar book:
	//test positivo
	@Test
	public void DeniedBook1() {
		this.templateDeniedBook("Lessor1", 83, null);
	}
	//La book no sea tuya
	@Test
	public void DeniedBook2() {
		this.templateDeniedBook("Lessor4", 83, IllegalArgumentException.class);
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateRegisterProperty(final String username, final String name, final String description, final String address, final int rate, final Class<?> expected) {
		Class<?> caught;
		Property property;

		caught = null;
		try {
			this.authenticate(username);
			property = this.propertyService.create(this.lessorService.findByPrincipal().getId());
			property.setAddress(address);
			property.setDescription(description);
			property.setName(name);
			property.setRate(rate);

			property = this.propertyService.save(property);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateEditProperty(final String username, final int beanId, final String name, final String description, final String address, final int rate, final Class<?> expected) {
		Class<?> caught;
		Property property;

		caught = null;
		try {
			this.authenticate(username);
			property = this.propertyService.findOne(beanId);
			property.setAddress(address);
			property.setDescription(description);
			property.setName(name);
			property.setRate(rate);
			this.propertyService.save(property);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateAcceptBook(final String username, final int bookId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			this.bookService.acceptBook(bookId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDeniedBook(final String username, final int bookId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			this.bookService.denyBook(bookId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
