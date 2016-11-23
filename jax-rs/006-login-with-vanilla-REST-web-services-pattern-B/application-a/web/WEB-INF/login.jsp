<%@ page session="true" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="mjb44.mvc.DDConstants" %>
<%@ page import="mjb44.mvc.JSPMapping" %>
<%@ page import="mjb44.mvc.RequestScopeAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<html>
  <head>
    <title>login page</title>
  </head>
  <c:set var="hackAttemptName" value="<%=RequestScopeAttributes.HACK_ATTEMPT.getValue()%>"/>
    <c:if test="${requestScope[hackAttemptName]!=null}">
      <p>
        <i style='color:red;'>Illegal entry attempt detected from IP: <tt>${requestScope[hackAttemptName].ipAddress}</tt> and using agent:<br> <tt>${requestScope[hackAttemptName].userAgent}</tt></br>&mdash; your details have been recorded
        </i>
        </p>
  </c:if>
  <body>
    <form action="<%=request.getContextPath()%>/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.LOGIN.getPath()%>"
          method="POST">
      Username:
      <input type="text" name="username" autofocus/>
      <br/>
      Password:
      <input type="password" name="password"/>
      <br/>
      <input type="submit" name="Submit" value="ok"/>
    </form>
  </body>

</html>
