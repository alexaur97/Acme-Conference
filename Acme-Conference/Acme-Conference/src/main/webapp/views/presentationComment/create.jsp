<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form
	action="presentation/comment/create.do?presentationId=${presentationId}"
	modelAttribute="presentationComment" method="post">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="presentationComment.author" path="author" />
	<acme:textbox code="presentationComment.title" path="title" />
	<acme:textarea code="presentationComment.text" path="text" />

	<acme:submit name="save" code="presentationComment.save" />
	<acme:cancel code="presentationComment.cancel"
		url="/presentation/show.do?presentationId=${presentationId}" />
</form:form>