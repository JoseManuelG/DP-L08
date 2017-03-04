
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
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
	private ActorService	actorService;


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
		Audit result;

		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(actorService.findByPrincipal().equals(audit.getAuditor()));
		if (audit.getId() != 0) {
			Assert.isTrue(audit.getDraftMode());
		}

		Date currentTime = new Date(System.currentTimeMillis() - 100);
		audit.setWritingMoment(currentTime);
		audit.setDraftMode(false);
		audit.getProperty().getAudits().remove(findOne(audit.getId()));

		result = auditRepository.save(audit);
		return result;
	}

	public Audit saveDraft(Audit audit) {
		Audit result;

		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(actorService.findByPrincipal().equals(audit.getAuditor()));
		if (audit.getId() != 0) {
			Assert.isTrue(audit.getDraftMode());
		}

		Date currentTime = new Date(System.currentTimeMillis() - 100);
		audit.setWritingMoment(currentTime);
		audit.setDraftMode(true);
		audit.getProperty().getAudits().remove(findOne(audit.getId()));

		result = auditRepository.save(audit);
		return result;
	}

	public void delete(Audit audit) {
		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(audit.getId() != 0, "El audit debe estar antes en la base de datos");
		Assert.isTrue(audit.getDraftMode());
		auditRepository.exists(audit.getId());
		Assert.isTrue(actorService.findByPrincipal().equals(audit.getAuditor()));
		audit.getProperty().getAudits().remove(findOne(audit.getId()));
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

	public Integer getMinimumAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getMinimumAuditsPerProperty();
	}

	public Double getAverageAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getAverageAuditsPerProperty();
	}

	public Integer getMaximumAuditsPerProperty() {
		//Dashboard-21
		return auditRepository.getMaximumAuditsPerProperty();
	}

	public void deleteAuditsForProperty(Property property) {
		Collection<Audit> audits = auditRepository.findAuditsForPropertyId(property.getId());
		for (Audit a : audits) {
			auditRepository.delete(a);
		}
	}

}
