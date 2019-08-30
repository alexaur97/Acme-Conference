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


<spring:message code="panel.list"/> 
<br>
<display:table pagesize="5" name="panels" id="panel"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="panel.title"  property="title" />
	<display:column titleKey="panel.startMoment"  property="startMoment" />
	<display:column titleKey="panel.summary"  property="summary" />
	<display:column titleKey="panel.edit">
		<acme:cancel url="/panel/administrator/edit.do?panelId=${panel.id}" code="panel.edit" />
	</display:column>
	<display:column titleKey="panel.show">
		<acme:cancel url="/panel/administrator/show.do?panelId=${panel.id}" code="panel.show" />
	</display:column>
	
</display:table>



