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


<spring:message code="category.list"/> 
<br>
<display:table pagesize="5" name="categories" id="category"
	requestURI="${requestURI}" class="displaytag table">
	<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
	<display:column titleKey="category.name"  property="name" />
	 </jstl:when>
    <jstl:otherwise>
	<display:column titleKey="category.nameEs"  property="nameEs" />
	 </jstl:otherwise>
	</jstl:choose>
	<display:column titleKey="category.root"  property="root" />
	<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
	<display:column titleKey="category.parent"  property="parent.name" />
	</jstl:when>
    <jstl:otherwise>
    <display:column titleKey="category.parent"  property="parent.nameEs" />
     </jstl:otherwise>
	</jstl:choose>
	
	<display:column titleKey="category.edit">
		<jstl:if test="${category.root ne true}">
		<acme:cancel url="/category/administrator/edit.do?categoryId=${category.id}" code="category.edit" />
		</jstl:if>
	</display:column>
	
</display:table>

<acme:cancel url="/category/administrator/create.do" code="category.create" />




