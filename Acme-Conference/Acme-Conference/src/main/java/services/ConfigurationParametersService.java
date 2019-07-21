
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationParametersRepository;
import domain.ConfigurationParameters;

@Service
@Transactional
public class ConfigurationParametersService {

	@Autowired
	private ConfigurationParametersRepository	configurationParametersRepository;

	@Autowired
	private AdministratorService				administratorService;


	public ConfigurationParameters find() {
		final ConfigurationParameters result = (ConfigurationParameters) this.configurationParametersRepository.findAll().toArray()[0];
		return result;
	}

	public ConfigurationParameters save(final ConfigurationParameters configurationParameters) {
		this.administratorService.findByPrincipal();
		final ConfigurationParameters saved = this.configurationParametersRepository.save(configurationParameters);
		Assert.notNull(saved);
		return saved;

	}

}
