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

<security:authorize access="hasRole('ADMINISTRATOR')">
<acme:cancel url="/message/administrator/broadcastSubmitters.do" code="message.broadcast.submitter" />
<br>
<br>
<acme:cancel url="/message/administrator/broadcastAttendee.do" code="message.broadcast.attendee" />
<br>
<br>
<acme:cancel url="/message/administrator/broadcastAuthor.do" code="message.broadcast.authors" />
<br>
<br>
<acme:cancel url="/message/administrator/broadcastAll.do" code="message.broadcast.all" />
<br>
<br>
</security:authorize>
<td><acme:cancel url="/message/create.do" code="message.create" /></td>

<h5>
	<spring:message code="message.listThem" />
	<td><acme:cancel url="/message/list.do" code="message.normal" /></td>
	<td><acme:cancel url="/message/listByTopic.do"
			code="message.byTopic" /></td>
	<td><acme:cancel url="/message/listBySender.do"
			code="message.sender" /></td>
	<td><acme:cancel url="/message/listByRecipient.do"
			code="message.recipient" /></td>
</h5>

<h4>
	<spring:message code="message.received" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.topic" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>

	<jstl:forEach items="${messagesRecived}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>

			<td><jstl:if test="${lang eq 'en'}">
					<jstl:out value="${x.topic.name}" />
				</jstl:if> <jstl:if test="${lang eq 'es'}">
					<jstl:out value="${x.topic.nameEs}" />
				</jstl:if></td>

			<td><jstl:out value="${x.moment}" /></td>
			<jstl:choose>
				<jstl:when test="${not empty x.sender}">
					<td><jstl:out value="${x.sender.name} ${x.sender.surname}" /></td>
				</jstl:when>
				<jstl:otherwise>
					<td><jstl:out value="SYSTEM" /></td>
				</jstl:otherwise>
			</jstl:choose>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.sended" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.topic" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.recipient" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesSend}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:if test="${lang eq 'en'}">
					<jstl:out value="${x.topic.name}" />
				</jstl:if> <jstl:if test="${lang eq 'es'}">
					<jstl:out value="${x.topic.nameEs}" />
				</jstl:if></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out value="${x.recipient.name} ${x.recipient.surname}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.spam" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.topic" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesSpam}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:if test="${lang eq 'en'}">
					<jstl:out value="${x.topic.name}" />
				</jstl:if> <jstl:if test="${lang eq 'es'}">
					<jstl:out value="${x.topic.nameEs}" />
				</jstl:if></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out value="${x.sender.name} ${x.sender.surname}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.deleted" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.topic" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.recipient" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesDeleted}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:if test="${lang eq 'en'}">
					<jstl:out value="${x.topic.name}" />
				</jstl:if> <jstl:if test="${lang eq 'es'}">
					<jstl:out value="${x.topic.nameEs}" />
				</jstl:if></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out value="${x.sender.name} ${x.sender.surname}" /></td>
			<td><jstl:out value="${x.recipient.name} ${x.recipient.surname}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

