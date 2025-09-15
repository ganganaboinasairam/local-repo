<%@ page import="java.sql.*" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <%! // small HTML escaper 
        public String escapeHtml(String s) { if (s==null) return "" ; return
            s.replace("&", "&amp;" ) .replace("<", "&lt;" ) .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;");
            }
            %>

            <% // Use a distinct session variable name to avoid shadowing the implicit 'session'
                javax.servlet.http.HttpSession userSession=request.getSession(false); if (userSession==null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp" ); return; } Boolean loggedIn=(Boolean)
                userSession.getAttribute("loggedIn"); String sessionToken=(String) userSession.getAttribute("token"); if
                (loggedIn==null || !loggedIn || sessionToken==null) { 
                	// not logged in or no token in session -> send to
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
                }

                // Resolve custId: prefer request param if present, otherwise use session attribute
                String custId = null;
                String custIdParam = request.getParameter("cust_id");
                if (custIdParam != null && !custIdParam.trim().isEmpty()) {
                custId = custIdParam.trim();
                } else {
                custId = (String) userSession.getAttribute("cust_id");
                }

                // If still no custId, either show all rows or show a message. Here we show a friendly message.
                if (custId == null || custId.trim().isEmpty()) {
                out.println("<p style='color:orange;'>No customer selected. Please return to the index page and choose a customer.</p>");
                return;
                }

                // DB variables (declared here so they are in scope for JSP expressions)
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                String custName = "";
                String branchCode = "";
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8" />
                    <title>Customer Details</title>
                    <script type="text/javascript">

                    async function openChatbox(custId, branchCode, custName) {
                    	  try {
                    	    // 1) request short-lived chat token from main app
                    	    const resp = await fetch('<%= request.getContextPath() %>/create-chat-token', {
                    	      method: 'POST',
                    	      credentials: 'include',
                    	      headers: { 'Accept': 'application/json' }
                    	    });
                    	    if (!resp.ok) throw new Error('Failed to create chat token');

                    	    const { chatToken } = await resp.json();

                    	    // 2) build a hidden form to POST into the chat app launch endpoint (token not in URL)
                    	    const form = document.createElement('form');
                    	    form.method = 'POST';
                    	    form.target = 'chatboxFrame';
                    	    // point at chatapp launch servlet (absolute or full URL)
                    	    form.action = 'http://localhost:8085/chatboxEECE1/chat-init';

                    	    // hidden fields
                    	    function addField(name, value) {
                    	      const i = document.createElement('input'); i.type = 'hidden'; i.name = name; i.value = value || '';
                    	      form.appendChild(i);
                    	    }
                    	    addField('chatToken', chatToken);
                    	    addField('custId', custId);
                    	    addField('branchCode', branchCode);
                    	    addField('custName', custName);

                    	    document.body.appendChild(form);

                    	    // show iframe and submit
                    	    document.getElementById('chatboxFrame').style.display = 'block';
                    	    form.submit();

                    	    // cleanup
                    	    setTimeout(() => document.body.removeChild(form), 1000);
                    	  } catch (e) {
                    	    alert('Cannot open chat: ' + e.message);
                    	  }
                    	}


                    </script>
                </head>

                <body>
                    <h2>Customer Details for <%= escapeHtml(custId) %>
                    </h2>

                    <% try { Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn=DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:xe" , "SAIRAM" , "SAIRAM" );
                        String sql="SELECT cust_id, cust_name, branch_code FROM data_master WHERE cust_id = ?" ;
                        ps=conn.prepareStatement(sql); ps.setString(1, custId); rs=ps.executeQuery(); if (rs.next()) {
                        custName=rs.getString("cust_name"); branchCode=rs.getString("branch_code"); } else {
                        out.println("<p style='color:orange;'>No customer found for id: " + escapeHtml(custId) + "</p> ");
                        }
                        } catch (Exception e) {
                        out.println("<p style='color:red;'> Error: " + escapeHtml(e.getMessage()) + "</p>");
                        } finally {
                        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
                        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
                        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
                        }
                        %>

                        <table border="1" cellpadding="5">
                            <tr>
                                <th>Customer ID</th>
                                <td>
                                    <%= escapeHtml(custId) %>
                                </td>
                            </tr>
                            <tr>
                                <th>Customer Name</th>
                                <td>
                                    <%= escapeHtml(custName) %>
                                </td>
                            </tr>
                            <tr>
                                <th>Branch Code</th>
                                <td>
                                    <%= escapeHtml(branchCode) %>
                                </td>
                            </tr>
                        </table>

                        <br>
                        <button id="openChat" onclick="openChatbox('<%= escapeHtml(custId) %>', '<%= escapeHtml(branchCode) %>', '<%= escapeHtml(custName) %>');">Open Chatbox</button>

                        <br><br>
                        <iframe id="chatboxFrame" name="chatboxFrame" style="display:none; width:800px; height:500px; border:1px solid #ccc;"></iframe>

                </body>

                </html>