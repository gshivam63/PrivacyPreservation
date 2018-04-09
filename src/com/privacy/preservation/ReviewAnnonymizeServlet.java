package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
@WebServlet(name = "ReviewAnnonymizeServlet",urlPatterns = {"/reviewannonymizeservlet"})
public class ReviewAnnonymizeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String admin_username =(String) session.getAttribute("USERNAME");
        session.setAttribute("USERNAME", admin_username);
        String fileName=request.getParameter("myField_fileName");
        System.out.println(fileName+" ==filename");
    }
}


