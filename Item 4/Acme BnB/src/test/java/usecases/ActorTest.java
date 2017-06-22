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

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import services.BookService;
import services.CommentService;
import services.PropertyService;
import services.SocialIdentityService;
import services.TenantService;
import utilities.AbstractTest;
import domain.Actor;
import domain.SocialIdentity;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private SocialIdentityService socialIdentityService;

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private BookService		bookService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------


	@Test
	public void createSocialIdentityPositiveTest() {
		this.createSocialIdentitytemplate("tenant1", "http://www.test.com", "myNick", "testNet", null);
	}

	@Test
	public void createSocialIdentityNegativeTest() {
		this.createSocialIdentitytemplate("tenant1", "noURL", "myNick", "testNet", ConstraintViolationException.class);
	}
	
	@Test
	public void editSocialIdentityPositiveTest() {
		this.editSocialIdentitytemplate("lessor1", 123, "http://www.test.com", "myNick", "testNet", null);
	}

	@Test
	public void editSocialIdentityNegativeTest() {
		this.editSocialIdentitytemplate("lessor1", 123, "http://www.test.com", "", "testNet", ConstraintViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void createSocialIdentitytemplate(final String actor, final String link, final String nick, final String socialNetwork, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate("tenant1");
			SocialIdentity socialIdentity;
			Actor principal;
			
			socialIdentity = socialIdentityService.create();
			principal = actorService.findByPrincipal(); 
			socialIdentity.setActor(principal);
			socialIdentity.setLink(link);
			socialIdentity.setNick(nick);
			socialIdentity.setSocialNetwork(socialNetwork);
			socialIdentityService.save(socialIdentity);
			socialIdentityService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void editSocialIdentitytemplate(final String actor, final int id, final String link, final String nick, final String socialNetwork, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate("tenant1");
			SocialIdentity socialIdentity;
			Actor principal;
			
			socialIdentity = socialIdentityService.findOne(id);
			principal = actorService.findByPrincipal(); 
			socialIdentity.setActor(principal);
			socialIdentity.setLink(link);
			socialIdentity.setNick(nick);
			socialIdentity.setSocialNetwork(socialNetwork);
			socialIdentityService.save(socialIdentity);
			socialIdentityService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
