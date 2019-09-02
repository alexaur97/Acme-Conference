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


<acme:display code="panel.title" path="${panel.title}" />
<spring:message code="panel.speakers" />
<ul>
	<jstl:forEach items="${panel.speakers}" var="x">
		<li><jstl:out value="${x}" /></li>
	</jstl:forEach>
</ul>

<acme:display code="panel.room" path="${panel.room}" />
<acme:display code="panel.summary" path="${panel.summary}" />
<spring:message code="panel.attachments" />
<ul>
	<jstl:forEach items="${panel.attachments}" var="x">
		<li><a href="${x}"><jstl:out value="${x}" /></a></li>
	</jstl:forEach>
</ul>


<h2><spring:message code="panel.schedule"/></h2>	
						
<acme:display code="panel.startMoment" path="${panel.startMoment}" />
<acme:display code="panel.duration" path="${endMoment}" />

<br>
<acme:cancel url="/conference/list.do"
							code="msg.cancel" />

