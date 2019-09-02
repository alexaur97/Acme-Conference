<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:display code="presentation.title" path="${presentation.title}" />
<spring:message code="presentation.speakers" />
<ul>
	<jstl:forEach items="${presentation.speakers}" var="x">
		<li><jstl:out value="${x}" /></li>
	</jstl:forEach>
</ul>

<acme:display code="presentation.room" path="${presentation.room}" />
<acme:display code="presentation.summary" path="${presentation.summary}" />
<spring:message code="presentation.attachments" />
<ul>
	<jstl:forEach items="${presentation.attachments}" var="x">
		<li><a href="${x}"><jstl:out value="${x}" /></a></li>
	</jstl:forEach>
</ul>
<acme:display code="presentation.cameraReadyVersion" path="${presentation.cameraReadyVersion}" />

<h2><spring:message code="presentation.schedule"/></h2>	
						
<acme:display code="presentation.startMoment" path="${presentation.startMoment}" />
<acme:display code="presentation.duration" path="${endMoment}" />

<br>
<acme:cancel url="/conference/list.do"
							code="msg.cancel" />


