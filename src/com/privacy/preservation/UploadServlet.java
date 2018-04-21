package com.privacy.preservation;
import java.io.*;
import java.util.*;
import java.lang.Math.*;

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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;

@WebServlet(name = "UploadServlet",urlPatterns = {"/uploadservlet"})
public class UploadServlet extends HttpServlet {
    static int col_num=0;
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String admin_username =(String) session.getAttribute("USERNAME");
        session.setAttribute("USERNAME", admin_username);
        ArrayList<String> column_encryption= (ArrayList<String>) session.getAttribute("column_arrayList");
        session.setAttribute("column_arrayList", column_encryption);
        String fileName=(String)session.getAttribute("fileName");
        session.setAttribute("fileName", fileName);
        System.out.println("file is "+fileName);
        HashMap<String,String> col_option= new HashMap<String,String>();
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
                        String fileName1 = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInByte = fi.getSize();
                        FILE_NAME = fi.getName();
                        StringTokenizer tok = new
                                StringTokenizer(fileName1, "/");
                        String fileToWrite = "";
                        while (tok.hasMoreTokens()) {
                            fileToWrite = tok.nextToken();
                        }
                        File file = new File(dir, fileToWrite);
                        fi.write(file);
                    } else {
                        String name = fi.getFieldName();
                        String value = fi.getString();
                        col_option.put(name, value);
                    }
                }
                System.out.println(col_option);
                System.out.println("<h1>File Uploaded in <br/>" + dir.getAbsolutePath());
                FILE_NAME = "C:\\Users\\shivam\\IdeaProjects\\PrivacyPreservation\\web\\uploaded\\" + FILE_NAME;
                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                workbook.createSheet("map sheet");
                workbook.setSheetOrder("map sheet",1);
                Sheet datatypeSheet_map = workbook.getSheetAt(1);
                Iterator<Row> anonRows = datatypeSheet.iterator();
                Row upper = anonRows.next();
                int row_num=0;
                Row row_map_header = datatypeSheet_map.createRow(0);
                while (anonRows.hasNext()) {
                    Iterator<Cell> columns = upper.iterator();
                    Row currentRow = anonRows.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    row_num++;
                    Row row_map = datatypeSheet_map.createRow(row_num);
                    col_num=0;
                    while (cellIterator.hasNext()) {
                        Cell col = columns.next();
                        Cell currentCell = cellIterator.next();
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            if(currentCell.getStringCellValue()!=null) {
                                currentCell.setCellValue(anonymizeString(currentCell.getStringCellValue(), col_option.get(col.getStringCellValue()),row_map,row_map_header,col.getStringCellValue()));
                            }
                        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                currentCell.setCellValue(anonymizeInt((int) currentCell.getNumericCellValue(),col_option.get(col.getStringCellValue()),row_map,row_map_header,col.getStringCellValue()));
                        }
                    }
                }
                //stop
                Iterator<Row> iterator = datatypeSheet.iterator();
                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    ArrayList<Object> column_values = new ArrayList<Object>();
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
                FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
                //function to create map file
                createmaped_file(fileName,col_option);
                request.setAttribute("column_arrayList",column_encryption);
                request.setAttribute("upload_message","File is uploaded");
                RequestDispatcher disp = this.getServletContext().getRequestDispatcher("/AnnonymizeColumns.jsp");
                disp.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void createmaped_file(String fileName,HashMap<String,String> col_option){
            System.out.println("inside map file function"+fileName);
    }
    public static String anonymizeString(String s, String anon,Row row_map,Row row_map_header,String header)
    {
        if(anon==null)
        {
            anon="No_Encryption";
        }
        String newString = "";
        switch(anon){
            case ("Random_Anonymization"):
                for(int i=0;i<s.length();i++)
                {
                    Random r = new Random();
                    int Low = 0;
                    int High = 25;
                    int Result = r.nextInt(High-Low) + Low;
                    if((int)(s.toLowerCase().charAt(i)+Result)>(int)'z')
                    {
                        newString = newString + (char)((s.toLowerCase().charAt(i)+Result)-(int)'z' + (int)'a'- 1);
                    }
                    else
                    {
                        newString = newString + (char)(s.toLowerCase().charAt(i) + Result);
                    }
                }
                break;
            case("Generalisation"):
                for(int i=0;i<s.length();i++)
                {
                    if((int)(s.toLowerCase().charAt(i))<(int)'g')
                    {
                        newString = newString + (char)((int)'a');
                    }
                    else if((int)(s.toLowerCase().charAt(i))<(int)'m')
                    {
                        newString = newString + (char)((int)'i');
                    }
                    else if((int)(s.toLowerCase().charAt(i))<(int)'s')
                    {
                        newString = newString + (char)((int)'r');
                    }
                    else if((int)(s.toLowerCase().charAt(i))<(int)'w')
                    {
                        newString = newString + (char)((int)'s');
                    }
                    else
                    {
                        newString = newString + (char)((int)'y');
                    }

                }
                break;
            case ("No_Encryption"):
                newString = s;
                break;
            default:
                newString = s;
        }
        if(!anon.equals("No_Encryption")){
            Cell cell1 = row_map.createCell(col_num);
            Cell cell2 = row_map.createCell(col_num+1);
            cell1.setCellValue(s);
            cell2.setCellValue(newString);
            Cell cell3 = row_map_header.createCell(col_num);
            Cell cell4 = row_map_header.createCell(col_num+1);
            cell3.setCellValue(header+"_old");
            cell4.setCellValue(header+"_new");
            col_num=col_num+2;
        }
        return newString;
    }
    public static int anonymizeInt(int n, String anon,Row row_map,Row row_map_header,String header)
    {
        if(anon==null)
        {
            anon="No_Encryption";
        }
        int newInt=0;
        switch(anon){
            case ("Random_Anonymization"):
                while(n>0)
                {
                    Random r = new Random();
                    int Low = 0;
                    int High = 9;
                    int Result = r.nextInt(High-Low) + Low;
                    newInt =newInt*10 + Result;
                    n = n/10;
                }
                break;
            case ("No_Encryption"):
                newInt = n;
                break;
            case("Generalisation"):
                int temp = n;
                int size=0;
                while (temp>0)
                {
                    size++;
                    temp=temp/10;
                }
                int parameter = fun(size);
                parameter =
                        newInt = (n/parameter + (int)Math.round((double)((n%parameter)/(double)parameter)))*parameter;
                break;
            default:
                newInt = n;
        }
        if(!anon.equals("No_Encryption")){
            Cell cell1 = row_map.createCell(col_num);
            Cell cell2 = row_map.createCell(col_num+1);
            cell1.setCellValue(n);
            cell2.setCellValue(newInt);
            Cell cell3 = row_map_header.createCell(col_num);
            Cell cell4 = row_map_header.createCell(col_num+1);
            cell3.setCellValue(header+"_old");
            cell4.setCellValue(header+"_new");
            col_num=col_num+2;
        }
        return newInt;
    }
    public static int fun(int size)
    {
        int parameter = pow(size);
        parameter = parameter / pow((size-1)/2+1);
        return parameter;
    }
    public static int pow(int size)
    {
        int parameter = 1;
        for(int i=0;i<(size-1);i++)
        {
            parameter = parameter * 10;
        }
        return parameter;
    }
}