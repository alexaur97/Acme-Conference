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


<acme:display code="conference.title" path="${conference.title}" />
<acme:display code="conference.acronym" path="${conference.acronym}" />
<acme:display code="conference.venue" path="${conference.venue}" />
<acme:display code="conference.submission" path="${conference.submissionDeadline}" />
<acme:display code="conference.notification" path="${conference.notification}" />
<acme:display code="conference.cameraReady" path="${conference.cameraReady}" />
<acme:display code="conference.submission"
	path="${conference.submission}" />
<acme:display code="conference.notification"
	path="${conference.notification}" />
<acme:display code="conference.cameraReady"
	path="${conference.cameraReady}" />
<acme:display code="conference.startDate" path="${conference.startDate}" />
<acme:display code="conference.endDate" path="${conference.endDate}" />
<acme:display code="conference.summary" path="${conference.summary}" />
<acme:display code="conference.fee" path="${conference.fee}" />
<acme:display code="conference.category"
	path="${conference.category.title}" />

<security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:button url="/conference/administrator/list.do"
		code="conference.back" />
	<jstl:choose>
		<jstl:when test="${bool eq true}">
		</jstl:when>
		<jstl:otherwise>
		<p><spring:message code="conference.decision" /></p>
			<acme:button
				url="/conference/administrator/decision.do?conferenceId=${conference.id}"
				code="conference.run" />
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>
