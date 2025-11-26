<%-- 
    Document   : form_login
    Created on : Nov 26, 2025, 8:32:31 PM
    Author     : luliou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="auth">
            Username: <input type="text" name="user" /><br />
            Password: <input type="password" name="pass" /><br />
            <input type="submit" value="Login" />
        </form>
        ${msg}
    </body>
</html>
