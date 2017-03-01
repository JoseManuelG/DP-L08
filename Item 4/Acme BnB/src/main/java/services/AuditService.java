
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import security.LoginService;
import domain.Audit;
import domain.Auditor;
import domain.Property;

@Service
@Transactional
public class AuditService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private LoginService	loginService;


	// Constructor --------------------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public Audit create() {
		Audit result;
		result = new Audit();
		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;
		result = auditRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Audit findOne(int auditId) {
		Audit result;
		result = auditRepository.findOne(auditId);
		return result;
	}

	public Audit save(Audit audit) {
		Assert.notNull(audit, "El audit no puede ser nulo");
		Audit result;

		Date currentTime = new Date(System.currentTimeMillis() - 100);
		audit.setWritingMoment(currentTime);
		
		result = auditRepository.save(audit);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Audit audit) {
		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(audit.getId() != 0, "El audit debe estar antes en la base de datos");
		auditRepository.exists(audit.getId());
		Assert.isTrue(loginService.getPrincipal().equals(audit.getAuditor().getUserAccount()));

		auditRepository.delete(audit);

	}

	// Other Bussiness Methods --------------------------------------------------------

	public Collection<Audit> findAuditsForProperty(Property property) {
		Collection<Audit> result = auditRepository.findAuditsForPropertyId(property.getId());
		return result;
	}

	public boolean checkUnique(Property property, Auditor auditor) {
		boolean result = false;
		if (auditRepository.countAuditForauditorIdAndPropertyId(auditor.getId(), property.getId()) == 0) {
			result = true;
		}
		//Assert.notNull(auditRepository.findAuditsForauditorIdAndPropertyId(auditor.getId() ,property.getId()));
		return result;
	}

	public Audit getAuditForPropertyAndAuditor(Property property, Auditor auditor) {
		Audit result;
		result = auditRepository.findAuditForauditorIdAndPropertyId(auditor.getId(), property.getId());
		return result;
	}

	public Collection<Audit> findAuditsForAuditor(Auditor auditor) {
		Collection<Audit> result = auditRepository.findAuditsForauditorId(auditor.getId());
		return result;
	}

	public int getMinimumAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getMinimumAuditsPerProperty();
	}

	public double getAverageAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getAverageAuditsPerProperty();
	}

	public int getMaximumAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getMaximumAuditsPerProperty();
	}

}
