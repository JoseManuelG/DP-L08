<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message  code="property.name" />: <jstl:out value="${property.name}" />
<br>
<spring:message  code="property.rate" />: <jstl:out value="${property.rate}"/>
<br>
<spring:message  code="property.description" />: <jstl:out value="${property.description}"/>
<br>
<spring:message  code="property.address" />: <jstl:out value="${property.address}"/>
<br>

<jstl:if test="${!results.isEmpty()}">
	<display:table pagesize="5" class="displaytag" name="attributeValues"  id="row">
		
		<!-- Action links -->
		<jstl:if test="${ esMiProperty}">
		<display:column>
			<a href="attributeValue/lessor/edit.do?attributeValueId=${row.id}">
				<spring:message	code="attributeValue.edit" />
			</a>
	
		</display:column>
			</jstl:if>
		<!-- Attributes -->
		
		<acme:column sorteable="false" code="attribute.name" path="attribute.name"/>
		
		<acme:column sorteable="false" code="attributeValue.value" path="value"/>
		
	</display:table>
			<jstl:if test="${ esMiProperty}">
			<a href=attributeValue/lessor/create.do?propertyId=${property.id }>
	      <spring:message  code="attributeValue.create" />
	</a>
			</jstl:if>
</jstl:if>

<display:table pagesize="5" class="displaytag" name="audits"  id="row">
		
		<!-- Action links -->
		
		<spring:message code="property.view.audit" var="auditHeader" />
			<display:column title="${auditHeader}">
			<a href="audit/view.do?auditId=${row.id}">
				<spring:message	code="audit.view" />
			</a>
	
		</display:column>
			
		<!-- Attributes -->
		
		<acme:column sorteable="false" code="audit.text" path="text"/>
		
		<acme:column sorteable="false" code="audit.auditor.name" path="auditor.name"/>
		
		<acme:column sorteable="false" code="audit.auditor.companyName" path="auditor.companyName"/>
		
	</display:table>

<security:authorize access="hasRole('AUDITOR')">
		
			<a href="audit/auditor/create.do?propertyId=${property.id}">
				<spring:message	code="audit.create" />
			</a>
		
		</security:authorize>
	