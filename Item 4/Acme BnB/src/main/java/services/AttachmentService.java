
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import security.LoginService;
import domain.Attachment;
import domain.Audit;

@Service
@Transactional
public class AttachmentService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private AttachmentRepository	attachmentRepository;


	// Supporting Services ------------------------------------------------------------
	
	@Autowired
	private LoginService	loginService;

	// Constructor --------------------------------------------------------------------

	public AttachmentService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public Attachment create() {
		Attachment result;
		result = new Attachment();
		return result;
	}

	public Collection<Attachment> findAll() {
		Collection<Attachment> result;
		result = attachmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Attachment findOne(int attachmentId) {
		Attachment result;
		result = attachmentRepository.findOne(attachmentId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Attachment save(Attachment attachment) {
		Attachment result;
		
		Assert.notNull(attachment, "El attachment no puede ser nulo");
		Assert.isTrue(loginService.getPrincipal().equals(attachment.getAudit().getAuditor().getUserAccount()));
		Assert.isTrue(attachment.getAudit().getDraftMode());
		result = attachmentRepository.save(attachment);
		return result;
	}
	
	@SuppressWarnings("static-access")
	public void delete(Attachment attachment) {
		Assert.notNull(attachment, "El attachment no puede ser nulo");
		Assert.isTrue(attachment.getId() != 0, "El attachment debe estar antes en la base de datos");
		Assert.isTrue(attachment.getAudit().getDraftMode());
		attachmentRepository.exists(attachment.getId());
		Assert.isTrue(loginService.getPrincipal().equals(attachment.getAudit().getAuditor().getUserAccount()));

		attachmentRepository.delete(attachment);

	}

	

	// Other Bussiness Methods --------------------------------------------------------

	
	public Collection<Attachment> findAllAttachmentsByAudit(Audit audit) {
		Collection<Attachment> result;
		result = attachmentRepository.findAllAttachmentsByAuditId(audit.getId());
		return result;
	}
}
