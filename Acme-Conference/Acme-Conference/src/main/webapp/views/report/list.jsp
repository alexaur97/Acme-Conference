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



<display:table pagesize="5" name="reports" id="report"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="report.originality"  property="originality" />
	<display:column titleKey="report.quality"  property="quality" />
	<display:column titleKey="report.readability"  property="readability" />
	<display:column titleKey="report.decision"  property="decision" />
	
	<display:column titleKey="report.show">
		<acme:cancel url="/report/reviewer/show.do?reportId=${report.id}" code="report.show" />
	</display:column>
	
</display:table>

<jstl:if test="${hasSubmission eq true}">
		<acme:cancel url="/report/reviewer/create.do" code="report.create" />
</jstl:if> 

<jstl:if test="${hasSubmission eq false}">
		<spring:message code="report.noSubmission"/>
</jstl:if> 


