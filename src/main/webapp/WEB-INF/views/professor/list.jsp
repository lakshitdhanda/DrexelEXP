<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>DrexelEXP - Professors</title>
	<script src="<c:url value="/resources/js/jquery-1.8.1.js" />"></script>
	<script src="<c:url value="/resources/js/drexelexp.js" />"></script>
	<link href="<c:url value="/resources/css/drexelexp.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
</head>
<body>
	 <div class="header">
		<%@ include file="/WEB-INF/views/header.jsp" %>
	</div>
	<div id="page">
		<div id ="text">
			<h1>Professors</h1>
			<table>
			<tr>
			<td width="350">Name
			<hr /></td>
			<td width="20">
			Rating
			<hr />
			</td>
			</tr>
			<c:forEach items="${professors}" var="professor">
			<tr>
			
			<td id="listItem"><a href="<c:url value="/professor/show/${professor.id}"/>"><c:out value="${professor.name}" /></a></td>
			<td id="listItem">${professor.ratingString}</td>
			</tr>
			</c:forEach>
			</table>
			<hr />
			<c:if test="${pageNum!=null}">
				<c:if test="${pageNum != 1}"><a href="<c:url value="/professor/${pageNum - 1}"/>">Previous</a></c:if>
				<a href="<c:url value="/professor/${pageNum + 1}"/>">Next</a>
			</c:if>
		</div>
	</div>
 
</body>
</html>