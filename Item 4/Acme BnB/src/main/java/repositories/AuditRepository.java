package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Integer>{
	

}
