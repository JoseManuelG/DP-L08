
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="books" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<security:authorize access="hasRole('TENANT')">
		<spring:message code="book.lessor" var="lessor" />
		<display:column title="lessor" sortable="true">
			<a href="lessor/view.do?lessorId=${row.lessor.id}">
			<spring:message code="book.view" var="lessor" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('LESSOR')">
		<spring:message code="book.tenant" var="tenant" />
		<display:column title="tenant" sortable="true">
			<a href="lessor/view.do?tenantId=${row.lessor.id}">
			<spring:message code="book.view"/></a>
		</display:column>
	</security:authorize>
	
	<acme:column sorteable="true" code="book.property.name" path="property.name"/>
	
	<acme:column sorteable="true" code="book.checkin" path="checkInDate"/>
	
	<acme:column sorteable="true" code="book.checkout" path="checkOutDate"/>
	
	<acme:column sorteable="true" code="book.smoker" path="smoker"/>
	
	<acme:column sorteable="true" code="book.state" path="state"/>
	
	<acme:column sorteable="true" code="book.amount" path="totalAmount"/>

	<security:authorize access="hasRole('TENANT')">
		<spring:message code="book.credit.card" var="CCName" />
		<display:column title="CCName" sortable="true">
			<jstl:out value="${maskedCard}"/>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('LESSOR')">
		<display:column>
			<jstl:if test="${row.state eq 'PENDING'}">
				<a href="book/lessor/acceptBook.do?bookId=${row.id}">
					<spring:message	code="book.accept" /></a> |
				<a href="book/lessor/denyBook.do?bookId=${row.id}">
					<spring:message	code="book.deny" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	

</display:table>
