
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	@Query("select b from Book b where b.creditCard.id = ?1")
	boolean existsCreditCardForAnyBook(int creditCardId);

}
