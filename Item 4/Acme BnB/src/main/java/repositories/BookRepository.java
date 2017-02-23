
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	@Query("select case when count(b) > 0 then true else false end from Book b where b.creditCard.id = ?1")
	public boolean existsCreditCardForAnyBook(int creditCardId);

	//Find all the books for a given property 
	@Query("select p.books from Property p where p.id=?1")
	public Collection<Book> findBooksForPropertyId(int propertyId);

}
