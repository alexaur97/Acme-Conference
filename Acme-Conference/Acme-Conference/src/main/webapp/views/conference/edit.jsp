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

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<fieldset class="col-md-6 col-md-offset-3">

				<form:form action="conference/administrator/edit.do"
					modelAttribute="conference" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>	
						<acme:textbox code="conference.title" path="title" />
						<acme:textbox code="conference.acronym" path="acronym" />
						<acme:textbox code="conference.venue" path="venue" />
						<br>
						<spring:message code="conference.dates" />
						<acme:textbox placeholder="dd-MM-yyyy hh:mm" code="conference.submission" path="submissionDeadline" />
						<acme:textbox placeholder="dd-mm-yyyy hh:mm" code="conference.notification" path="notification" />
						<acme:textbox placeholder="dd-mm-yyyy hh:mm" code="conference.cameraReady" path="cameraReady" />
						<acme:textbox placeholder="dd-mm-yyyy hh:mm" code="conference.startDate" path="startDate" />
						<acme:textbox placeholder="dd-mm-yyyy hh:mm" code="conference.endDate" path="endDate" />
						<acme:textarea code="conference.summary" path="summary" />
						<acme:textbox code="conference.fee" path="fee" />
						
						<spring:message code="conference.mode"/>
						<form:select path="mode">
							<form:option value="DRAFT"/>
							<form:option value="FINAL" />
						</form:select>
						
						<acme:select items="${categories}" itemLabel="name" code="conference.category" path="category"/>
					
						<acme:submit name="save" code="conference.save" />						
						<acme:cancel url="/conference/administrator/list.do"
							code="conference.cancel" />
							
						
				
					</div>
				</form:form>
			</fieldset>
		

		</div>
	</div>
</div>
