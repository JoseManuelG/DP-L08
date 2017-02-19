
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.Property;

@Service
@Transactional
public class FinderService {

	// Managed Repository --------------------------------------
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService		actorService;


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
		Finder result, old;
		Double min, max;
		Collection<Property> results;

		Assert.notNull(finder);

		if (finder.getMaxPrice() != null && finder.getMinPrice() != null) {
			Assert.isTrue(finder.getMaxPrice() >= finder.getMinPrice());
		}

		old = finderRepository.findOne(finder.getId());

		result = finder;

		if (!(finder.getDestination().equals(old.getDestination()) && finder.getKeyword().equals(old.getKeyword()) && finder.getMaxPrice() == old.getMaxPrice() && finder.getMinPrice() == old.getMinPrice())) {
			result.setCacheMoment(new Date(System.currentTimeMillis() - 100));
			if (result.getMinPrice() == null) {
				min = 0.0;
			} else {
				min = result.getMinPrice();
			}
			if (result.getMaxPrice() == null) {
				results = finderRepository.searchPropertiesWithoutMaxPrice(result.getDestination(), result.getKeyword(), min);
			} else {
				results = finderRepository.searchPropertiesWithMaxPrice(result.getDestination(), result.getKeyword(), min, result.getMaxPrice());
			}

			result.setResults(results);

			result = finderRepository.save(result);
		}

		Assert.notNull(result);

		return result;
	}

	public void delete(Finder finder) {
		Assert.notNull(finder, "finder.error.null");

		Assert.isTrue(finderRepository.exists(finder.getId()), "finder.error.exists");

		finderRepository.delete(finder);
	}

	// Other business methods --------------------------------------

	public Finder findByPrincipal() {
		Finder result;

		result = finderRepository.findByTenant(actorService.findByPrincipal().getId());

		return result;
	}
}
