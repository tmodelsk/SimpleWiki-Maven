<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>${page.name}</title>
</head>
<body>

<table>
<tr>
	<td>
		<img alt="Wiki icon" width="100" height="100" src="resources/wiki-button-simple.png">
		<p>${wiki.name}</p>
		<br/><a href="./">Main</a>
	</td>
	<td>
		<a href="?edit">Edit</a>
		${page.htmlBody}
	</td>
</tr>
</table>
</body>
</html>
