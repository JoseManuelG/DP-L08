
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository --------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;


	//Supporting Services --------------------------------------
	//Simple CRUD methods --------------------------------------
	public Configuration create() {
		Configuration result;
		result = new Configuration();
		return result;
	}
	public Collection<Configuration> findAll() {
		Collection<Configuration> result;
		Assert.notNull(configurationRepository);
		result = configurationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Configuration findOne() {
		Configuration result = null;
		Collection<Configuration> configurations = configurationRepository.findAll();
		for (Configuration configuration : configurations) {
			result = configuration;
			break;
		}
		return result;
	}

	public Configuration save(Configuration configuration) {
		Assert.notNull(configuration);
		Configuration result;
		Assert.notNull(configuration.getFee());
		Assert.isTrue(configuration.getFee() >= 0);
		result = configurationRepository.save(configuration);
		return result;
	}

	//other business methods --------------------------------------

}
