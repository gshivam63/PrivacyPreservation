package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(name = "GetColumnServlet",urlPatterns = {"/getcolumnservlet"})
public class GetColumnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            System.out.println("4");
            String database_name = request.getParameter("database_name");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/"+database_name;
            Connection conn = DriverManager.getConnection(url, "root", "complicated");
            DatabaseMetaData dbmd = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            ArrayList<String> tables_list=new ArrayList<String>();
            while (rs.next()) {
                tables_list.add(rs.getString("TABLE_NAME"));
            }
            request.setAttribute("database_tablelist",tables_list);
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/ShowColumns.jsp");
            disp.forward(request, response);
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }
}
