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
<spring:message code="stats.activityComments.activity" /> :
</h3>
<spring:message code="stats.activityComments.activity.average" />
:
<jstl:out value="${activityCommentsPerActivity[0][0]}" />
<br />
<spring:message code="stats.activityComments.activity.min" />
:
<jstl:out value="${activityCommentsPerActivity[0][1]}" />
<br />
<spring:message code="stats.activityComments.activity.max" />
:
<jstl:out value="${activityCommentsPerActivity[0][2]}" />
<br />
<spring:message code="stats.activityComments.activity.stddev" />
:
<jstl:out value="${activityCommentsPerActivity[0][3]}" />
<br />