package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
@WebServlet(name = "AdminLogoutServlet",urlPatterns = {"/adminlogoutservlet"})
public class AdminLogoutServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
            response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
            response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
            response.setHeader("Pragma","no-cache");
            HttpSession s1 = request.getSession(false);
            if (s1 != null) {
                s1.invalidate();
                s1 = null;
            }
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/index.jsp?msg=Logout Successfully.");
            disp.forward(request, response);
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }
}

