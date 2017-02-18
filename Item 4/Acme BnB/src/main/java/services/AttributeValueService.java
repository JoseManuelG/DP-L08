
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttributeValueRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.AttributeValue;
import domain.Property;

@Service
@Transactional
public class AttributeValueService {

	//Managed Repository---------------------------—

	@Autowired
	private AttributeValueRepository	attributeValueRepository;

	//Supporting services---------------------------—
	@Autowired
	private LoginService				loginService;


	//Constructors------------------------------------

	public AttributeValueService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public AttributeValue create() {
		AttributeValue result;

		result = new AttributeValue();

		return result;
	}

	public Collection<AttributeValue> findAll() {
		Collection<AttributeValue> result;

		result = attributeValueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public AttributeValue findOne(int attributeValueId) {
		AttributeValue result;

		result = attributeValueRepository.findOne(attributeValueId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public AttributeValue save(AttributeValue attributeValue) {
		Assert.hasText(attributeValue.getValue(), "El atributeValue debe tener un valor");
		UserAccount account = loginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().contains(Authority.ADMINISTRATOR), "Debes ser un Administrador para editar los AttributeValues");

		AttributeValue result;
		result = attributeValueRepository.save(attributeValue);

		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(AttributeValue attributeValue) {
		Assert.notNull(attributeValue, "El attributeValueo no puede ser nulo");
		Assert.isTrue(attributeValue.getId() != 0, "El attributeValueo debe estar en la base de datos");
		UserAccount account = loginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().contains(Authority.ADMINISTRATOR), "Debes ser un Administrador para editar los AttributeValues");

		attributeValueRepository.delete(attributeValue);
	}

	// Other bussiness methods ----------------------------------------------------------------------------------------
	public Collection<AttributeValue> findAttributeValuesForProperty(Property property) {
		Collection<AttributeValue> result = attributeValueRepository.findAttributeValuesForPropertyId(property.getId());
		return result;
	}

}
