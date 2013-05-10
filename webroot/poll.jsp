<%-- 
    Document   : index
    Created on : 9-mag-2013, 19.55.19
    Author     : lenz
--%>

<%@page import="ch.loway.oss.adb.web.PollData"%>
<%@page import="ch.loway.oss.adb.web.OpenAmi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="img/display.css" rel="stylesheet">
    </head>
    <body>
    <%
            PollData p = new PollData();
            out.println( p.poll(0) );
    %>


<script>
function reload() {
	var url = "poll.jsp?";
	url += "&nocache=" + Math.random();
	//$("#pic").attr("src", url);
        location.href=url;
}

var eventTimer = setInterval( 'reload()', 3000 );
</script>


    </body>
</html>
