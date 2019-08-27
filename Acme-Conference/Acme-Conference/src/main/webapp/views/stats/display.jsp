<%--
 * footer.jsp
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h3>
<spring:message code="stats.conference.category" /> :
</h3>
<spring:message code="stats.conference.category.average" />
:
<jstl:out value="${conferencesPerCategory[0][0]}" />
<br />
<spring:message code="stats.conference.category.min" />
:
<jstl:out value="${conferencesPerCategory[0][1]}" />
<br />
<spring:message code="stats.conference.category.max" />
:
<jstl:out value="${conferencesPerCategory[0][2]}" />
<br />
<spring:message code="stats.conference.category.stddev" />
:
<jstl:out value="${conferencesPerCategory[0][3]}" />
<br />

<h3>
<spring:message code="stats.conferenceComments.conference" /> :
</h3>
<spring:message code="stats.conferenceComments.conference.average" />
:
<jstl:out value="${conferenceCommentsPerConference[0][0]}" />
<br />
<spring:message code="stats.conferenceComments.conference.min" />
:
<jstl:out value="${conferenceCommentsPerConference[0][1]}" />
<br />
<spring:message code="stats.conferenceComments.conference.max" />
:
<jstl:out value="${conferenceCommentsPerConference[0][2]}" />
<br />
<spring:message code="stats.conferenceComments.conference.stddev" />
:
<jstl:out value="${conferenceCommentsPerConference[0][3]}" />
<br />

<h3>
<spring:message code="stats.presentationComments.presentation" /> :
</h3>
<spring:message code="stats.presentationComments.presentation.average" />
:
<jstl:out value="${commentsPerPresentation[0][0]}" />
<br />
<spring:message code="stats.presentationComments.presentation.min" />
:
<jstl:out value="${commentsPerPresentation[0][1]}" />
<br />
<spring:message code="stats.presentationComments.presentation.max" />
:
<jstl:out value="${commentsPerPresentation[0][2]}" />
<br />
<spring:message code="stats.presentationComments.presentation.stddev" />
:
<jstl:out value="${commentsPerPresentation[0][3]}" />
<br />


<h3>
<spring:message code="stats.panelComments.panel" /> :
</h3>
<spring:message code="stats.panelComments.panel.average" />
:
<jstl:out value="${commentsPerPanel[0][0]}" />
<br />
<spring:message code="stats.panelComments.panel.min" />
:
<jstl:out value="${commentsPerPanel[0][1]}" />
<br />
<spring:message code="stats.panelComments.panel.max" />
:
<jstl:out value="${commentsPerPanel[0][2]}" />
<br />
<spring:message code="stats.panelComments.panel.stddev" />
:
<jstl:out value="${commentsPerPanel[0][3]}" />
<br />

<h3>
<spring:message code="stats.tutorialComments.tutorial" /> :
</h3>
<spring:message code="stats.tutorialComments.tutorial.average" />
:
<jstl:out value="${commentsPerTutorial[0][0]}" />
<br />
<spring:message code="stats.tutorialComments.tutorial.min" />
:
<jstl:out value="${commentsPerTutorial[0][1]}" />
<br />
<spring:message code="stats.tutorialComments.tutorial.max" />
:
<jstl:out value="${commentsPerTutorial[0][2]}" />
<br />
<spring:message code="stats.tutorialComments.tutorial.stddev" />
:
<jstl:out value="${commentsPerTutorial[0][3]}" />
<br />

<h3>
<spring:message code="stats.conferenceSubmissions.conference" /> :
</h3>
<spring:message code="stats.conferenceSubmissions.conference.average" />
:
<jstl:out value="${submissionsPerConference[0][0]}" />
<br />
<spring:message code="stats.conferenceSubmissions.conference.min" />
:
<jstl:out value="${submissionsPerConference[0][1]}" />
<br />
<spring:message code="stats.conferenceSubmissions.conference.max" />
:
<jstl:out value="${submissionsPerConference[0][2]}" />
<br />
<spring:message code="stats.conferenceSubmissions.conference.stddev" />
:
<jstl:out value="${submissionsPerConference[0][3]}" />
<br />

<h3>
<spring:message code="stats.conferenceRegistrations.conference" /> :
</h3>
<spring:message code="stats.conferenceRegistrations.conference.average" />
:
<jstl:out value="${registrationsPerConference[0][0]}" />
<br />
<spring:message code="stats.conferenceRegistrations.conference.min" />
:
<jstl:out value="${registrationsPerConference[0][1]}" />
<br />
<spring:message code="stats.conferenceRegistrations.conference.max" />
:
<jstl:out value="${registrationsPerConference[0][2]}" />
<br />
<spring:message code="stats.conferenceRegistrations.conference.stddev" />
:
<jstl:out value="${registrationsPerConference[0][3]}" />
<br />

<h3>
<spring:message code="stats.conferenceFee.conference" /> :
</h3>
<spring:message code="stats.conferenceFee.conference.average" />
:
<jstl:out value="${conferencesFee[0][0]}" />
<br />
<spring:message code="stats.conferenceFee.conference.min" />
:
<jstl:out value="${conferencesFee[0][1]}" />
<br />
<spring:message code="stats.conferenceFee.conference.max" />
:
<jstl:out value="${conferencesFee[0][2]}" />
<br />
<spring:message code="stats.conferenceFee.conference.stddev" />
:
<jstl:out value="${conferencesFee[0][3]}" />
<br />

<h3>
<spring:message code="stats.conference.days" /> :
</h3>
<spring:message code="stats.conference.days.average" />
:
<jstl:out value="${conferencesPerDays[0][0]}" />
<br />
<spring:message code="stats.conference.days.min" />
:
<jstl:out value="${conferencesPerDays[0][1]}" />
<br />
<spring:message code="stats.conference.days.max" />
:
<jstl:out value="${conferencesPerDays[0][2]}" />
<br />
<spring:message code="stats.conference.days.stddev" />
:
<jstl:out value="${conferencesPerDays[0][3]}" />
<br />