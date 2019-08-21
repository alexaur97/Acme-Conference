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

<form:form action="actor/registerAuthor.do" modelAttribute="registerSponsorAndAuthorForm" method="post">

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.middleName" path="middleName" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.address" path="address" />
	<acme:textbox code="actor.photo" path="photo" />
	<br />
	<acme:textbox code="actor.username" path="username" />
	<acme:password code="actor.password" path="password" />
	<acme:password code="actor.repeatPassword" path="repeatPassword" />
	<br />
	<acme:checkbox code="actor.terms" path="terms"/>
	
	<acme:submit name="saveAuthor" code="actor.save" />
	<acme:cancel url="/#" code="actor.cancel"/>
	
</form:form>