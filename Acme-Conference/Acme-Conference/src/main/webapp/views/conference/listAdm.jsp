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


<spring:message code="conference.titleSubmission"/> 
<br>
<display:table pagesize="5" name="conferencesSubmission" id="conferenceSubmission"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.submission"  property="submission" />
	<display:column titleKey="conference.show">
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conference.id}" code="conference.show" />
	</display:column>
	<display:column titleKey="conference.panel">
		<acme:cancel url="/panel/administrator/list.do?conferenceId=${conference.id}" code="conference.panel" />
	</display:column>
	<display:column titleKey="conference.presentation">
		<acme:cancel url="/presentation/administrator/list.do?conferenceId=${conference.id}" code="conference.presentation" />
	</display:column>
	<display:column titleKey="conference.tutorial">
		<acme:cancel url="/tutorial/administrator/list.do?conferenceId=${conference.id}" code="conference.tutorial" />
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
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conference.id}" code="conference.show" />
	</display:column>
		<display:column titleKey="conference.panel">
		<acme:cancel url="/panel/administrator/list.do?conferenceId=${conference.id}" code="conference.panel" />
	</display:column>
	<display:column titleKey="conference.presentation">
		<acme:cancel url="/presentation/administrator/list.do?conferenceId=${conference.id}" code="conference.presentation" />
	</display:column>
		<display:column titleKey="conference.tutorial">
		<acme:cancel url="/tutorial/administrator/list.do?conferenceId=${conference.id}" code="conference.tutorial" />
	</display:column>
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
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conference.id}" code="conference.show" />
	</display:column>
		<display:column titleKey="conference.panel">
		<acme:cancel url="/panel/administrator/list.do?conferenceId=${conference.id}" code="conference.panel" />
	</display:column>
	<display:column titleKey="conference.presentation">
		<acme:cancel url="/presentation/administrator/list.do?conferenceId=${conference.id}" code="conference.presentation" />
	</display:column>
		<display:column titleKey="conference.tutorial">
		<acme:cancel url="/tutorial/administrator/list.do?conferenceId=${conference.id}" code="conference.tutorial" />
	</display:column>

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
		<acme:cancel url="/conference/administrator/show.do?conferenceId=${conference.id}" code="conference.show" />
	</display:column>
	<display:column titleKey="conference.panel">
		<acme:cancel url="/panel/administrator/list.do?conferenceId=${conference.id}" code="conference.panel" />
	</display:column>
	<display:column titleKey="conference.presentation">
		<acme:cancel url="/presentation/administrator/list.do?conferenceId=${conference.id}" code="conference.presentation" />
	</display:column>
		<display:column titleKey="conference.tutorial">
		<acme:cancel url="/tutorial/administrator/list.do?conferenceId=${conference.id}" code="conference.tutorial" />
	</display:column>

</display:table>

<br>
<br>
<acme:cancel url="/panel/administrator/create.do" code="conference.create.panel" />
<acme:cancel url="/presentation/administrator/create.do" code="conference.create.presentation" />
<acme:cancel url="/tutorial/administrator/create.do" code="conference.create.tutorial" />
