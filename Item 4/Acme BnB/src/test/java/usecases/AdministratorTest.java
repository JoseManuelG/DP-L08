/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.AttributeService;
import services.AuditorService;
import services.UserAccountService;
import utilities.AbstractTest;
import domain.Attribute;
import domain.Auditor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AuditorService		auditorService;
	@Autowired
	private AttributeService	attributeService;
	@Autowired
	private UserAccountService	userAccountService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de crear attribute
	//test positivo
	@Test
	public void RegisterAttribute1() {
		this.templateRegisterAttribute("admin", "NameTest", null);
	}
	//nombre en blanco
	@Test
	public void RegisterAttribute2() {
		this.templateRegisterAttribute("admin", "", IllegalArgumentException.class);
	}
	//atributo ya existente
	@Test
	public void RegisterAttribute3() {
		this.templateRegisterAttribute("admin", "Country", DataIntegrityViolationException.class);
	}

	//Caso de uso de editar un attribute
	//test positivo
	@Test
	public void EditAttribute1() {
		this.templateEditAttribute("admin", 100, "NameTest", null);
	}
	//nombre en blanco
	@Test
	public void EditAttribute2() {
		this.templateEditAttribute("admin", 100, "", IllegalArgumentException.class);
	}
	//atributo ya existente
	@Test
	public void EditAttribute3() {
		this.templateEditAttribute("admin", 100, "Country", DataIntegrityViolationException.class);
	}

	//Caso de uso de editar un attribute
	//test positivo
	@Test
	public void DeleteAttribute1() {
		this.templateDeleteAttribute("admin", 106, null);
	}
	//attribute en uso
	@Test
	public void DeleteAttribute2() {
		this.templateDeleteAttribute("admin", 100, DataIntegrityViolationException.class);
	}

	//Caso de uso de registrar a un auditor
	//test positivo
	@Test
	public void RegisterAuditor1() {
		this.templateRegisterAuditor("admin", "name", "surname", "email@email.com", "+123456789", "http://www.photo.com", "companyName", "auditorTest", "auditorTest", null);
	}
	//auditor con nombre de usuario ya existente
	@Test
	public void RegisterAuditor2() {
		this.templateRegisterAuditor("admin", "name", "surname", "email@email.com", "+123456789", "http://www.photo.com", "companyName", "auditor1", "auditorTest", DataIntegrityViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateRegisterAttribute(final String username, final String name, final Class<?> expected) {
		Class<?> caught;
		Attribute attribute;

		caught = null;
		try {
			this.authenticate(username);
			attribute = this.attributeService.create();
			attribute.setName(name);

			attribute = this.attributeService.save(attribute);

			this.attributeService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateEditAttribute(final String username, final Integer attributeId, final String name, final Class<?> expected) {
		Class<?> caught;
		Attribute attribute;

		caught = null;
		try {
			this.authenticate(username);
			attribute = this.attributeService.findOne(attributeId);
			attribute.setName(name);

			attribute = this.attributeService.save(attribute);

			this.attributeService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDeleteAttribute(final String username, final Integer attributeId, final Class<?> expected) {
		Class<?> caught;
		Attribute attribute;

		caught = null;
		try {
			this.authenticate(username);
			attribute = this.attributeService.findOne(attributeId);

			this.attributeService.delete(attribute);

			this.attributeService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateRegisterAuditor(final String username, final String name, final String surname, final String email, final String phone, final String picture, final String companyName, final String userAccountName, final String password,
		final Class<?> expected) {
		Class<?> caught;
		Auditor auditor;
		UserAccount userAccount;
		final Collection<Authority> authorities;
		final Authority authority;

		caught = null;
		try {
			auditor = this.auditorService.create();
			userAccount = this.userAccountService.create();
			authorities = new ArrayList<Authority>();
			authority = new Authority();

			authority.setAuthority("AUDITOR");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);

			auditor.setUserAccount(userAccount);
			auditor.getUserAccount().setUsername(userAccountName);
			auditor.getUserAccount().setPassword(password);
			auditor.setEmail(email);
			auditor.setName(name);
			auditor.setSurname(surname);
			auditor.setPhone(phone);
			auditor.setPicture(picture);
			auditor.setCompanyName(companyName);

			this.auditorService.save(auditor);
			this.auditorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
