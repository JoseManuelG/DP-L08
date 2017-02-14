
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttributeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attribute;
import domain.AttributeValue;

@Service
@Transactional
public class AttributeService {

	//Managed Repository---------------------------—

	@Autowired
	private AttributeRepository	attributeRepository;

	//Supporting services---------------------------—
	@Autowired
	private LoginService		loginService;


	//Constructors------------------------------------

	public AttributeService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public Attribute create() {
		Attribute result;

		result = new Attribute();
		Collection<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
		result.setAttributeValues(attributeValues);
		return result;
	}

	public Collection<Attribute> findAll() {
		Collection<Attribute> result;

		result = attributeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Attribute findOne(int attributeId) {
		Attribute result;

		result = attributeRepository.findOne(attributeId);
		Assert.notNull(result);

		return result;
	}

	public Attribute save(Attribute attribute) {
		Assert.hasText(attribute.getName(), "El atributo debe tener un nombre");
		UserAccount account = loginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().contains(Authority.ADMINISTRATOR), "Debes ser un Administrador para editar los Attributes");

		Attribute result;
		result = attributeRepository.save(attribute);

		return result;
	}

	public void delete(Attribute attribute) {
		Assert.notNull(attribute, "El attributeo no puede ser nulo");
		Assert.isTrue(attribute.getId() != 0, "El attributeo debe estar en la base de datos");
		UserAccount account = loginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().contains(Authority.ADMINISTRATOR), "Debes ser un Administrador para editar los Attributes");

		attributeRepository.delete(attribute);
	}

}
