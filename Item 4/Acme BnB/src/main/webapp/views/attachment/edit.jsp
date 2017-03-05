
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


<form:form action="attachment/auditor/edit.do" modelAttribute="attachment">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="audit" />
	
	<form:label path="name">
		<spring:message code="attachment.name" />:
	</form:label>
	<form:input path="name" />
		<form:errors cssClass="error" path="name" />
	<br />
	
		<form:label path="url">
		<spring:message code="attachment.url" />:
	</form:label>
	<form:input path="url" />
		<form:errors cssClass="error" path="url" />
	<br />
	<br />

	<acme:submit name="save" code="attachment.save"/>
	
	<jstl:if test="${stepHint.id != 0}">
		<acme:submit name="delete" code="attachment.delete"/>
	</jstl:if>
	
	<acme:cancel url="audit/view.do?auditId=${attachment.audit.id}" code="attachment.cancel"/>
</form:form>
