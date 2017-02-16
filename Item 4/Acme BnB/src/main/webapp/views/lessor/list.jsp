
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
	name="lessors" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
		<display:column>
			<a href="lessor/edit.do?lessorId=${row.id}">
				<spring:message	code="lessor.edit" />
			</a>
		</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="lessor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />
	
	<spring:message code="lessor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false" />
	
	<spring:message code="lessor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false" />

	<spring:message code="lessor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false" />
	
	<spring:message code="lessor.totalFee" var="totalFeeHeader" />
	<display:column property="totalFee" title="${totalFeeHeader}" sortable="false" />
	
</display:table>

