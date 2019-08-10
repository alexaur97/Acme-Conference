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


<acme:display code="tutorial.title" path="${tutorial.title}" />
<acme:display code="tutorial.speakers" path="${tutorial.speakers}" />
<acme:display code="tutorial.duration" path="${tutorial.duration}" />
<acme:display code="tutorial.startMoment" path="${tutorial.startMoment}" />
<acme:display code="tutorial.room" path="${tutorial.room}" />
<acme:display code="tutorial.summary" path="${tutorial.summary}" />
<acme:display code="tutorial.attachments" path="${tutorial.attachments}" />

<br>

<spring:message code="tutorial.sections"/> 

<br>

<display:table pagesize="5" name="sections" id="section"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="tutorial.section.title"  property="title" />
	<display:column titleKey="tutorial.section.summary"  property="summary" />
	<display:column titleKey="tutorial.section.pictures"  property="pictures" />

	
	
</display:table>

<acme:cancel url="/section/administrator/create.do?tutorialId=${tutorial.id}" code="tutorial.create.section" />


