
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
	name="audits" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<a href="audit/auditor/edit.do?auditId=${row.id}">
				<spring:message	code="audit.edit" />
			</a>
		</display:column>
		</security:authorize>
		
		<display:column>
			<a href="audit/view.do?auditId=${row.id}">
				<spring:message	code="audit.view" />
			</a>
		</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="audit.text" var="text" />
	<display:column property="text" title="${text}" sortable="false" />
	<display:column property="draftMode" title="${draftMode}" sortable="true" />

</display:table>

<a href="audit/auditor/create.do">
	<spring:message	code="audit.create" />
</a>
