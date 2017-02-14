package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Lessor;

@Repository
public interface LessorRepository extends JpaRepository<Lessor,Integer> {
	
//	//Returns the credit card's lessor
//	@Query("select cc.lessor from CreditCard cc where cc.id = ?1")
//	public Lessor getLessorByCreditCardId(int creditCardId);

}
