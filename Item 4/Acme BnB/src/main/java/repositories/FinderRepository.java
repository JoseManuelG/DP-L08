
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Property;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Tenant t join t.finder f where t.id=?1")
	Finder findByTenant(int tenantId);

	@Query("select p from Property p where p.address like '%?1%' and p.rate>=?3 and " + "(p.address like '%?2%' or p.name like '%?2%' or p.description like '%?2')")
	Collection<Property> searchPropertiesWithoutMaxPrice(String destination, String keyword, Double min);

	@Query("select p from Property p where p.address like '%?1%' and p.rate>=?3 and " + "(p.address like '%?2%' or p.name like '%?2%' or p.description like '%?2') " + "and p.rate<=?4")
	Collection<Property> searchPropertiesWithMaxPrice(String destination, String keyword, Double min, Double maxPrice);

}
