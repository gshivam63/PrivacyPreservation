package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(name = "AdminLoginServlet",urlPatterns = {"/adminloginservlet"})
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String admin_username = request.getParameter("username");
            String admin_password = request.getParameter("password");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admindetails", "root", "complicated");
            PreparedStatement pst = con.prepareStatement("SELECT username,password FROM login_details where username=?");
            pst.setString(1, admin_username);
            ResultSet rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                String name = rs.getString("username");
                String pass = rs.getString("password");
                RequestDispatcher disp = null;
                if (!admin_username.equalsIgnoreCase(name)) {
                    response.sendRedirect("index.jsp?err=UserId Does not Exist. Retry...");
                } else if (admin_username.equalsIgnoreCase(name) && admin_password.equals(pass)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("USERNAME", name);
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/AfterAdminLogin.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("index.jsp?err=Login Authentication Failed.Retry...");
                }
            } else {
                response.sendRedirect("index.jsp?err=User Id does Not Exist...Retry...");
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
