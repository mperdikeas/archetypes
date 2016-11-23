<%@ page contentType="text/html" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="mjb44.mvc.DDConstants" %>
<%@ page import="mjb44.mvc.JSPMapping"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>JSP Model2 architecture</title>
  </head>
  <body>
    <c:set var="thisPage" value="<%=this.getClass().getSimpleName().replaceAll(\"_\", \".\")%>" scope="request"/>
      <jsp:include page="header.jsp"/>
      <p>
        This is the logout page. Click the below button if you wish to
        logout of this awesome app.
      </p>
      <!-- The path in the action attribute of the form element below
           doesn't have to be /servlet/logout since the URL rewriting
           filter will rewrite the URL anyways-->
      <form action="<%=request.getContextPath()%>/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.LOGOUT.getPath()%>"
            method="POST">
      <input type="submit" name="Submit" value="I am too dumb to use this app, please log me out"/>
    </form>      
  </body>

</html>
