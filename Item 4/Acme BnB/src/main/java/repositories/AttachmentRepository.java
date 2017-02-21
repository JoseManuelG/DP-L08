
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attachment;
import domain.Auditor;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
	
	@Query("select a from Attachment a where a.audit.id = ?1")
	Collection<Attachment> findAllAttachmentsByAuditId(int id);
}
