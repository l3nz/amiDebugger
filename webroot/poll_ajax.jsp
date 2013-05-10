
<%@page import="ch.loway.oss.adb.web.WebTools"%>
<%@page import="ch.loway.oss.adb.web.PollData"%>
<%@page import="ch.loway.oss.adb.web.OpenAmi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
            PollData p = new PollData();
            String filter = WebTools.getS(request, "filter");
            out.println( p.poll(0, filter) );
%>


