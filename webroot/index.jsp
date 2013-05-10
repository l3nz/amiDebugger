<%-- 
    Document   : index
    Created on : 10-mag-2013, 16.42.42
    Author     : lenz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lenz's amiDebugger</title>
    </head>
    <body>
        <h1>Lenz's amiDebugger</h1>

        Please enter here the AMI data for your Asterisk server.

        <hr>



        <form method="POST"  action="run.jsp">

        <input type="hidden" name="op" value="connect">

            <table>

                <tr>
                    <td>Asterisk server:</td>
                    <td><input type="text" name="server"></td>
                </tr>

                <tr>
                    <td>AMI port:</td>
                    <td><input type="text" name="port" value="5038"></td>
                </tr>

                <tr>
                    <td>AMI user:</td>
                    <td><input type="text" name="login"></td>
                </tr>

                <tr>
                    <td>AMI password:</td>
                    <td><input type="password" name="pass"></td>
                </tr>

                <tr>
                    <td colspan="2"><input type="submit"></td>
                </tr>



            </table>


        </form>


    </body>
</html>
