package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(name = "GetDatabaseServlet",urlPatterns = {"/getdatabaseservlet"})
public class GetDatabaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(url, "root", "complicated");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet ctlgs = dbmd.getCatalogs();
            ArrayList<String> database_names=new ArrayList<String>();
            while (ctlgs.next()){
                database_names.add(ctlgs.getString(1));
            }
            request.setAttribute("database_arrayList",database_names);
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/ShowDatabases.jsp");
            disp.forward(request, response);
        }
         catch (Exception e) {
            System.out.print(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(url, "root", "complicated");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet ctlgs = dbmd.getCatalogs();
            ArrayList<String> database_names=new ArrayList<String>();
            while (ctlgs.next()){
                database_names.add(ctlgs.getString(1));
            }
            request.setAttribute("database_arrayList",database_names);
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/ShowDatabases.jsp");
            disp.forward(request, response);
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

}
