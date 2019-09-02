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

<form:form action="finder/author/view.do" modelAttribute="finder"  class="form-horizontal" method="post">
	<div class="form-group ">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<acme:textbox code="finder.keyword" path="keyword"/>
	<jstl:if test="${lang eq 'en'}">	
		<acme:select items="${categories}" itemLabel="name" code="finder.category" path="category"/>
	</jstl:if>
	<jstl:if test="${lang eq 'es'}">
		<acme:select items="${categories}" itemLabel="nameEs" code="finder.category" path="category"/>
	</jstl:if>
	<acme:textbox placeholder = "dd-MM-yyyy"  code="finder.minDate" path="minDate"/>
	<acme:textbox placeholder = "dd-MM-yyyy"  code="finder.maxDate" path="maxDate"/>
	<acme:textbox type="number" step="0.01" code="finder.maxFee" path="maxFee"/>
	<acme:submit name="save" code="finder.search"/>
	<acme:submit name="clean" code="finder.clean"/>

</div>	
</form:form>
</fieldset>

<display:table pagesize="5" name="conferences" id="conference"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="conference.title" property="title" />
	<display:column titleKey="conference.submission"
		property="submissionDeadline" />
	<display:column titleKey="conference.startDate" property="startDate" />
	<display:column titleKey="conference.endDate" property="endDate" />
	<display:column titleKey="conference.fee" property="fee" />
	<jstl:if test="${lang eq 'en'}">
		<display:column titleKey="conference.category" property="category.name" />	
	</jstl:if>
	<jstl:if test="${lang eq 'es'}">
		<display:column titleKey="conference.category" property="category.nameEs" />	
	</jstl:if>
	<display:column titleKey="conference.show">
			<acme:cancel
				url="/conference/show.do?conferenceId=${conference.id}"
				code="conference.show" />
		</display:column>
	

</display:table>

</div>
</div>	
</div>	
</div>	