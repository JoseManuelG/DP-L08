
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import security.LoginService;
import domain.AttributeValue;
import domain.Book;
import domain.Lessor;
import domain.Property;

@Service
@Transactional
public class PropertyService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private PropertyRepository	propertyRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private LoginService		loginService;

	@Autowired
	private LessorService		lessorService;


	// Constructor --------------------------------------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD Methods ------------------------------------------------------------

	public Property create() {
		Property result;
		result = new Property();
		result.setAttributeValues(new ArrayList<AttributeValue>());
		result.setAudits(new ArrayList<Audits>());
		result.setBooks(new ArrayList<Book>());
		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;
		result = propertyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Property findOne(int propertyId) {
		Property result;
		result = propertyRepository.findOne(propertyId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Property save(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.notNull(loginService.getPrincipal(), "El getprincipal no puede ser nulo");
		Property result;
		result = propertyRepository.save(property);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(property.getId() != 0, "La propiedad debe estar antes en la base de datos");
		propertyRepository.exists(property.getId());
		Assert.isTrue(loginService.getPrincipal().equals(property.getLessor().getUserAccount()));
		Lessor lessor = property.getLessor();
		Collection<Property> properties = lessor.getProperties();
		properties.remove(property);
		lessor.setProperties(properties);
		lessorService.save(lessor);
		propertyRepository.delete(property);

	}

	// Other Bussiness Methods --------------------------------------------------------

}
