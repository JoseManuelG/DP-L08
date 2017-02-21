
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


<form:form action="attributeValue/lessor/edit.do" modelAttribute="attributeValue">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="property" />
		
		<jstl:if test="${id ne 0 }">
		<acme:select items="${attributes}" itemLabel="name" code="attributeValue.attribute" path="attribute"/>
		</jstl:if>	
		<jstl:if test="${id eq 0 }">
		<form:hidden path="attribute" />
		</jstl:if>
	
	<acme:textbox code="attributeValue.value" path="value"/>
	<br />
		
	<acme:submit name="save" code="attributeValue.save"/>
	
	<jstl:if test="${attribute.id eq 0}">
		<acme:submit name="delete" code="attributeValue.delete"/>
	</jstl:if>
	<acme:cancel url="javascript:window.location.href='property/view.do?propertyId=${property.id }'" code="attributeValue.cancel"/>
	<br>
</form:form>
