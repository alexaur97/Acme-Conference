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
<spring:message code="conference.conferencesDraft"/>
<br>
<display:table pagesize="5" name="conferencesDraft" id="conferenceDraft"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.category"  property="category.name" />
	<display:column titleKey="conference.submission"  property="submissionDeadline" />
	<display:column titleKey="conference.notification"  property="notification" />
	<display:column titleKey="conference.cameraReady"  property="cameraReady" />
	<display:column titleKey="conference.startDate"  property="startDate" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conferenceDraft.id}" code="conference.show" />
	</display:column>
	<display:column titleKey="conference.edit">
		<acme:cancel url="/conference/administrator/edit.do?conferenceId=${conferenceDraft.id}" code="conference.edit" />
	</display:column>
</display:table>

<br>
<br>

<spring:message code="conference.titleSubmission"/> 
<br>
<display:table pagesize="5" name="conferencesSubmission" id="conferenceSubmission"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.submission"  property="submissionDeadline" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conferenceSubmission.id}" code="conference.show" />
	</display:column>
	
	<display:column titleKey="conference.edit">
		<jstl:if test="${conferenceSubmission.mode=='DRAFT'}">
		<acme:cancel url="/conference/administrator/edit.do?conferenceId=${conferenceSubmission.id}" code="conference.edit" />
	</jstl:if>
	</display:column>
</display:table>

<br>
<br>

<spring:message code="conference.titleNotification"/>
<br>
<display:table pagesize="5" name="conferencesNotification" id="conferenceNotification"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.notification"  property="notification" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conferenceNotification.id}" code="conference.show" />
	</display:column>
	
	<display:column titleKey="conference.edit">
		<jstl:if test="${conferenceNotification.mode =='DRAFT'}">
		<acme:cancel url="/conference/administrator/edit.do?conferenceId=${conferenceNotification.id}" code="conference.edit" />
	</jstl:if></display:column>
	</display:table>


<br>
<br>
<spring:message code="conference.titleCameraReady"/>
<br>
<display:table pagesize="5" name="conferencesCameraReady" id="conferenceCameraReady"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.cameraReady"  property="cameraReady" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conferenceCameraReady.id}" code="conference.show" />
	</display:column>

	<display:column titleKey="conference.edit">
			<jstl:if test="${conferenceCameraReady.mode == 'DRAFT' }">
		<acme:cancel url="/conference/administrator/edit.do?conferenceId=${conferenceCameraReady.id}" code="conference.edit" />
	</jstl:if></display:column>

</display:table>

<br>
<br>
<spring:message code="conference.titleStartDate"/>
<br>
<display:table pagesize="5" name="conferencesStartDate" id="conferenceStartDate"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.startDate"  property="startDate" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conferenceStartDate.id}" code="conference.show" />
	</display:column>
	<display:column titleKey="conference.edit">
		<jstl:if test="${conferenceStartDate.mode == 'DRAFT' }">
		<acme:cancel url="/conference/administrator/edit.do?conferenceId=${conferenceStartDate.id}" code="conference.edit" />
	</jstl:if></display:column>
</display:table>

<br>
<br>



<security:authorize access="hasRole('ADMINISTRATOR')">
	<acme:cancel url="/conference/administrator/create.do"
		code="conference.create" />
</security:authorize>