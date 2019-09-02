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
<form:form action="conference/search.do" modelAttribute="conference" class="form-horizontal" method="post">
					<br>
					<acme:textbox code="conference.keyword" path="title" />
						<acme:submit name="save" code="conference.search" />
					</form:form>

<display:table name="conferences" id="conference"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title"  property="title" />
	<display:column titleKey="conference.acronym" property="acronym" />
	<display:column titleKey="conference.venue" property="venue" />
	<display:column titleKey="conference.submission" property="submissionDeadline" />
	<display:column titleKey="conference.cameraReady" property="cameraReady" />
	<display:column titleKey="conference.startDate" property="startDate" />
	<display:column titleKey="conference.endDate" property="endDate" />
	<display:column titleKey="conference.summary" property="summary" />
	<display:column titleKey="conference.fee" property="fee" />	
</display:table>

