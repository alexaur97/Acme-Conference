<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:display code="tutorial.title" path="${tutorial.title}" />
<acme:display code="tutorial.room" path="${tutorial.room}" />
<acme:display code="tutorial.summary" path="${tutorial.summary}" />

<h2>
	<spring:message code="tutorial.speakers" />
	:
</h2>

<ul>
	<jstl:forEach items="${tutorial.speakers}" var="x">
		<li><jstl:out value="${x}" /></li>
	</jstl:forEach>
</ul>

<h2>
	<spring:message code="tutorial.schedule" />
</h2>
<acme:display code="tutorial.startMoment" path="${tutorial.startMoment}" />
<acme:display code="tutorial.duration" path="${endMoment}" />


<h2>
	<spring:message code="tutorial.attachments" />
	:
</h2>

<ul>
	<jstl:forEach items="${tutorial.attachments}" var="x">
		<li><a href="${x}"><jstl:out value="${x}" /></a></li>
	</jstl:forEach>
</ul>


<br>

<spring:message code="tutorial.sections" />

<br>

<display:table pagesize="5" name="sections" id="section"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="tutorial.section.title" property="title" />
	<display:column titleKey="tutorial.section.summary" property="summary" />
	<display:column titleKey="tutorial.section.pictures"
		property="pictures" />
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column titleKey="tutorial.section.edit">
			<acme:cancel
				url="/section/administrator/edit.do?sectionId=${section.id}"
				code="tutorial.section.edit" />

		</display:column>
	</security:authorize>



</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:cancel
		url="/section/administrator/create.do?tutorialId=${tutorial.id}"
		code="tutorial.create.section" />
</security:authorize>

<acme:cancel url="conference/list.do" code="msg.cancel" />


