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


<spring:message  code="lessor.name" />: <jstl:out value="${auditor.name}" />
<br>
<spring:message  code="lessor.surname" />: <jstl:out value="${auditor.surname}"/>
<br>
<spring:message  code="lessor.email" />: <jstl:out value="${auditor.email}"/>
<br>
<spring:message  code="lessor.phone" />: <jstl:out value="${auditor.phone}"/>
<br>
<img src=<jstl:out value="${auditor.picture}"/> />
<br>
<spring:message  code="auditor.companyName" />: <jstl:out value="${auditor.companyName}"/>
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="socialIdentities" requestURI="${requestURI}" id="row" uid="socialIdentity">
	
	<jstl:if test="${esMiPerfil}">
			<spring:message code="property.edit.property" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="socialIdentity/edit.do?socialIdentityId=${social.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
	</jstl:if>
	
	<acme:column sorteable="true" code="auditor.socialIdentity.nick" path="nick"/>
	
	<acme:column sorteable="false" code="auditor.socialIdentity.socialNetwork" path="socialNetwork"/>
	
	<display:column>
			<a href=<jstl:out value="${social.link}" />>
				<spring:message code="auditor.socialIdentity.link" />
			</a>
	</display:column>
	
</display:table>
<br/>
<jstl:if test="${esMiPerfil}">
	<a href=socialIdentity/create.do>
	     <spring:message  code="socialIdentity.create" />
	</a>
</jstl:if>
<br/>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="audits" requestURI="${requestURI}" id="row" uid="audit">
	
	<acme:column sorteable="true" code="auditor.audit.writingMoment" path="writingMoment"/>
	
	<acme:column sorteable="false" code="auditor.socialIdentity.draftMode" path="draftMode"/>
		
</display:table>
<br/>
<a href="security/edit.do"><spring:message  code="auditor.edit" /></a>

	