<%-- 
    Document   : index
    Created on : 9-mag-2013, 19.55.19
    Author     : lenz
--%>

<%@page import="ch.loway.oss.adb.web.OpenAmi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<frameset rows="*,180px">
  <frame id="pic"src="poll.jsp">
  <frame src="command.jsp">  
</frameset>

<%

    OpenAmi o = new OpenAmi();

        o.open("10.10.5.19", 5038, "wombat", "dials");

%>


</html>
