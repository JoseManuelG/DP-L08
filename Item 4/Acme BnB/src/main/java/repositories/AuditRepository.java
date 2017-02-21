
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//Find all the audits for a given property 
	@Query("select p.audits from Property p where p.id=?1")
	public Collection<Audit> findAuditsForPropertyId(int propertyId);

	@Query("select a.audits from Auditor a where a.id=?1")
	public Collection<Audit> findAuditsForauditorId(int auditorId);
	
	@Query("select count(a) from Audit a where a.auditor.id=?1 and a.property.id=?2 ")
	public int countAuditForauditorIdAndPropertyId(int auditorId, int propertyId);
	
	@Query("select a from Audit a where a.auditor.id=?1 and a.property.id=?2 ")
	public Audit findAuditForauditorIdAndPropertyId(int auditorId, int propertyId);

}
