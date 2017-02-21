package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository --------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;
	// Supporting Services --------------------------------------
	
	
	// Simple CRUD methods --------------------------------------
	public Administrator create() {
		Administrator result;
		result = new Administrator();
		

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		Assert.notNull(administratorRepository);
		result = administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(int administratorId) {
		Administrator result;

		result = administratorRepository.findOne(administratorId);

		return result;
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator,"El administrador no puede ser null");
		Administrator result;
		result = administratorRepository.save(administrator);

		return result;
	}

	public void delete(Administrator administrator) {
		Assert.notNull(administrator,"El administrador no puede ser null");
		Assert.isTrue(administrator.getId() != 0,"No puede borrar un objeto de ID=0");

		Assert.isTrue(administratorRepository.exists(administrator.getId()),"El administrador debe existir");

		administratorRepository.delete(administrator);
	}
	// other business methods --------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Administrator findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = administratorRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}

}
