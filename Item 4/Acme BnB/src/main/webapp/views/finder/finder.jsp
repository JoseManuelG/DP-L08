
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<fieldset>
	<form:form action="finder/finder.do" modelAttribute="finder">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="properties" />
		<form:hidden path="cacheMoment" />
		
		<acme:textbox code="finder.destination" path="destination"/>
		<acme:textbox code="finder.minPrice" path="minPrice"/>
		<acme:textbox code="finder.maxPrice" path="maxPrice"/>
		<acme:textbox code="finder.keyword" path="keyword"/>
		
		<acme:submit name="save" code="finder.search"/>
	</form:form>
</fieldset>

<jstl:if test="${!result.isEmpty()}">
	<display:table pagesize="5" class="displaytag" name="result" requestURI="${requestURI}" id="row">
		
		<!-- Action links -->
		
		<display:column>
			<a href="book.do?propertyId=${row.id}">
				<spring:message	code="finder.book" />
			</a>
		</display:column>
		
		<!-- Attributes -->
		
		<acme:columm sorteable="true" code="finder.property.name" path="name"/>
		
		<acme:columm sorteable="true" code="finder.property.rate" path="rate"/>
		
		<acme:columm sorteable="false" code="finder.property.description" path="description"/>
		
		<acme:columm sorteable="false" code="finder.property.address" path="address"/>
		
		<!-- TODO: añadir attributes y datos del propietario? -->
		
	</display:table>
</jstl:if>

	
<acme:cancel url="<spring:url value='/' />" code="finder.back"/>