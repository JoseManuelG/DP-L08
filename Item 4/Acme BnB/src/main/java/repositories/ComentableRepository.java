
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comentable;

@Repository
public interface ComentableRepository extends JpaRepository<Comentable, Integer> {

	@Query("select a from Comentable a where a.userAccount.id = ?1")
	Comentable findByUserAccountId(int id);

}
