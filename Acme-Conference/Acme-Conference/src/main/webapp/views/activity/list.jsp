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


<spring:message code="presentation.list" />
<br>
<display:table pagesize="5" name="presentations" id="presentation"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="presentation.title" property="title" />
	<display:column titleKey="presentation.startMoment"
		property="startMoment" />
	<display:column titleKey="presentation.summary" property="summary" />
	<display:column titleKey="presentation.cameraReadyVersion"
		property="cameraReadyVersion" />
	<display:column titleKey="activity.show">
		<acme:cancel
			url="/conference/activity/presentation/show.do?presentationId=${presentation.id}"
			code="activity.show" />
	</display:column>
	<display:column titleKey="conference.comments">
		<acme:cancel
			url="/conference/activity/listCommentPresentation.do?presentationId=${presentation.id}"
			code="conference.comments" />
	</display:column>
</display:table>
<br>
<br>

<spring:message code="panel.list" />
<br>
<display:table pagesize="5" name="panels" id="panel"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="panel.title" property="title" />
	<display:column titleKey="panel.startMoment" property="startMoment" />
	<display:column titleKey="panel.summary" property="summary" />
	<display:column titleKey="activity.show">
		<acme:cancel
			url="/conference/activity/panel/show.do?panelId=${panel.id}"
			code="activity.show" />
	</display:column>
	<display:column titleKey="conference.comments">
		<acme:cancel
			url="/conference/activity/listCommentPanel.do?panelId=${panel.id}" 
			code="conference.comments" />
	</display:column>

</display:table>
<br>
<br>

<spring:message code="tutorial.list" />
<br>
<display:table pagesize="5" name="tutorials" id="tutorial"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="tutorial.title" property="title" />
	<display:column titleKey="tutorial.startMoment" property="startMoment" />
	<display:column titleKey="tutorial.summary" property="summary" />
	<display:column titleKey="activity.show">
		<acme:cancel
			url="/conference/activity/tutorial/show.do?tutorialId=${tutorial.id}"
			code="activity.show" />
	</display:column>
	<display:column titleKey="conference.comments">
		<acme:cancel
			url="/conference/activity/listCommentTutorial.do?tutorialId=${tutorial.id}"
			code="conference.comments" />
	</display:column>

</display:table>
<br>




