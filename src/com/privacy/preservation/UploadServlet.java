package com.privacy.preservation;
import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "UploadServlet",urlPatterns = {"/uploadservlet"})
public class UploadServlet extends HttpServlet {

        protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        String param2=request.getParameter("Key");
        System.out.println("param 2 "+param2);
        HttpSession session = request.getSession(false);
        String admin_username =(String) session.getAttribute("USERNAME");
        session.setAttribute("USERNAME", admin_username);
        ArrayList<String> column_list= (ArrayList<String>) session.getAttribute("column_arrayList");
        System.out.println("column list "+column_list);
        String ctxPath="C:\\Users\\shivam\\IdeaProjects\\PrivacyPreservation\\web\\";
        File dir=new File(ctxPath,"uploaded");
        if(!dir.exists()){
            dir.mkdir();
        }
        Writer out=response.getWriter();
        boolean uploadData=ServletFileUpload.isMultipartContent(request);
        String FILE_NAME="";
        if(uploadData) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File("c:\\temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> fileItems = upload.parseRequest(request);
                Iterator<FileItem> i = fileItems.iterator();
                while (i.hasNext()) {
                    FileItem fi = i.next();
                    if (!fi.isFormField()) {
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInByte = fi.getSize();
                        FILE_NAME=fi.getName();
                        StringTokenizer tok = new
                                StringTokenizer(fileName, "/");
                        String fileToWrite = "";
                        while (tok.hasMoreTokens()) {
                            fileToWrite = tok.nextToken();
                        }
                        File file = new File(dir, fileToWrite);
                        fi.write(file);
                    }
                }
                System.out.println("<h1>File Uploaded in <br/>" + dir.getAbsolutePath());
                FILE_NAME="C:\\Users\\shivam\\IdeaProjects\\PrivacyPreservation\\web\\uploaded\\"+FILE_NAME;
                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                //play
                XSSFRow  row= (XSSFRow) datatypeSheet.getRow(1);
                int mincol=row.getFirstCellNum();
                int maxcol=row.getLastCellNum();
                System.out.println(mincol+" "+maxcol);
                XSSFRow temp= (XSSFRow) datatypeSheet.getRow(2);
                for(int j=mincol;j<maxcol;j++){
                    Cell currentCell=row.getCell(j);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.println(currentCell.getStringCellValue());
                        temp.getCell(j).setCellValue(currentCell.getStringCellValue());
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.println(currentCell.getNumericCellValue());

                        temp.getCell(j).setCellValue(currentCell.getNumericCellValue());
                    }
                }
                //stop
                Iterator<Row> iterator = datatypeSheet.iterator();
                ArrayList<Object> column_values=new ArrayList<Object>();
                while (iterator.hasNext()) {

                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();

                    while (cellIterator.hasNext()) {

                        Cell currentCell = cellIterator.next();
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            column_values.add(currentCell.getStringCellValue());
                        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                            column_values.add(currentCell.getNumericCellValue());
                        }
                    }
                    System.out.println(column_values);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}