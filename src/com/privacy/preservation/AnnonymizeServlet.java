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
@WebServlet(name = "AnnonymizeServlet",urlPatterns = {"/annonymizeservlet"})
public class AnnonymizeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String fileName=request.getParameter("myField_fileName");
            String FILE_NAME = "C:\\Users\\shivam\\Downloads\\"+fileName+".xlsx";
            try {
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
                            column_values.add(currentCell.getStringCellValue());
                        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                            column_values.add(currentCell.getNumericCellValue());
                        }
                    }
                    request.setAttribute("column_arrayList",column_values);
                    request.setAttribute("fileName",fileName);
                    RequestDispatcher disp = null;
                    disp = this.getServletContext().getRequestDispatcher("/AnnonymizeColumns.jsp");
                    disp.forward(request, response);

                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


