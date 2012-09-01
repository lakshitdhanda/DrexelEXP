<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Ingest</title>
</head>
<body>
<h1>
	Ingest
</h1>

<ul>
<c:forEach items="${colleges}" var="college">
<li>
	<h4><c:out value="${college.getName()}"/></h4>
	<c:forEach items="${college.getSubjects()}" var="subject">
		<h3><c:out value="${subject.getName()}"/></h3>
	</c:forEach>
</li>
</c:forEach>
</ul>

</body>
</html>
