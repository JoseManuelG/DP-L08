
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>






<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="properties" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<security:authorize access="hasRole('LESSOR')">
	<jstl:if test="${requestURI == 'property/lessor/myProperties.do'}">
			<spring:message code="property.edit.property" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="property/lessor/edit.do?propertyId=${row.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
	</jstl:if>
	</security:authorize>
	<spring:message code="property.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="property/view.do?propertyId=${row.id}">
				<spring:message	code="property.view" />
			</a>
	</display:column>
	<jstl:if test="${requestURI == 'property/list.do'}">
	    <spring:message code="property.view.lessor" var="viewTitleHeader" />
	    <display:column title="${viewTitleHeader}">
	      <a href="lessor/view.do?lessorId=${row.lessor.id}">
	      <spring:message  code="property.view" />
	    </a>
	      
	    </display:column>
  	</jstl:if>

	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="false" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>

<security:authorize access="hasRole('LESSOR')">
	<a href="property/lessor/create.do">
	      <spring:message  code="property.new" />
	</a>
</security:authorize>

