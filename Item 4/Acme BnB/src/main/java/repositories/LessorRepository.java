package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Book;
import domain.Lessor;

@Repository
public interface LessorRepository extends JpaRepository<Lessor,Integer> {
	
	
	@Query("select case when count(l) > 0 then true else false end from Lessor l where l.creditCard.id = ?1")
	boolean existsCreditCardForAnyLessor(int creditCardId);

	@Query("select l from Lessor l where l.userAccount.id = ?1")
	Lessor findByUserAccount(int id);

	@Query("select l.books from Lessor l where l.id = ?1")
	Collection<Book> findAllBooksByPrincipal(int id);
	
	@Query("select count (b) from Book b where b.tenant.id = ?1 and b.lessor.id= ?2")
	int findAllBooksByTennantAndLessor(int tenantId, int lessorId);

}
