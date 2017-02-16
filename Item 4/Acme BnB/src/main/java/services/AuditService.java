
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
		Assert.notNull(audit, "La tarjeta de crédito no puede ser nula");
		Audit result;
		if (audit.getId() == 0) {
			Date currentTime = new Date(System.currentTimeMillis());
			audit.setWritingMoment(currentTime);
		}

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

}
