
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import domain.AttributeValue;
import domain.Audit;
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
	private LessorService		lessorService;


	// Constructor --------------------------------------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD Methods ------------------------------------------------------------

	public Property create(int lessorId) {
		Property result;
		result = new Property();
		result.setAttributeValues(new ArrayList<AttributeValue>());
		result.setAudits(new ArrayList<Audit>());
		result.setBooks(new ArrayList<Book>());
		result.setIsCopy(false);
		result.setLastUpdate(new Date(System.currentTimeMillis() - 100));
		result.setLessor(lessorService.findOne(lessorId));
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

	public Property save(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(lessorService.findByPrincipal().equals(property.getLessor()));
		Property result;
		result = propertyRepository.save(property);
		return result;
	}

	public void delete(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(property.getId() != 0, "La propiedad debe estar antes en la base de datos");
		propertyRepository.exists(property.getId());
		Assert.isTrue(lessorService.findByPrincipal().equals(property.getLessor().getUserAccount()));
		Lessor lessor = property.getLessor();
		Collection<Property> properties = lessor.getLessorProperties();
		properties.remove(property);
		lessor.setLessorProperties(properties);
		lessorService.save(lessor);
		propertyRepository.delete(property);

	}

	// Other Bussiness Methods --------------------------------------------------------

	public Collection<Property> findPropertiesByLessor(Lessor lessor) {
		Collection<Property> result = propertyRepository.findPropertiesByLessorId(lessor.getId());
		return result;
	}
	
	public Property createCopy(Property property){
		Property result;
		Collection<AttributeValue> attributeValues;

		attributeValues = property.getAttributeValues();
		for (AttributeValue value : attributeValues){
			value.setId(0);
			value.setVersion(0);
		}
		
		result= this.create(property.getLessor().getId());
		result.setAddress(property.getAddress());
		result.setAttributeValues(attributeValues);
		result.setDescription(property.getDescription());
		result.setIsCopy(true);
		result.setLastUpdate(property.getLastUpdate());
		result.setName(property.getName());
		result.setRate(property.getRate());
		
		result = propertyRepository.save(result);
		
		return result;
	}

}
