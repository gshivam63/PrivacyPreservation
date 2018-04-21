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
            String upload_message= (String) session.getAttribute("upload_message");
            session.setAttribute("upload_message",upload_message);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_dataset", "root", "complicated");
            Statement stmt = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs_data = stmt.executeQuery("SELECT * from patient");
            int data_present=0;
            if(rs_data.next()){
                data_present=1;
            }
            con1.close();
            String url = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(url, "root", "complicated");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet ctlgs = dbmd.getCatalogs();
            ArrayList<String> database_names=new ArrayList<String>();
            while (ctlgs.next()){
                String temp=ctlgs.getString(1);
                if(data_present==0 && temp.equals("transactional_data")){

                }
                else
                    database_names.add(ctlgs.getString(1));
            }
            System.out.println("database list "+database_names);
            request.setAttribute("database_arrayList",database_names);
            conn.close();
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
            String upload_message= (String) session.getAttribute("upload_message");
            session.setAttribute("upload_message",upload_message);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_dataset", "root", "complicated");
            Statement stmt = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs_data = stmt.executeQuery("SELECT * from patient");
            int data_present=0;
            if(rs_data.next()){
                data_present=1;
            }
            con1.close();
            String url = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(url, "root", "complicated");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet ctlgs = dbmd.getCatalogs();
            ArrayList<String> database_names=new ArrayList<String>();
            while (ctlgs.next()){
                String temp=ctlgs.getString(1);
                if(data_present==0 && temp.equals("transactional_data")){

                }
                else
                    database_names.add(ctlgs.getString(1));
            }
            System.out.println("database list "+database_names);
            request.setAttribute("database_arrayList",database_names);
            conn.close();
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/ShowDatabases.jsp");
            disp.forward(request, response);
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

}
