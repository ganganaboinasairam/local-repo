package testproject.servlet;

import testproject.dao.DataMasterDAO;
import testproject.model.DataMaster;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/dataMaster")
public class DataMasterServlet extends HttpServlet {
     
    private DataMasterDAO dataMasterDAO;
    
    @Override
    public void init() throws ServletException {
        dataMasterDAO = new DataMasterDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create or get session
        HttpSession session = request.getSession(true);
        session.setAttribute("lastAccess", new java.util.Date().toString());

        // Check if logged in
        if (session.getAttribute("loggedIn") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Validate token
        String tokenParam = request.getParameter("token");
        String sessionToken = (String) session.getAttribute("token");
        if (tokenParam == null || !tokenParam.equals(sessionToken)) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        
        try {
            List<DataMaster> dataList = dataMasterDAO.getAllData();
            
            // Generate HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Data Master Information</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #333; }");
            out.println("table { border-collapse: collapse; width: 100%; margin-top: 20px; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Data Master Information</h1>");
            
            if (dataList.isEmpty()) {
                out.println("<p>No data found in DATA_MSTER table.</p>");
            } else {
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Data Code</th><th>Data Value</th><th>Description</th></tr>");
                
                for (DataMaster data : dataList) {
                    out.println("<tr>");
                    out.println("<td>" + data.getId() + "</td>");
                    out.println("<td>" + data.getDataCode() + "</td>");
                    out.println("<td>" + data.getDataValue() + "</td>");
                    out.println("<td>" + data.getDescription() + "</td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
            }
            
            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception e) {
            out.println("<h1>Error</h1>");
            out.println("<p>Error retrieving data: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
