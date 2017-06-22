
package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.LessorService;
import services.TenantService;
import services.UserAccountService;
import utilities.AbstractTest;
import domain.Lessor;
import domain.Tenant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorTest extends AbstractTest {

	@Autowired
	private LessorService		lessorService;
	@Autowired
	private TenantService		tenantService;
	@Autowired
	private UserAccountService	userAccountService;


	//Caso de uso de registrarse como lessor
	//test positivo
	@Test
	public void registerLessor1() {
		this.templateRegisterLessor("name", "surname", "email@email.com", "+123456789", "http://www.photo.com", "LessorTest", "LessorTest", null);
	}
	// nombre nulo
	@Test
	public void registerLessor2() {
		this.templateRegisterLessor(null, "surname", "email@email.com", "+123456789", "http://www.photo.com", "LessorTest", "LessorTest", ConstraintViolationException.class);
	}
	// apellidos nulo
	@Test
	public void registerLessor3() {
		this.templateRegisterLessor("name", null, "email@email.com", "+123456789", "http://www.photo.com", "LessorTest", "LessorTest", ConstraintViolationException.class);
	}
	// email nulo
	@Test
	public void registerLessor4() {
		this.templateRegisterLessor("name", "surname", null, "+123456789", "http://www.photo.com", "LessorTest", "LessorTest", ConstraintViolationException.class);
	}
	// foto de perfil nula
	@Test
	public void registerLessor6() {
		this.templateRegisterLessor("name", "surname", "email@email.com", "123456789", null, "LessorTest", "LessorTest", ConstraintViolationException.class);
	}
	// nombre cuenta nulo
	@Test
	public void registerLessor7() {
		this.templateRegisterLessor("name", "surname", "email@email.com", "123456789", "http://www.photo.com", null, "LessorTest", ConstraintViolationException.class);
	}
	// nombre cuenta repetido
	@Test
	public void registerLessor8() {
		this.templateRegisterLessor("name", "surname", "email@email.com", "123456789", "http://www.photo.com", "lessor1", "LessorTest", DataIntegrityViolationException.class);
	}
	// contraseña nula
	@Test
	public void registerLessor9() {
		this.templateRegisterLessor("name", "surname", "email@email.com", "123456789", "http://www.photo.com", "LessorTest", null, ConstraintViolationException.class);
	}
	//Caso de uso de registrarse como tenant
	//test positivo
	@Test
	public void registerTenant1() {
		this.templateRegisterTenant("name", "surname", "email@email.com", "+123456789", "http://www.photo.com", "auditorTest", "auditorTest", null);
	}
	// nombre nulo
	@Test
	public void registerTenant2() {
		this.templateRegisterTenant(null, "surname", "email@email.com", "+123456789", "http://www.photo.com", "auditorTest", "auditorTest", ConstraintViolationException.class);
	}
	// apellidos nulo
	@Test
	public void registerTenant3() {
		this.templateRegisterTenant("name", null, "email@email.com", "+123456789", "http://www.photo.com", "auditorTest", "auditorTest", ConstraintViolationException.class);
	}
	// email nulo
	@Test
	public void registerTenant4() {
		this.templateRegisterTenant("name", "surname", null, "+123456789", "http://www.photo.com", "auditorTest", "auditorTest", ConstraintViolationException.class);
	}
	// foto de perfil nula
	@Test
	public void registerTenant6() {
		this.templateRegisterTenant("name", "surname", "email@email.com", "123456789", null, "auditorTest", "auditorTest", ConstraintViolationException.class);
	}
	// nombre cuenta nulo
	@Test
	public void registerTenant7() {
		this.templateRegisterTenant("name", "surname", "email@email.com", "123456789", "http://www.photo.com", null, "auditorTest", ConstraintViolationException.class);
	}
	// nombre cuenta repetido
	@Test
	public void registerTenant8() {
		this.templateRegisterTenant("name", "surname", "email@email.com", "123456789", "http://www.photo.com", "tenant1", "auditorTest", DataIntegrityViolationException.class);
	}
	// contraseña nuña
	@Test
	public void registerTenant9() {
		this.templateRegisterTenant("name", "surname", "email@email.com", "123456789", "http://www.photo.com", "auditorTest", null, ConstraintViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateRegisterLessor(final String name, final String surname, final String email, final String phone, final String picture, final String userAccountName, final String password, final Class<?> expected) {
		Class<?> caught;
		Lessor lessor;
		UserAccount userAccount;
		final Collection<Authority> authorities;
		final Authority authority;

		caught = null;
		try {
			lessor = this.lessorService.create();
			userAccount = this.userAccountService.create();
			authorities = new ArrayList<Authority>();
			authority = new Authority();

			authority.setAuthority("LESSOR");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);

			lessor.setUserAccount(userAccount);
			lessor.getUserAccount().setUsername(userAccountName);
			lessor.getUserAccount().setPassword(password);
			lessor.setEmail(email);
			lessor.setName(name);
			lessor.setSurname(surname);
			lessor.setPhone(phone);
			lessor.setPicture(picture);
			lessor.setTotalFee(0);

			this.lessorService.save(lessor);
			this.lessorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateRegisterTenant(final String name, final String surname, final String email, final String phone, final String picture, final String userAccountName, final String password, final Class<?> expected) {
		Class<?> caught;
		Tenant tenant;
		UserAccount userAccount;
		final Collection<Authority> authorities;
		final Authority authority;

		caught = null;
		try {
			tenant = this.tenantService.create();
			userAccount = this.userAccountService.create();
			authorities = new ArrayList<Authority>();
			authority = new Authority();

			authority.setAuthority("LESSOR");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);

			tenant.setUserAccount(userAccount);
			tenant.getUserAccount().setUsername(userAccountName);
			tenant.getUserAccount().setPassword(password);
			tenant.setEmail(email);
			tenant.setName(name);
			tenant.setSurname(surname);
			tenant.setPhone(phone);
			tenant.setPicture(picture);

			this.tenantService.save(tenant);
			this.tenantService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
