<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form method="post" action="/drexelexp/course/search">

	<table>
	<tr>
	
		<td><form:label name="query">Course:</form:label></td>
		<td><form:input name="query" /></td>
	</tr>

	<tr>
		<td colspan="2">
			<input type="submit" value="Search"/>
		</td>
	</tr>
</table>	
	
</form:form>