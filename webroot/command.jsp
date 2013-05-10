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


        <form method="POST">
            <textarea name="cmd" id="cmd" class="cmd"></textarea>
            <input type="submit">
        </form>

<%

    OpenAmi o = new OpenAmi();

    String rq = request.getParameter("cmd");
    if (rq != null) {
        if (rq.length() > 0) {
            o.sendCmd(rq);
        }
    }
%>

    </body>
</html>
