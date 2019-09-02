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

<acme:display code="report.quality" path="${report.quality}" />
<acme:display code="report.originality" path="${report.originality}" />
<acme:display code="report.readability" path="${report.readability}" />
<acme:display code="report.decision" path="${report.decision}" />

<h4><spring:message code="report.comments"/></h4>
<ul>
<jstl:forEach items="${report.comments}" var="x">
<li><jstl:out value="${x}" /></li>
</jstl:forEach>
</ul>

	<acme:button url="submission/reviewer/show.do?submissionId=${report.submission.id}" code="report.submission"/>

	<acme:cancel url="/report/reviewer/list.do" code="msg.cancel" />





