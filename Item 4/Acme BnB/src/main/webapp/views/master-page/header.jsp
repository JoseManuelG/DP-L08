<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="<spring:url value='/' />">
		<img src="images/logo.png" alt="Acme BnB Co., Inc." />
	</a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="attribute/administrator/list.do"><spring:message code="master.page.administrator.attributes" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		<li><a class="fNiv"><spring:message code="master.page.lessor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="lessor/list.do"><spring:message code="master.page.lessor.list" />
						</a>
					</li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.property" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="property/list.do"><spring:message code="master.page.property.list" />
						</a>
					</li>
				</ul>
			</li>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('LESSOR')">
						<li><a href="book/lessor/list.do"><spring:message code="master.page.lessor.request.books" /></a></li>
						<li><a href="property/lessor/myProperties.do"><spring:message code="master.page.lessor.myProperties" /></a></li>
						<li><a href="lessor/lessor/fee.do"><spring:message code="master.page.lessor.unpaid.fee" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('TENANT')">
						<li><a href="book/tenant/list.do"><spring:message code="master.page.tenant.request.books" /></a></li>
					</security:authorize>	
					<security:authorize access="hasRole('TENANT')">
						<li><a href="finder/tenant/finder.do"><spring:message code="master.page.tenant.finder" /></a></li>
					</security:authorize>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

