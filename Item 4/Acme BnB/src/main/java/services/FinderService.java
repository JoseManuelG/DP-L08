
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed Repository --------------------------------------
	@Autowired
	private FinderRepository	finderRepository;


	// Supporting Services --------------------------------------

	// Simple CRUD methods --------------------------------------
	public Finder create() {
		Finder result;

		result = new Finder();

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(int finderId) {
		Finder result;

		result = finderRepository.findOne(finderId);

		return result;
	}

	public Finder save(Finder finder) {
		Finder result;

		Assert.notNull(finder, "finder.error.null");
		result = finderRepository.save(finder);
		Assert.notNull(result, "finder.error.commit");

		return result;
	}

	public void delete(Finder finder) {
		Assert.notNull(finder, "finder.error.null");

		Assert.isTrue(finderRepository.exists(finder.getId()), "finder.error.exists");

		finderRepository.delete(finder);
	}

	// Other business methods --------------------------------------
}
