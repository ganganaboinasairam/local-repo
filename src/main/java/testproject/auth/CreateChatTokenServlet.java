package testproject.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//CreateChatTokenServlet.java (main app)
@WebServlet("/create-chat-token")
public class CreateChatTokenServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     HttpSession s = req.getSession(false);
     if (s == null || s.getAttribute("loggedIn") == null) {
         resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
         return;
     }
     String custId = (String) s.getAttribute("cust_id");
     if (custId == null) {
         resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
         return;
     }
     // create a short-lived token (e.g., 60 seconds)
     String token = testproject.auth.ChatTokenManager.create(custId, 60);
     resp.setContentType("application/json;charset=UTF-8");
     resp.getWriter().write("{\"chatToken\":\"" + token + "\"}");
 }
}

