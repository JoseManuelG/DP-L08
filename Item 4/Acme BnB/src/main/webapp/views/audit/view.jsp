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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message  code="audit.text" />: <jstl:out value="${audit.text}" />
<br>
<spring:message  code="audit.writingMoment" />: <jstl:out value="${audit.writingMoment}"/>
<br>
<spring:message  code="audit.draftMode" />: <jstl:out value="${audit.draftMode}"/>
<br>


<br/>
<br/>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="attachments" requestURI="${requestURI}" id="row" uid="attachment">
	
	<acme:column sorteable="true" code="audit.attachment.name" path="name"/>
	
	<acme:column sorteable="false" code="audit.attachment.url" path="url"/>
	
	<jstl:if test="${ esMiAudit}">
		<display:column>
			<a href="attachment/auditor/edit.do?attachmentId=${row.id}">
				<spring:message	code="audit.attachment.edit" />
			</a>
	
		</display:column>
			</jstl:if>	
</display:table>

<jstl:if test="${ esMiAudit}">
				<a href="attachment/auditor/create.do?auditId=${audit.id}">
				<spring:message	code="audit.attachment.create" />
				</a>
</jstl:if>
<br/>


	