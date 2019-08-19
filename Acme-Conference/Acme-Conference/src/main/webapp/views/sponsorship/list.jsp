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
<display:table name="sponsorships" id="sponsorship" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
			<display:column titleKey="sponsorship.banner">
	<a href="${sponsorship.targetUrl}"><img src="${ sponsorship.banner}"
		alt="Acme Conference Co., Inc."style="width: 150px; height: 50px;"/></a>
			</display:column>
		
	<display:column titleKey="sponsorship.show">
		<acme:button url="/sponsorship/sponsor/show.do?sponsorshipId=${sponsorship.id}"
			code="sponsorship.show" />
	</display:column>
		<display:column titleKey="sponsorship.edit">
			<acme:button url="/sponsorship/sponsor/edit.do?sponsorshipId=${sponsorship.id}" code="sponsorship.edit" />
	</display:column>
	<display:column titleKey="sponsorship.delete">
		<acme:button url="/sponsorship/sponsor/delete.do?sponsorshipId=${sponsorship.id}"
			code="sponsorship.delete" />
	</display:column>
</display:table>
<acme:button url="/sponsorship/sponsor/create.do" code="sponsorship.create"/>
				
