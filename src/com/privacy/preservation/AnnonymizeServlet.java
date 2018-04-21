package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
@WebServlet(name = "AnnonymizeServlet",urlPatterns = {"/annonymizeservlet"})
public class AnnonymizeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String upload_message= (String) session.getAttribute("upload_message");
            session.setAttribute("upload_message",upload_message);
            String fileName=request.getParameter("myField_fileName");
            session.setAttribute("fileName",fileName);
            String FILE_NAME = "C:\\Users\\shivam\\IdeaProjects\\PrivacyPreservation\\web\\templates\\"+fileName+".xlsx";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+fileName, "root", "complicated");
                Statement stmt = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs_tablename = stmt.executeQuery("SELECT table_name from information_schema.tables where table_schema='"+fileName+"'");
                Set<String> blocked_col=new HashSet<String>();
                while(rs_tablename.next()){
                    blocked_col.add(rs_tablename.getString(1)+"_name");
                }
                if(fileName.equals("transactional_data"))
                    blocked_col.add("patient_name");
                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = datatypeSheet.iterator();
                ArrayList<Object> column_values=new ArrayList<Object>();
                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    while (cellIterator.hasNext()) {
                        Cell currentCell = cellIterator.next();
                        //getCellTypeEnum shown as deprecated for version 3.15
                        //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            if(!blocked_col.contains(currentCell.getStringCellValue()))
                                column_values.add(currentCell.getStringCellValue());
                        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                            if(!blocked_col.contains(currentCell.getNumericCellValue()))
                                column_values.add(currentCell.getNumericCellValue());
                        }
                    }
                }
                request.setAttribute("column_arrayList",column_values);
                request.setAttribute("fileName",fileName);
                RequestDispatcher disp = null;
                disp = this.getServletContext().getRequestDispatcher("/AnnonymizeColumns.jsp");
                disp.forward(request, response);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


