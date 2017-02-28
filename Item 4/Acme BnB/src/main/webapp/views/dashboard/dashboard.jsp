
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

<fieldset>
	<spring:message code="AverageAcceptedBooksPerLessor" />: <jstl:out value="${AverageAcceptedBooksPerLessor}"/><br/>
	<spring:message code="AverageDeniedBooksPerLessor" />: <jstl:out value="${AverageDeniedBooksPerLessor}"/><br/>
	<spring:message code="AverageAcceptedBooksPerTenant" />: <jstl:out value="${AverageAcceptedBooksPerTenant}"/><br/>
	<spring:message code="AverageDeniedBooksPerTenant" />: <jstl:out value="${AverageDeniedBooksPerTenant}"/><br/>
	<spring:message code="LessorWithMoreAcceptedBooks" />: <jstl:out value="${LessorWithMoreAcceptedBooks}"/><br/>
	<spring:message code="LessorWithMoreDeniedBooks" />: <jstl:out value="${LessorWithMoreDeniedBooks}"/><br/>
	<spring:message code="LessorWithMorePendingBooks" />: <jstl:out value="${LessorWithMorePendingBooks}"/><br/>
	<spring:message code="TenantWithMoreAcceptedBooks" />: <jstl:out value="${TenantWithMoreAcceptedBooks}"/><br/>
	<spring:message code="TenantWithMoreDeniedBooks" />: <jstl:out value="${TenantWithMoreDeniedBooks}"/><br/>
	<spring:message code="TenantWithMorePendingBooks" />: <jstl:out value="${TenantWithMorePendingBooks}"/><br/>
	<spring:message code="LessorWithMaxAcceptedVersusTotalBooksRatio" />: <jstl:out value="${LessorWithMaxAcceptedVersusTotalBooksRatio}"/><br/>
	<spring:message code="LessorWithMinAcceptedVersusTotalBooksRatio" />: <jstl:out value="${LessorWithMinAcceptedVersusTotalBooksRatio}"/><br/>
	<spring:message code="TenantWithMaxAcceptedVersusTotalBooksRatio" />: <jstl:out value="${TenantWithMaxAcceptedVersusTotalBooksRatio}"/><br/>
	<spring:message code="TenantWithMinAcceptedVersusTotalBooksRatio" />: <jstl:out value="${TenantWithMinAcceptedVersusTotalBooksRatio}"/><br/>
	<spring:message code="AverageResultsPerFinder" />: <jstl:out value="${AverageResultsPerFinder}"/><br/>
	<spring:message code="MinimumResultsPerFinder" />: <jstl:out value="${MinimumResultsPerFinder}"/><br/>
	<spring:message code="MaximumResultsPerFinder" />: <jstl:out value="${MaximumResultsPerFinder}"/><br/>
	<spring:message code="MinimumSocialIdentitiesPerActor" />: <jstl:out value="${MinimumSocialIdentitiesPerActor}"/><br/>
	<spring:message code="AverageSocialIdentitiesPerActor" />: <jstl:out value="${AverageSocialIdentitiesPerActor}"/><br/>
	<spring:message code="MaximumSocialIdentitiesPerActor" />: <jstl:out value="${MaximumSocialIdentitiesPerActor}"/><br/>
	<spring:message code="MinimumInvoicesPerTenant" />: <jstl:out value="${MinimumInvoicesPerTenant}"/><br/>
	<spring:message code="AverageInvoicesPerTenant" />: <jstl:out value="${AverageInvoicesPerTenant}"/><br/>
	<spring:message code="MaximumInvoicesPerTenant" />: <jstl:out value="${MaximumInvoicesPerTenant}"/><br/>
	<spring:message code="TotalDueMoneyOfInvoices" />: <jstl:out value="${TotalDueMoneyOfInvoices}"/><br/>
	<spring:message code="AverageRequestsWithAuditsVersusNoAudits" />: <jstl:out value="${AverageRequestsWithAuditsVersusNoAudits}"/><br/>
</fieldset>
