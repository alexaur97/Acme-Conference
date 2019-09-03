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


<acme:display code="conference.title" path="${conference.title}" />
<acme:display code="conference.acronym" path="${conference.acronym}" />
<acme:display code="conference.venue" path="${conference.venue}" />
<acme:display code="conference.submission"
	path="${conference.submissionDeadline}" />
<acme:display code="conference.notification"
	path="${conference.notification}" />
<acme:display code="conference.cameraReady"
	path="${conference.cameraReady}" />
<acme:display code="conference.startDate" path="${conference.startDate}" />
<acme:display code="conference.endDate" path="${conference.endDate}" />
<acme:display code="conference.summary" path="${conference.summary}" />
<acme:display code="conference.fee" path="${conference.fee}" />
<acme:display code="conference.mode" path="${conference.mode}" />

<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<acme:display code="conference.category"
			path="${conference.category.name}" />
	</jstl:when>
	<jstl:otherwise>
		<acme:display code="conference.category"
			path="${conference.category.nameEs}" />
	</jstl:otherwise>
</jstl:choose>

<jstl:choose>
	<jstl:when test="${empty sponsorship}">
		<h4>
			<spring:message code="conference.noSponsorships" />
		</h4>
	</jstl:when>
	<jstl:otherwise>
		<h4>
			<spring:message code="conference.sponsorship" />
		</h4>
		<a  href="${sponsorship.targetUrl}"><img width="200"
			src="${sponsorship.banner}"></a>
		<p>
			<spring:message code="conference.sponsor" />
			:
			<jstl:out value="${sponsorship.sponsor.name}" />
			<jstl:out value="${sponsorship.sponsor.surname}" />
		</p>

	</jstl:otherwise>
</jstl:choose>

<jstl:if test="${submissions eq true}">
	<jstl:choose>
		<jstl:when test="${bool eq true}">

			<h4>
				<spring:message code="conference.acceptedSubmissions" />
			</h4>

			<display:table pagesize="5" name="acceptedSubmissions"
				id="acceptedSubmission" requestURI="${requestURI}"
				class="displaytag table">
				<display:column titleKey="conference.author"
					property="author.userAccount.username" />
				<display:column titleKey="conference.paper" property="paper.title" />
				<display:column titleKey="conference.ticker" property="ticker" />
				<display:column titleKey="conference.moment" property="moment" />
			</display:table>

			<h4>
				<spring:message code="conference.rejectedSubmissions" />
			</h4>

			<display:table pagesize="5" name="rejectedSubmissions"
				id="rejectedSubmission" requestURI="${requestURI}"
				class="displaytag table">
				<display:column titleKey="conference.author"
					property="author.userAccount.username" />
				<display:column titleKey="conference.paper" property="paper.title" />
				<display:column titleKey="conference.ticker" property="ticker" />
				<display:column titleKey="conference.moment" property="moment" />
			</display:table>
		</jstl:when>
		<jstl:otherwise>
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<br />
				<fieldset>
					<legend>
						<spring:message code="conference.decision" />
					</legend>
					<acme:button
						url="/conference/administrator/decision.do?conferenceId=${conference.id}"
						code="conference.run" />
				</fieldset>
				<br />
			</security:authorize>
		</jstl:otherwise>
	</jstl:choose>
</jstl:if>

<h4>
	<spring:message code="conference.comments" />
</h4>

<display:table name="comments" id="comment" requestURI="${requestURI}"
	class="displaytag table" pagesize="5">
	<display:column titleKey="conferenceComment.author" property="author" />
	<display:column titleKey="conferenceComment.moment" property="moment" />
	<display:column titleKey="conferenceComment.title" property="title" />
	<display:column titleKey="conferenceComment.text" property="text" />
	<display:column titleKey="conferenceComment.show">
		<acme:button url="conference/comment/show.do?commentId=${comment.id}"
			code="conferenceComment.show" />
	</display:column>
</display:table>
<acme:button
	url="conference/comment/create.do?conferenceId=${conference.id}"
	code="comment.write" />