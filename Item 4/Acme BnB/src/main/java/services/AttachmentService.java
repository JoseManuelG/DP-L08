
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import domain.Attachment;

@Service
@Transactional
public class AttachmentService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private AttachmentRepository	attachmentRepository;


	// Supporting Services ------------------------------------------------------------
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

	public Attachment save(Attachment attachment) {
		Assert.notNull(attachment, "La tarjeta de crédito no puede ser nula");
		Attachment result;
		result = attachmentRepository.save(attachment);
		return result;
	}

	// Other Bussiness Methods --------------------------------------------------------

}
