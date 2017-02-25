
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	//Find all the propertes for a given lessor 
	@Query("select l.lessorProperties from Lessor l where l.id=?1")
	public Collection<Property> findPropertiesByLessorId(int lessorId);

	//Dashboard-12
	@Query("select p from Property p where p.lessor.id=?1 order by p.audits.size")
	public List<Property> findPropertiesByLessorByNumberOfAudits(int lessorId);

}
