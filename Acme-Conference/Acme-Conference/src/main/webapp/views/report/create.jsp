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

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<fieldset class="col-md-6 col-md-offset-3">
			
			<form:form action="report/reviewer/create.do" 
			modelAttribute="report" class="form-horizontal" method="post">
									
							<acme:display code="report.submission.paper" path="${reviewer.submission.ticker}"/>
	
							<acme:textbox code="report.originality" path="originality" />
							<acme:textbox code="report.quality" path="quality" />
							<acme:textbox code="report.readability" path="readability" />
							<spring:message code="report.decision"/>
							<form:select path="decision">
							<form:option value="ACCEPT"/>
							<form:option value="REJECT" />
							<form:option value="BORDER-LINE" />
							</form:select>
							<acme:textarea code="report.comments" path="comments" />
							<br />
	

							<acme:submit name="save" code="report.save" />

							<acme:cancel url="/report/reviewer/list.do" code="msg.cancel" />
					
				</form:form>
			</fieldset>


		</div>
	</div>
</div>


