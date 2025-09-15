package testproject.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // demo-only auth check
        boolean valid = "00001".equals(username) && "password".equals(password);

        if (valid) {
            // Invalidate old session (session-fixation protection)
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // Create a fresh session
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            // store needed info in session (NOT in URL)
            session.setAttribute("loggedIn", Boolean.TRUE);
            session.setAttribute("username", username);
            session.setAttribute("cust_id", username); // or map username -> cust_id
            String token = UUID.randomUUID().toString();
            session.setAttribute("token", token);

            // PRG: redirect to index.jsp WITHOUT parameters
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
