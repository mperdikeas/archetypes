<%@ page contentType="text/html" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="mjb44.mvc.RequestScopeAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>JSP Model2 architecture</title>
  </head>
  <body>
    <%
    Boolean alreadyLoggedInRedirectB = RequestScopeAttributes.get(
        request
      , RequestScopeAttributes.ALREADY_LOGGEDIN_REDIRECT
      , Boolean.class);
    boolean alreadyLoggedInRedirect = (alreadyLoggedInRedirectB!=null)&&alreadyLoggedInRedirectB;
    System.out.printf("\n\n**** alreadyLoggedInRedirect evaluated to [%b]\n", alreadyLoggedInRedirect);
    // actually I shouldn't be doing this:
    //     request.setAttribute("alreadyLoggedInRedirect", alreadyLoggedInRedirect);
    // ... but rather, that:
    //    RequestScopeAttributes.set(RequestScopeAttributes.ALREADY_LOGGEDIN_REDIRECT, alreadyLoggedInRedirect);
    %>
    <c:set var="alreadyLoggedInRedirectName" value="<%=RequestScopeAttributes.ALREADY_LOGGEDIN_REDIRECT.getValue()%>"/>
    <c:if test="${requestScope[alreadyLoggedInRedirectName]}">    
        <i style='color:red;'>You have been redirected to this page (which serves as default) as you were already logged-in.</i>
    </c:if>
    <%-- look at example2.jsp for a less verbose way to achieve the same effect --%>
    <%
        String thisPage = this.getClass().getSimpleName().replaceAll("_", ".");
        System.out.printf("thisPage is: [%s]\n", thisPage);
        request.setAttribute("thisPage", thisPage);
    %>
    <jsp:include page="header.jsp"/>
    <jsp:include page="footer.jsp"/>
    <p>
    1+2+3 = ${1+2+3}
    </p>
    <p>
      Your IP address is: <%
      out.print(request.getRemoteAddr());
      %>
    </p>
    <%!Date      now ; %>
    <%!Calendar nowC ; %>
    <%
        now = new Date();
        nowC = Calendar.getInstance();
    %>
    <p>
      <%-- the below can't be given as a JSP EL expression --%>
      Now is: <%= now.toLocaleString() %>
    </p>
    <p>
      <%-- the below can't be given as a JSP EL expression --%>
      Now (from calendar) is: <%= nowC.getTime() %>
    </p>    
    <%
        Boolean weekEnd = null;    
        int day = nowC.get(Calendar.DAY_OF_WEEK);
        if ((day==Calendar.SATURDAY) || (day==Calendar.SUNDAY))
            weekEnd = true;
        else
            weekEnd = false;
        %>
    <p>
      Today is <%= (new SimpleDateFormat("EEEE", Locale.ENGLISH)).format(now) %> which is <%= weekEnd?"week end":"a work day" %>
        <% if (!weekEnd) { %>
          &mdash; weekend is <%= Calendar.SATURDAY-day %> days away (including today).<br/>
          If one wanted to be more precise, one might also say that weekend is
          <%
          nowC.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
          nowC.set(Calendar.HOUR_OF_DAY, 0);
          nowC.set(Calendar.MINUTE, 0);
          nowC.set(Calendar.SECOND, 0);
          Date weekEndStart = nowC.getTime();
          long millisNow          =          now.getTime();
          long millisWeekEndStart = weekEndStart.getTime();
          int secsDiff                = (int) (millisWeekEndStart/1000 - millisNow/1000);
          int hoursDiff = Math.round( secsDiff / 3600 );
          out.print(String.format("%d seconds or approximately %d hours away", secsDiff, hoursDiff));
          %>
        <% } else { %>
            &mdash; you have <%= day==Calendar.SUNDAY?1:2 %> days to rest (including today).
        <% } %>
    </p>
  </body>

</html>
