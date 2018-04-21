package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@WebServlet(name = "DownloadAnnonymizedServlet",urlPatterns = {"/downloadannonymizedservlet"})
public class DownloadAnnonymizedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String fileName=(String)session.getAttribute("fileName");
            session.setAttribute("fileName", fileName);
            String upload_message= (String) session.getAttribute("upload_message");
            session.setAttribute("upload_message",upload_message);
            fileName=fileName+".xlsx";
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String gurupath = "C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/uploaded/";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
            FileInputStream fileInputStream = new FileInputStream(gurupath+ fileName);
            int j;
            while ((j = fileInputStream.read()) != -1) {
                out.write(j);
            }
            fileInputStream.close();
            out.close();
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get clicked");

    }

}
