
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private BookService			bookService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private FinderService		finderService;

	// Validator --------------------------------------------------------------------
	@Autowired
	private Validator			validator;


	// Constructor --------------------------------------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD Methods ------------------------------------------------------------

	public Property create(final int lessorId) {
		Property result;
		result = new Property();
		result.setAttributeValues(new ArrayList<AttributeValue>());
		result.setAudits(new ArrayList<Audit>());
		result.setBooks(new ArrayList<Book>());
		result.setLastUpdate(new Date(System.currentTimeMillis() - 100));
		result.setLessor(this.lessorService.findOne(lessorId));
		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;
		result = this.propertyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Property findOne(final int propertyId) {
		Property result;
		result = this.propertyRepository.findOne(propertyId);
		return result;
	}

	public Property save(final Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(this.lessorService.findByPrincipal().equals(property.getLessor()));
		Assert.isTrue(!property.getAddress().isEmpty());
		Assert.isTrue(!property.getName().isEmpty());
		Assert.isTrue(!property.getDescription().isEmpty());
		Assert.isTrue(property.getRate() >= 0);

		property.setLastUpdate(new Date(System.currentTimeMillis() - 100));
		Property result;
		result = this.propertyRepository.save(property);
		return result;
	}

	public void delete(final Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(property.getId() != 0, "La propiedad debe estar antes en la base de datos");
		this.propertyRepository.exists(property.getId());
		Assert.isTrue(this.lessorService.findByPrincipal().equals(property.getLessor()));
		if (!property.getBooks().isEmpty())
			this.bookService.removePropertyFromBooks(property.getBooks());
		if (!property.getAudits().isEmpty())
			this.auditService.deleteAuditsForProperty(property);

		final Lessor lessor = property.getLessor();
		final Collection<Property> properties = lessor.getLessorProperties();
		properties.remove(property);
		lessor.setLessorProperties(properties);
		this.lessorService.save(lessor);
		this.finderService.removeProperty(property);
		this.propertyRepository.delete(property);

	}
	// Other Bussiness Methods --------------------------------------------------------

	public Collection<Property> findPropertiesByLessor(final Lessor lessor) {
		final Collection<Property> result = this.propertyRepository.findPropertiesByLessorId(lessor.getId());
		return result;
	}

	public List<Property> findPropertiesByLessorByNumberOfAudits(final int lessorId) {
		//Dashboard-12
		List<Property> properties, result;

		properties = this.propertyRepository.findPropertiesByLessorId(lessorId);
		result = this.propertyRepository.findPropertiesByLessorByNumberOfAudits(lessorId);
		for (final Property p : properties)
			if (!result.contains(p))
				result.add(p);
		return result;

	}

	public List<Property> findPropertiesByLessorOrderedByRequestNumber(final int lessorId) {
		//Dashboard-13
		List<Property> properties, result;

		properties = this.propertyRepository.findPropertiesByLessorId(lessorId);
		result = this.propertyRepository.findPropertiesByLessorIdOrderedByRequestNumber(lessorId);
		for (final Property p : properties)
			if (!result.contains(p))
				result.add(p);
		return result;
	}

	public List<Property> findPropertiesByLessorWithAcceptedBooks(final int lessorId) {
		//Dashboard-14
		List<Property> properties, result;

		properties = this.propertyRepository.findPropertiesByLessorId(lessorId);
		result = this.propertyRepository.findPropertiesByLessorIdWithAcceptedBooks(lessorId);
		for (final Property p : properties)
			if (!result.contains(p))
				result.add(p);
		return result;
	}

	public List<Property> findPropertiesByLessorWithDenieBooks(final int lessorId) {
		//Dashboard-15
		List<Property> properties, result;

		properties = this.propertyRepository.findPropertiesByLessorId(lessorId);
		result = this.propertyRepository.findPropertiesByLessorIdWithDeniedBooks(lessorId);
		for (final Property p : properties)
			if (!result.contains(p))
				result.add(p);
		return result;
	}

	public List<Property> findPropertiesByLessorWithPendingBooks(final int lessorId) {
		//Dashboard-16
		List<Property> properties, result;

		properties = this.propertyRepository.findPropertiesByLessorId(lessorId);
		result = this.propertyRepository.findPropertiesByLessorIdWithPendingBooks(lessorId);
		for (final Property p : properties)
			if (!result.contains(p))
				result.add(p);
		return result;
	}

	public List<Audit> findAuditsByProperty(final Property property) {

		return this.propertyRepository.findAuditsByProperty(property.getId());
	}

	public Property reconstruct(final Property property, final BindingResult bindingResult) {
		Property result, original;

		if (property.getId() == 0) {
			final int lessorId = this.lessorService.findByPrincipal().getId();
			result = this.create(lessorId);
		} else {
			original = this.propertyRepository.findOne(property.getId());
			result = new Property();
			result.setAttributeValues(original.getAttributeValues());
			result.setAudits(original.getAudits());
			result.setBooks(original.getBooks());
			result.setLastUpdate(original.getLastUpdate());
			result.setId(original.getId());
			result.setLessor(original.getLessor());
			result.setVersion(original.getVersion());
		}
		result.setName(property.getName());
		result.setRate(property.getRate());
		result.setDescription(property.getDescription());
		result.setAddress(property.getAddress());
		this.validator.validate(result, bindingResult);
		return result;

	}

	public Collection<Property> findAllOrdered() {
		return this.propertyRepository.findAllOrdered();
	}

	public void flush() {
		this.propertyRepository.flush();
	}

}
