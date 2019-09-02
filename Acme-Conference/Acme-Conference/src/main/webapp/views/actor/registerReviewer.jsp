<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="actor/registerReviewer.do" modelAttribute="registerReviewerForm" method="post">

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.middleName" path="middleName" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.address" path="address" />
	<acme:textbox code="actor.photo" path="photo" />
	<acme:textarea code="actor.keyWords" path="keyWords" />
	
	<br />
	<acme:textbox code="actor.username" path="username" />
	<acme:password code="actor.password" path="password" />
	<acme:password code="actor.repeatPassword" path="repeatPassword" />
	<br />
	<acme:checkbox code="actor.terms" path="terms"/>
		
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()" name="saveReviewer">
				<spring:message code="actor.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()" name="saveReviewer">
				<spring:message code="actor.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
	<acme:cancel url="/#" code="actor.cancel" />
<script type="text/javascript">
		function validatePhoneNumber() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("\\+\\d{2}([ ]{1}[(]{1}\\d{1,3}[)]{1})? \\d{4,}|\\+\\d{2} \\d{4,}|\\d{4,}|Null")) { return confirm("Phone number doesn't adhere to the correct pattern. Do you want to continue?"); }
		}

		function validatePhoneNumberEs() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("\\+\\d{2}([ ]{1}[(]{1}\\d{1,3}[)]{1})? \\d{4,}|\\+\\d{2} \\d{4,}|\\d{4,}|Null")) { return confirm("El teléfono no se ajusta al patrón correcto. ¿Desea continuar?"); }
		}
	</script>
</form:form>