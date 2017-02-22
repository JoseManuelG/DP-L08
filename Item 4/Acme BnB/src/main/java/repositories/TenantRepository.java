
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Book;
import domain.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

	@Query("select t from Tenant t where t.userAccount.id = ?1")
	Tenant findByUserAccount(int id);

	@Query("select t.books from Tenant t where t.id = ?1")
	Collection<Book> findAllBooksByPrincipal(int id);
	
	@Query("select count (b) from Book b where b.tenant.id = ?1 and b.lessor.id= ?2")
	int findAllBooksByTennantAndLessor(int tenantId, int lessorId);

}
