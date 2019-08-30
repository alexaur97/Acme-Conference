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


<spring:message code="conference.futureConference" />
<br />
<display:table pagesize="5" name="nextconferences" id="conference"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title" property="title" />
	<display:column titleKey="conference.submission"
		property="submissionDeadline" />
	<display:column titleKey="conference.startDate" property="startDate" />
	<display:column titleKey="conference.endDate" property="endDate" />
		<display:column titleKey="conference.category" property="category.name" sortable="true"/>
	
	<display:column titleKey="conference.show">
			<acme:cancel
				url="/conference/show.do?conferenceId=${conference.id}"
				code="conference.show" />
		</display:column>
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column titleKey="conference.panel">
			<acme:cancel
				url="/panel/administrator/list.do?conferenceId=${conference.id}"
				code="conference.panel" />
		</display:column>
		<display:column titleKey="conference.presentation">
			<acme:cancel
				url="/presentation/administrator/list.do?conferenceId=${conference.id}"
				code="conference.presentation" />
		</display:column>
		<display:column titleKey="conference.tutorial">
			<acme:cancel
				url="/tutorial/administrator/list.do?conferenceId=${conference.id}"
				code="conference.tutorial" />
		</display:column>
	</security:authorize>
	
	<display:column titleKey="conference.activities">
		<acme:cancel
			url="/conference/activity/listByConference.do?conferenceId=${conference.id}"
			code="conference.activities" />
	</display:column>
</display:table>
<br />
<security:authorize access="hasRole('AUTHOR')">
	<acme:cancel url="/registration/author/create.do"
		code="conference.registration" />
	<br/>
	<br/>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:cancel url="/panel/administrator/create.do"
		code="conference.create.panel" />
	<acme:cancel url="/presentation/administrator/create.do"
		code="conference.create.presentation" />
	<acme:cancel url="/tutorial/administrator/create.do"
		code="conference.create.tutorial" />
	<br />
	<br />
</security:authorize>

<spring:message code="conference.runningConference" />
<br />
<display:table pagesize="5" name="runningConferences"
	id="runningConference" requestURI="${requestURI}"
	class="displaytag table">
	<display:column titleKey="conference.title" property="title" />
	<display:column titleKey="conference.submission"
		property="submissionDeadline" />
	<display:column titleKey="conference.startDate" property="startDate" />
	<display:column titleKey="conference.endDate" property="endDate" />
	<display:column titleKey="conference.show">
			<acme:cancel
				url="/conference/show.do?conferenceId=${runningConference.id}"
				code="conference.show" />
		</display:column>
	<display:column titleKey="conference.comments">
		<acme:cancel
			url="/conference/comment/listByConference.do?conferenceId=${runningConference.id}"
			code="conference.comments" />
	</display:column>
	<display:column titleKey="conference.activities">
		<acme:cancel
			url="/conference/activity/listByConference.do?conferenceId=${runningConference.id}"
			code="conference.activities" />
	</display:column>

</display:table>
<br />
<spring:message code="conference.pastConference" />
<br />
<display:table pagesize="5" name="pastConferences" id="pastConference"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title" property="title" />
	<display:column titleKey="conference.submission"
		property="submissionDeadline" />
	<display:column titleKey="conference.startDate" property="startDate" />
	<display:column titleKey="conference.endDate" property="endDate" />
	<display:column titleKey="conference.show">
			<acme:cancel
				url="/conference/show.do?conferenceId=${pastConference.id}"
				code="conference.show" />
		</display:column>
	<display:column titleKey="conference.comments">
		<acme:cancel
			url="/conference/comment/listByConference.do?conferenceId=${pastConference.id}"
			code="conference.comments" />
	</display:column>
	<display:column titleKey="conference.activities">
		<acme:cancel
			url="/conference/activity/listByConference.do?conferenceId=${pastConference.id}"
			code="conference.activities" />
	</display:column>
</display:table>
<br>
<br>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:cancel url="/conference/administrator/create.do"
		code="conference.create" />
</security:authorize>