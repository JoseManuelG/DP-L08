 <%--
 * login.jsp
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

<p><spring:message code="security.register" /></p>
<form:form action="${requestURI}" modelAttribute="actorForm">
	
	<form:label path="typeOfActor">
		<spring:message code="security.register.typeOfActor" />
	</form:label>
	
	<select id="typeOfActor" name="typeOfActor">
    	<option value="LESSOR">Lessor</option>
    	<option value="TENANT">Tenant</option>
    </select>
    
    <acme:textbox code="security.register.name" path="name"/>
	<br />
	
	<acme:textbox code="security.password" path="password"/>
	<br />
	
	<acme:textbox code="security.register.surname" path="surname"/>
	<br />
	
	<acme:textbox code="security.register.email" path="email"/>
	<br />
	
	<acme:textbox code="security.register.phone" path="phone"/>
	<br />
	
	<a target="_blank" href="law/terms-conditions.do">
		<spring:message code="security.register.terms" />
	</a>
	<br/>
	<form:input type="checkbox" name="Terms" value="true" path="Terms"/><spring:message code="security.register.AceptTerms" />
	
	<br />
	
	<acme:submit code="security.register.save" name="save"/>
	
	<acme:cancel url="javascript:window.location.href=''" code="security.register.cancel"/>
	
</form:form>