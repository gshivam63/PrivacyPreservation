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
@WebServlet(name = "DeleteAnnonymizedServlet",urlPatterns = {"/deleteannonymizedservlet"})
public class DeleteAnnonymizedServlet extends HttpServlet {
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
            File file = new File("C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/uploaded/"+fileName);
            if(file.delete()){
                System.out.println("File deleted successfully");
            }
            else{
                System.out.println("Failed to delete the file");
            }
            request.setAttribute("delete_msg","Annonymized file has been deleted");
            RequestDispatcher disp = null;
            disp = this.getServletContext().getRequestDispatcher("/Review.jsp");
            disp.forward(request, response);
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get clicked");

    }

}
