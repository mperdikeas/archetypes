<%@ page session="true" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="mjb44.mvc.DDConstants" %>
<%@ page import="mjb44.mvc.JSPMapping" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Bad credentials
    </title>
  </head>
  <body>
    <h3>User <tt>[${badUser}]</tt> does not exist or wrong password given</h3>
    <p>Please go back to <a href='${pageContext.request.contextPath}/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.LOGIN.getPath()%>'>login page</a>
    </p>
  </body>

</html>
