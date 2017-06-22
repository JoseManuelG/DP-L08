
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Attachment;
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

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private Validator		validator;


	// Constructor --------------------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public Audit create(final int propertyId) {
		Audit result;
		Auditor principal;
		Property property;
		Date currentMoment;

		currentMoment = new Date(System.currentTimeMillis() - 100);
		principal = (Auditor) this.actorService.findByPrincipal();
		property = this.propertyService.findOne(propertyId);

		result = new Audit();
		result.setAuditor(principal);
		result.setProperty(property);
		result.setDraftMode(true);
		result.setWritingMoment(currentMoment);
		result.setAttachments(new HashSet<Attachment>());

		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;
		result = this.auditRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Audit findOne(final int auditId) {
		Audit result;
		result = this.auditRepository.findOne(auditId);
		return result;
	}

	public Audit save(final Audit audit) {
		Audit result;

		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(this.actorService.findByPrincipal().equals(audit.getAuditor()));
		if (audit.getId() != 0)
			Assert.isTrue(audit.getDraftMode());

		final Date currentTime = new Date(System.currentTimeMillis() - 100);
		audit.setWritingMoment(currentTime);
		audit.setDraftMode(false);
		//		audit.getProperty().getAudits().remove(findOne(audit.getId()));

		result = this.auditRepository.save(audit);
		return result;
	}

	public Audit saveDraft(final Audit audit) {
		Audit result;

		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(this.actorService.findByPrincipal().equals(audit.getAuditor()));
		if (audit.getId() != 0)
			Assert.isTrue(audit.getDraftMode());

		final Date currentTime = new Date(System.currentTimeMillis() - 100);
		audit.setWritingMoment(currentTime);
		audit.setDraftMode(true);
		//		audit.getProperty().getAudits().remove(findOne(audit.getId()));

		result = this.auditRepository.save(audit);
		return result;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit, "El audit no puede ser nulo");
		Assert.isTrue(audit.getId() != 0, "El audit debe estar antes en la base de datos");
		Assert.isTrue(audit.getDraftMode());
		this.auditRepository.exists(audit.getId());
		Assert.isTrue(this.actorService.findByPrincipal().equals(audit.getAuditor()));
		audit.getProperty().getAudits().remove(this.findOne(audit.getId()));
		this.auditRepository.delete(audit);

	}

	public void flush() {
		this.auditRepository.flush();

	}

	// Other Bussiness Methods --------------------------------------------------------

	public Collection<Audit> findAuditsForProperty(final Property property) {
		final Collection<Audit> result = this.auditRepository.findAuditsForPropertyId(property.getId());
		return result;
	}

	public boolean checkUnique(final int propertyId, final Auditor auditor) {
		boolean result;

		result = this.auditRepository.countAuditForauditorIdAndPropertyId(auditor.getId(), propertyId) == 0;

		//Assert.notNull(auditRepository.findAuditsForauditorIdAndPropertyId(auditor.getId() ,property.getId()));
		return result;
	}

	public boolean checkUniqueOrDraft(final int propertyId, final Auditor auditor) {
		boolean result;
		Audit audit;

		audit = this.getAuditForPropertyAndAuditor(this.propertyService.findOne(propertyId), auditor);
		result = !(this.auditRepository.countAuditForauditorIdAndPropertyId(auditor.getId(), propertyId) == 1 && !audit.getDraftMode());

		return result;
	}

	public Audit getAuditForPropertyAndAuditor(final Property property, final Auditor auditor) {
		Audit result;
		result = this.auditRepository.findAuditForauditorIdAndPropertyId(auditor.getId(), property.getId());
		return result;
	}

	public Collection<Audit> findAuditsForAuditor(final Auditor auditor) {
		final Collection<Audit> result = this.auditRepository.findAuditsForauditorId(auditor.getId());
		return result;
	}

	public Integer getMinimumAuditsPerProperty() {
		//Dashboard-21
		return this.auditRepository.getMinimumAuditsPerProperty();
	}

	public Double getAverageAuditsPerProperty() {
		//Dashboard-21
		return this.auditRepository.getAverageAuditsPerProperty();
	}

	public Integer getMaximumAuditsPerProperty() {
		//Dashboard-21
		return this.auditRepository.getMaximumAuditsPerProperty();
	}

	public void deleteAuditsForProperty(final Property property) {
		final Collection<Audit> audits = this.auditRepository.findAuditsForPropertyId(property.getId());
		for (final Audit a : audits)
			this.auditRepository.delete(a);
	}

	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result, original;

		if (audit.getId() == 0)
			result = this.create(audit.getProperty().getId());
		else {
			original = this.findOne(audit.getId());
			result = new Audit();
			result.setAttachments(original.getAttachments());
			result.setAuditor(original.getAuditor());
			result.setDraftMode(original.getDraftMode());
			result.setProperty(original.getProperty());
			result.setId(original.getId());
			result.setVersion(original.getVersion());
			result.setWritingMoment(original.getWritingMoment());
		}
		result.setText(audit.getText());

		this.validator.validate(result, binding);

		return result;
	}

}
