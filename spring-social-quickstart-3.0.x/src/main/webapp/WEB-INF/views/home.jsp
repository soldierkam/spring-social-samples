<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Home</title>
	</head>
	<body>
	<ul>
		<li><a href="<c:url value="/signout" />">Sign Out</a></li>
	</ul>
	<h3>Your Facebook Friends</h3>
	<ul>
	<c:forEach items="${friends}" var="friend">
		<li><img src="http://graph.facebook.com/<c:out value="${friend.id}"/>/picture" align="middle"/><c:out value="${friend.name}"/></li>
	</c:forEach>

    </ul>

    <h3>Videos you likes</h3>
    <ul>
	    <c:forEach items="${likes}" var="video">
    		<li>
    		    <img src="<c:out value="${video.thumbnails[0].url}"/>" align="middle"/>
    		    <c:out value="${video.title}"/>
    		</li>
    	</c:forEach>

    </ul>
    <form method="POST" enctype="multipart/form-data" action="./upload">
         <input type="file" name="video" >
         <input type="submit">
    </form>
	</body>
</html>