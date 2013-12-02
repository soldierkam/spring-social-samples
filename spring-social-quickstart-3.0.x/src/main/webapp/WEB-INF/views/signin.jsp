<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Sign In</title>
	</head>
	<body>
		<form action="<c:url value="/signin/facebook" />" method="POST">
		    <button type="submit">Sign in with Facebook</button>
		    <input type="hidden" name="scope" value="email,publish_stream,offline_access" />		    
		</form>

		<form action="<c:url value="/signin/vimeo" />" method="POST">
            <button type="submit">Sign in with Vimeo</button>
            <input type="hidden"/>
        </form>
	</body>
</html>
