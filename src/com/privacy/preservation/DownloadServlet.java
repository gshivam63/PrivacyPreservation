package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(name = "DownloadServlet",urlPatterns = {"/downloadservlet"})
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String fileName=request.getParameter("param1")+".xlsx";
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            //String gurufile = "test.txt";
            String gurupath = "C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/templates/";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + fileName + "\"");

            FileInputStream fileInputStream = new FileInputStream(gurupath+ fileName);

            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            out.close();
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

}
