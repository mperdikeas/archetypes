<%@ page import="mjb44.mvc.JSPMapping" %>
<%@ page import="mjb44.mvc.DDConstants" %>

<p>
  These are all the pages you can visit:
  <ul>
    <li><a href="${pageContext.request.contextPath}/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.EXAMPLE1.getPath()%>">the 1st example page</a></li>        
    <li><a href="${pageContext.request.contextPath}/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.EXAMPLE2.getPath()%>">the 2nd example page</a></li>
    <li><a href="${pageContext.request.contextPath}/<%=DDConstants.SERVLET_PATH%>/<%=JSPMapping.LOGOUT.getPath()%>">the logout page</a></li>    
  </ul>
</p>
