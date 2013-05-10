<%-- 
    Document   : index
    Created on : 9-mag-2013, 19.55.19
    Author     : lenz
--%>

<%@page import="ch.loway.oss.adb.web.WebTools"%>
<%@page import="ch.loway.oss.adb.web.OpenAmi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title></title>
        <link href="img/display.css" rel="stylesheet">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
        <script src="./img/amiDebugger.js"></script>
    </head>
    <body>
        <table border="0">
            <tr>
                <td valign="top">
                    <div id="results">...loading....</div>
                </td>
                <td valign="top">

                    <h3>Lenz's amiDebugger</h3>

                    Last post to server: <br>
                    <div id="postresults">---</div>
                    <hr>

                    <input type="button" value="Reload" onclick="sendAction('Reload');"><br>
                    <input type="button" value="Queue Summary" onclick="sendAction('QueueSummary');"><br>
                    <input type="button" value="List Commands" onclick="sendAction('ListCommands');"><br>
                    <input type="button" value="Core Status" onclick="sendAction('CoreStatus');"><br>
                    <input type="button" value="Core Settings" onclick="sendAction('CoreSettings');"><br>

                    <hr>
                    Text filter: <br>
                    <input type="text" value="" id="filter">

                    <hr>

                    <a href="index.jsp">Use other server</a>

                    <hr>
                    
                    Links: <br>

                    <a href="http://www.voip-info.org/wiki/view/Asterisk+manager+API" target="ami">AMI on Voip-Info</a> <br>
                    <a href="http://queuemetrics.com" target="qm">QueueMetrics</a> <br>
                    <a href="http://wombatdialer.com" target="wbt">WombatDialer</a> <br>
                    
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <textarea name="cmd" id="cmd" class="cmd"></textarea>
                </td>
                <td valign="top" align="center">
                    <input type="button" value="Send Command" onclick="sendCmdArea();">

                    <p>
                        <a href="https://github.com/l3nz" target="new"><img border="0" src="img/fatcat.png"></a>

                </td>
            </tr>


        </table>
        

<%
// Set Up AMI

String op = WebTools.getS(request, "op");

if ( op.equalsIgnoreCase("connect")) {

    OpenAmi o = new OpenAmi();
    String srv = WebTools.getS(request, "server");
    int    port = WebTools.getI(request, "port");
    String login = WebTools.getS(request, "login");
    String pass = WebTools.getS(request, "pass");

    o.open( srv, port, login, pass);
}

%>
        
    </body>
</html>
