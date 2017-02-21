
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	destination;
	private Double	minPrice;
	private Double	maxPrice;
	private String	keyword;
	private Date	cacheMoment;


	@NotBlank
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Min(0)
	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	//TODO: Problema: en el dominio hemos puesto que como mínimo el valor de  
	//		este atributo debe tomar el valor minPrice, pero debe ser una constante.
	//		Solucion: Poner minimo 0 y controlarlo en servicios.
	@Min(0)
	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCacheMoment() {
		return cacheMoment;
	}

	public void setCacheMoment(Date cacheMoment) {
		this.cacheMoment = cacheMoment;
	}


	//Relationships---------------------------

	private Collection<Property>	results;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Property> getResults() {
		return results;
	}

	public void setResults(Collection<Property> results) {
		this.results = results;
	}

}
