<%@ page contentType="text/html" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>JSP Model2 architecture</title>
  </head>
  <body>
    <c:set var="thisPage" value="<%=this.getClass().getSimpleName().replaceAll(\"_\", \".\")%>" scope="request"/>
    <jsp:include page="header.jsp"/>
    <jsp:include page="footer.jsp"/>
  </body>

</html>
