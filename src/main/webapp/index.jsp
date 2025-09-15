<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    // small HTML escaper for safe output
    public String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
%>
<%
    // avoid redeclaring the implicit 'session' variable; use userSession instead
    javax.servlet.http.HttpSession userSession = request.getSession(false);
    if (userSession == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Boolean loggedIn = (Boolean) userSession.getAttribute("loggedIn");
    if (loggedIn == null || !loggedIn) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String custId = (String) userSession.getAttribute("cust_id");
    String token = (String) userSession.getAttribute("token");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Oracle Database Web Application</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .container { max-width: 800px; margin: 0 auto; }
        .link-box { display: inline-block; padding: 10px 20px; margin: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .link-box:hover { background-color: #0056b3; }
        .meta { margin-top: 15px; color: #444; }
        .logout { display:inline-block; margin-left:10px; background:#cc3333; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Oracle Database Web Application</h1>
        <h2>DATA_MSTER Table Viewer</h2>

        <p>Signed in as: <strong><%= escapeHtml((String) userSession.getAttribute("username")) %></strong></p>

        <!-- Links no longer include token in URL (server keeps them in session) -->
        <a href="<%= request.getContextPath() %>/dataDisplay.jsp" class="link-box">View via JSP</a>
        <a href="<%= request.getContextPath() %>/dataMaster" class="link-box">View via Servlet</a>

        <!-- optional logout (implement LogoutServlet to invalidate session) -->
        <a href="<%= request.getContextPath() %>/logout" class="link-box logout">Logout</a>

        <div class="meta">
            <p><strong>Internal (server-side) values — not shown in URL:</strong></p>
            <ul>
                <li>Customer ID (cust_id): <%= escapeHtml(custId) %></li>
                <li>Session token: <%= escapeHtml(token == null ? "(not set)" : token) %></li>
            </ul>
            <p><em>Note: only show the token here for debugging. In production avoid printing tokens into pages.</em></p>
        </div>

        <h3>Setup Instructions:</h3>
        <ol>
            <li>Ensure Oracle database is running</li>
            <li>Create the DATA_MSTER table if it doesn't exist</li>
            <li>Add Oracle JDBC driver (ojdbc8.jar or ojdbc11.jar) to WEB-INF/lib</li>
            <li>Update database credentials in DBConnection.java if needed</li>
        </ol>

        <h3>Sample SQL to create table:</h3>
        <pre>
CREATE TABLE DATA_MSTER (
    ID NUMBER PRIMARY KEY,
    DATA_CODE VARCHAR2(50),
    DATA_VALUE VARCHAR2(100),
    DESCRIPTION VARCHAR2(255)
);

INSERT INTO DATA_MSTER VALUES (1, 'CODE1', 'Value1', 'First data record');
INSERT INTO DATA_MSTER VALUES (2, 'CODE2', 'Value2', 'Second data record');
COMMIT;
        </pre>
    </div>
</body>
</html>
