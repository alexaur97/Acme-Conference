<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img width="450" src="${banner}" alt="Acme Rookies Co., Inc."
		style="margin-bottom: 0.5em;" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->


		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/setup.do"><spring:message code="master.page.administrator.config" /></a></li>
					<li><a href="stats/administrator/display.do"><spring:message
								code="master.page.administrator.stats" /></a></li>
					
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="conference/list.do"><spring:message code="master.page.conferences" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="conference/list.do"><spring:message code="master.page.conferences" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv" href="sponsorship/sponsor/list.do"><spring:message
						code="master.page.sponsorship" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv" href="conference/administrator/list.do"><spring:message
					code="master.page.listConference" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv" href="submission/administrator/list.do"><spring:message
					code="master.page.submissions" /></a></li>
			<li><a href="stats/administrator/score.do"><spring:message
								code="master.page.administrator.score" /></a></li>
		</security:authorize>

		<li><a class="fNiv" href="conference/search.do"><spring:message
					code="master.page.searchConference" /></a></li>
		
		<security:authorize access="hasRole('AUTHOR')">
			<li><a href="finder/author/view.do"><spring:message
						code="master.page.finder" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">

			<li><a class="fNiv" href="topic/administrator/list.do"><spring:message
					code="master.page.listTopic" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">

			<li><a class="fNiv" href="category/administrator/list.do"><spring:message
					code="master.page.listCategory" /></a></li>
		</security:authorize>

	<security:authorize access="hasRole('REVIEWER')">

			<li><a class="fNiv" href="report/reviewer/list.do"><spring:message
					code="master.page.listReports" /></a></li>
		</security:authorize>
		

			<security:authorize access="hasRole('AUTHOR')">

			<li><a class="fNiv" href="registration/author/list.do"><spring:message
					code="master.page.listRegistration" /></a></li>
			<li><a class="fNiv" href="submission/author/list.do"><spring:message
					code="master.page.submissions" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
			<ul>
					<li class="arrow"></li>	
					<li><a href="actor/registerSponsor.do"><spring:message code="master.page.register.sponsor" /></a></li>
					<li><a href="actor/registerAuthor.do"><spring:message code="master.page.register.author" /></a></li>
					<li><a href="actor/registerReviewer.do"><spring:message code="master.page.register.reviewer" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li>
				<a href="actor/show.do" class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>


						<li><a href="actor/edit.do"><spring:message
									code="master.page.editProfile" /></a></li>

					<li><a href="message/list.do"><spring:message
								code="master.page.message" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
