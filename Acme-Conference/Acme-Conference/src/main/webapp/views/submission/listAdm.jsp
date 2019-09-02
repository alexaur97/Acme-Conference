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


<spring:message code="submission.submissionsUnderReview"/> 
<br>
<display:table pagesize="5" name="submissionsUnderReview" id="submissionUnderReview"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="submission.author"  property="author.name" />
	<display:column titleKey="submission.conference"  property="conference.title" />
	<display:column titleKey="submission.moment"  property="moment" />
	
</display:table>
<br>
<jstl:if test="${message != null}">
	<spring:message code="${message}"/>
</jstl:if>
<br>
<br><spring:message code="submission.unassignedSubmissions"/> 
<br>
<display:table pagesize="5" name="unassignedSubmissions" id="unassignedSubmission"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="submission.author"  property="author.name" />
	<display:column titleKey="submission.conference"  property="conference.title" />
	<display:column titleKey="submission.moment"  property="moment" />
	<display:column titleKey="submission.assign">
		<acme:cancel url="/submission/administrator/assign.do?submissionId=${unassignedSubmission.id}" code="submission.assign" />
	</display:column>
</display:table>

<br>
<br>

<spring:message code="submission.submissionsAccepted"/>
<br>
<display:table pagesize="5" name="submissionsAccepted" id="submissionAccepted"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="submission.author"  property="author.name" />
	<display:column titleKey="submission.conference"  property="conference.title" />
	<display:column titleKey="submission.moment"  property="moment" />

	
	</display:table>


<br>
<br>
<spring:message code="submission.submissionsRejected"/>
<br>
<display:table pagesize="5" name="submissionsRejected" id="submissionRejected"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="submission.author"  property="author.name" />
	<display:column titleKey="submission.conference"  property="conference.title" />
	<display:column titleKey="submission.moment"  property="moment" />


</display:table>

