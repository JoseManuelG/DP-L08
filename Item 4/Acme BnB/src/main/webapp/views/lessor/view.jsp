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

<spring:message  code="lessor.name" />: <jstl:out value="${lessor.name}" />
<br>
<spring:message  code="lessor.surname" />: <jstl:out value="${lessor.surname}"/>
<br>
<spring:message  code="lessor.email" />: <jstl:out value="${lessor.email}"/>
<br>
<spring:message  code="lessor.phone" />: <jstl:out value="${lessor.phone}"/>
<br>


	