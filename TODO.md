# TODO: Hide Actual URLs on Redirects

- [x] Edit LoginServlet.java: Change sendRedirect to forward in doPost (successful login) and doGet
- [x] Edit DataMasterServlet.java: Change sendRedirect to forward for authentication failures
- [x] Edit index.jsp: Replace response.sendRedirect with <jsp:forward> for auth checks
- [x] Edit dataDisplay.jsp: Replace response.sendRedirect with <jsp:forward> for auth checks
