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
			
			<form:form action="registration/author/create.do" 
			modelAttribute="registrationForm" class="form-horizontal" method="post">
									
					
							<spring:message code="registration.creditCard" />
							<acme:textbox code="registration.holderName" path="holderName" />
							<spring:message code="sponsorship.brandName" />
							<form:select path="brandName">
    						<form:options items="${brandNames}" />
							</form:select>
							<acme:textbox code="registration.number" path="number" />
							<acme:textbox code="registration.expirationMonth" path="expirationMonth" />
							<acme:textbox code="registration.expirationYear" path="expirationYear" />
							<acme:textbox code="registration.cvv" path="cvv" />
							<br />
	
							<spring:message code="registration.conferencias" />:
							<acme:select items="${conferencias}" itemLabel="title" code="registration.conferencia" path="conference"/>
							<acme:submit name="save" code="registration.save" />

							<acme:cancel url="/registration/author/list.do" code="msg.cancel" />
					
				</form:form>
			</fieldset>


		</div>
	</div>
</div>


