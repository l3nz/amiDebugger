<%-- 
    Document   : index
    Created on : 9-mag-2013, 19.55.19
    Author     : lenz
--%>

<%@page import="ch.loway.oss.adb.web.PollData"%>
<%@page import="ch.loway.oss.adb.web.OpenAmi"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    OpenAmi o = new OpenAmi();

    String rq = request.getParameter("cmd");
    if (rq != null) {
        if (rq.length() > 0) {
            o.sendCmd(rq);
        }
    }

    out.println( new Date() );
%>



