package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lessor;

@Repository
public interface LessorRepository extends JpaRepository<Lessor,Integer> {
	
	@Query("select l from Lessor l where l.creditCard.id = ?1")
	boolean existsCreditCardForAnyLessor(int creditCardId);

}
