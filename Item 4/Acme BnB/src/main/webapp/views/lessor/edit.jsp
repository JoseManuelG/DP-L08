
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

<form:form action="lessor/edit.do" modelAttribute="actorForm">
	
	<form:hidden path="typeOfActor" />	
	<br />
	
	<acme:textbox code="lessor.username" path="lessor.userAccount.username"/>
	<acme:textbox code="lessor.password" path="lessor.userAccount.password"/>
	<acme:textbox code="lessor.name" path="lessor.name"/>
	<acme:textbox code="lessor.surname" path="lessor.surname"/>
	<acme:textbox code="lessor.email" path="lessor.email"/>
	<acme:textbox code="lessor.phone" path="lessor.phone"/>
	<acme:textbox code="lessor.picture" path="lessor.picture"/>
	
	
	<acme:submit name="save" code="lessor.save"/>
	
	<jstl:if test="${actorForm.lessor.id ne 0}">
		<acme:submit name="delete" code="lessor.delete"/>
	</jstl:if>
	<acme:cancel url="javascript:window.location.href='lessor/list.do'" code="lessor.cancel"/>
	<br>
</form:form>