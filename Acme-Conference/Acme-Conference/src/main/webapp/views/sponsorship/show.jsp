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


<a href="${sponsorship.targetUrl}"><img src="${ sponsorship.banner}"
		alt="Acme Rookies Co., Inc."style="width: 150px; height: 50px;"/></a>
	
</br><spring:message code="sponsorship.targetUrl" />: <jstl:out value="${sponsorship.targetUrl}"></jstl:out>
</br><spring:message code="sponsorship.conferences" />: <jstl:out value="${sponsorship.conference.title}"></jstl:out>
<br/><spring:message code="sponsorship.sponsor"/>: <jstl:out value="${sponsorship.sponsor.name} ${sponsorship.sponsor.surname}"></jstl:out>
<br/>
<h3><spring:message code="sponsorship.creditCard" /></h3>
<spring:message code="sponsorship.holderName"/>: <jstl:out value="${sponsorship.creditCard.holderName}"></jstl:out>
<br/><spring:message code="sponsorship.brandName"/>: <jstl:out value="${sponsorship.creditCard.brandName}"></jstl:out>
<br/><spring:message code="sponsorship.number"/>: <jstl:out value="${sponsorship.creditCard.number}"></jstl:out>
<br/><spring:message code="sponsorship.expirationMonth"/>: <jstl:out value="${sponsorship.creditCard.expirationMonth}"></jstl:out>
<br/><spring:message code="sponsorship.expirationYear"/>: <jstl:out value="${sponsorship.creditCard.expirationYear}"></jstl:out>
	<br/><spring:message code="sponsorship.cvv"/>: <jstl:out value="${sponsorship.creditCard.cvv}"></jstl:out>
			
