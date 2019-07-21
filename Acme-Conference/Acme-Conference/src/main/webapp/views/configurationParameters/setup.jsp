<%--
 * action-2.jsp
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

<form:form modelAttribute="configurationParameters"
	action="configuration/administrator/setup.do">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="systemName">
		<spring:message code="configurationParameters.systemName" />
	</form:label>
	<form:input path="systemName" />
	<form:errors cssClass="error" path="systemName" />
	<br />

	<form:label path="banner">
		<spring:message code="configurationParameters.banner" />
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	<form:label path="message">
		<spring:message code="configurationParameters.message" />
	</form:label>
	<form:input path="message" />
	<form:errors cssClass="error" path="message" />
	<br />
	<form:label path="messageEs">
		<spring:message code="configurationParameters.messageEs" />
	</form:label>
	<form:input path="messageEs" />
	<form:errors cssClass="error" path="messageEs" />
	<br />
	<form:label path="countryCode">
		<spring:message code="configurationParameters.countryCode" />
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br />
	<acme:textarea code="configurationParameters.creditCardMakes" path="creditCardMakes"/>
	<acme:textarea code="configurationParameters.voidWords" path="voidWords"/>
	<acme:textarea code="configurationParameters.voidWordsEs" path="voidWordsEs"/>

	<jstl:if test="${b eq true}">
		<p style="color: green">
			<spring:message code="configurationParameters.success" />
		</p>
	</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="configurationParameters.save"/>" />
	<input type="button" name="cancel"
		value="<spring:message code="configurationParameters.cancel"/>"
		onclick="javascript: relativeRedir('#')" />

</form:form>