<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>${page.name} edit</title>
</head>
<body>
Edit page ${page.name}
<form:form method="POST" action="?savePage" modelAttribute="page">
	<form:label path="name">Name</form:label><form:input path="name"/><br/> 
	<form:textarea path="whtmlBody" rows="10" cols="100" />
	<hr/>
	<input type="submit" value="Save" />
	<hr/>
</form:form>
<hr/>Current page looks like: <hr/>
 ${page.whtmlBody}
</body>
</html>
