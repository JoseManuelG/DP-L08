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

import services.AuditService;
import services.AuditorService;
import services.PropertyService;
import utilities.AbstractTest;
import domain.Audit;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private PropertyService	propertyService;
	@Autowired
	private AuditService	auditService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de crear retalium con titulo longitud minima:
	//test positivo
	@Test
	public void CreateAudit1() {
		this.templateCreateAudit("auditor1", 57, "Test", true, null);
	}
	//Caso de uso de crear retalium con titulo longitud minima:
	//test positivo
	@Test
	public void CreateAudit2() {
		this.templateCreateAudit("auditor1", 57, "Test", false, null);
	}

	//Caso de uso de registrar property con name blanco:
	//test negativo
	@Test
	public void CreateAudit3() {
		this.templateCreateAudit(null, 57, "Test", true, IllegalArgumentException.class);
	}
	//Caso de uso de registrar property con descripcion blanco:
	//test negativo
	@Test
	public void CreateAudit4() {
		this.templateCreateAudit("chorbi1", 57, "Test", true, IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateAudit(final String username, final int propertyId, final String text, final Boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		Audit audit;

		caught = null;
		try {
			this.authenticate(username);
			audit = this.auditService.create(propertyId);
			audit.setText(text);
			audit.setDraftMode(draftMode);
			audit = this.auditService.save(audit);
			this.auditService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
