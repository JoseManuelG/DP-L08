
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

}
