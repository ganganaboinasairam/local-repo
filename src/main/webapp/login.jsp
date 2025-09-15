<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    // small HTML escaper for JSP output
    public String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Login - Oracle Database Web Application</title>
    <style>
        /* (same styles you had) */
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .container { max-width: 400px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"], input[type="password"] { width: 100%; padding: 8px; box-sizing: border-box; }
        button { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form action="<%= request.getContextPath() %>/login" method="post" autocomplete="off">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required maxlength="100">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required maxlength="100">
            </div>
            <button type="submit">Login</button>
        </form>

        <% String error = (String) request.getAttribute("error"); 
           if (error != null && !error.trim().isEmpty()) { %>
            <div class="error"><%= escapeHtml(error) %></div>
        <% } %>
    </div>
</body>
</html>
