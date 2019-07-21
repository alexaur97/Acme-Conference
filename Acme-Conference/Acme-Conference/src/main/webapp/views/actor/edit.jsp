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

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:set var="form" value="administratorEditForm" />
</security:authorize>
<security:authorize access="hasRole('REVIEWER')">
	<jstl:set var="form" value="reviewerEditForm" />
</security:authorize>
<security:authorize access="hasAnyRole('AUTHOR,SPONSOR')">
	<jstl:set var="form" value="actorEditForm" />
</security:authorize>

<form:form action="actor/edit.do" modelAttribute="${form}" method="POST">

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.middleName" path="middleName" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.photo" path="photo" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.address" path="address" />
	<security:authorize access="hasRole('REVIEWER')">
		<acme:textarea code="actor.keyWords" path="keyWords" />
	</security:authorize>
	<br />

	
<security:authorize access="hasRole('ADMINISTRATOR')">
<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()"
				name="saveAdmin">
				<spring:message code="actor.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()"
				name="saveAdmin">
				<spring:message code="actor.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>
<security:authorize access="hasRole('REVIEWER')">
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()"
				name="saveReviewer">
				<spring:message code="actor.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()"
				name="saveReviewer">
				<spring:message code="actor.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>

</security:authorize>
<security:authorize access="hasAnyRole('AUTHOR,SPONSOR')">
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()"
				name="save">
				<spring:message code="actor.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()"
				name="save">
				<spring:message code="actor.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>

</security:authorize>

	<acme:cancel url="actor/show.do" code="actor.cancel" />
	<script type="text/javascript">
		function validatePhoneNumber() {
			var phone = document.getElementById("phone");
			if (!(phone.value)
					.match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) {
				return confirm("Phone number doesn't adhere to the correct pattern. Do you want to continue?");
			}
		}
		function validatePhoneNumberEs() {
			var phone = document.getElementById("phone");
			if (!(phone.value)
					.match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) {
				return confirm("El teléfono no se ajusta al patrón correcto. ¿Desea continuar?");
			}
		}
	</script>
</form:form>
