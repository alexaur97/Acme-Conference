<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('AUTHOR')">
	<jstl:if test="${not empty score}">
		<acme:display code="registration.score" path="${score}" />
	</jstl:if>
</security:authorize>
<br>
<jstl:if test="${not empty actor.photo}" >
<img width="250px" src="${actor.photo}" alt="${actor.photo}" />
</jstl:if>

<br />
<acme:display code="actor.name" path="${actor.name}" />
<acme:display code="actor.middleName" path="${actor.middleName}" />
<acme:display code="actor.surname" path="${actor.surname}" />

<acme:display code="actor.email" path="${actor.email}" />
<acme:display code="actor.phone" path="${actor.phone}" />
<acme:display code="actor.address" path="${actor.address}" />
<security:authorize access="hasRole('REVIEWER')">
	<spring:message code="actor.keyWords" />
	<ul>
		<jstl:forEach items="${actor.keyWords}" var="x">
			<li><jstl:out value="${x}" /></li>
		</jstl:forEach>
	</ul>

</security:authorize>
<br />

<acme:button code="actor.edit" url="actor/edit.do" />
<acme:button code="actor.cancel" url="#" />


