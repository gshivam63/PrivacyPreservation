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
@WebServlet(name = "DownloadServlet",urlPatterns = {"/downloadservlet"})
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String schema_name=request.getParameter("param1");
            String fileName="C:\\Users\\shivam\\IdeaProjects\\PrivacyPreservation\\web\\templates\\"+request.getParameter("param1")+".xlsx";
            System.out.println("fileName trans "+fileName);
            //get tables in patient_data and insert in excel sheet
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+schema_name, "root", "complicated");
            Statement stmt = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs_tablename = stmt.executeQuery("SELECT table_name from information_schema.tables where table_schema='"+schema_name+"'");
            int i=0;
            rs_tablename.last();
            int size = rs_tablename.getRow();
            rs_tablename.beforeFirst();
            if(size==1){//if transactional data bcoz if have only one fact table
                String table_name=null;
                while(rs_tablename.next()) {
                    table_name = rs_tablename.getString(1);
                }
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema_name, "root", "complicated");
                PreparedStatement pst = con.prepareStatement(" SELECT COLUMN_NAME from information_schema.columns where table_schema='"+schema_name+"' and table_name='"+table_name+"'");
                ResultSet rs_columnname=pst.executeQuery();
                ArrayList<String> header_values=new ArrayList<String>();
                while(rs_columnname.next()){
                    header_values.add(rs_columnname.getString(1));
                }
                //prevent corrupt code starts
                File file = new File(fileName);
                file.createNewFile();
                FileInputStream excelFile = new FileInputStream(new File(fileName));
                Workbook wb_temp = new XSSFWorkbook();
                Sheet sheet1_temp = wb_temp.createSheet("sheet1");
                sheet1_temp = wb_temp.createSheet("sheet2");
                FileOutputStream fileOut_temp = new FileOutputStream(fileName);
                wb_temp.write(fileOut_temp);
                fileOut_temp.close();
                wb_temp.close();
                //prevent corrupt code ends
                XSSFWorkbook wb = new XSSFWorkbook(excelFile);
                XSSFSheet datatypeSheet = (XSSFSheet) wb.getSheetAt(0);
                Row row = datatypeSheet.createRow(0);
                //first column should be patient_name
                row = datatypeSheet.getRow(0);
                int columnCount = 0;
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue("patient_name");
                for (Object field:header_values) {
                    cell = row.createCell(columnCount);
                    if (field instanceof String) {
                        String temp=(String)field;
                        if(!temp.toLowerCase().contains("_id")) {
                            cell.setCellValue((String) field);
                            columnCount++;
                        }
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                        columnCount++;
                    }
                }
                //get patient names from health_dataset.patient and add them as dropdown under patient_name of transactional_data schema
                Connection con_patient_name = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_dataset" , "root", "complicated");
                PreparedStatement pst_patient_name = con_patient_name.prepareStatement(" SELECT patient_name from patient");
                ResultSet rs_patient_name=pst_patient_name.executeQuery();
                ArrayList<String> patient_names=new ArrayList<String>();
                int tuples_count=0;
                while(rs_patient_name.next()){
                    patient_names.add(rs_patient_name.getString("patient_name"));
                    tuples_count++;
                }
                System.out.println(patient_names+" tuples count "+tuples_count+" fileName "+fileName);
                //add validation list in patient_name
                DataValidation dataValidation = null;
                DataValidationConstraint constraint = null;
                DataValidationHelper validationHelper = null;
                validationHelper = new XSSFDataValidationHelper(datatypeSheet);
                CellRangeAddressList addressList = new CellRangeAddressList(1, tuples_count, 0, 0);
                String builder = patient_names.toString().replace("[", "").replace("]", "");
                constraint = validationHelper.createExplicitListConstraint(new String[]{builder});
                dataValidation = validationHelper.createValidation(constraint, addressList);
                dataValidation.setSuppressDropDownArrow(true);
                datatypeSheet.addValidationData(dataValidation);
                //validation code ends here
                FileOutputStream fileOut = new FileOutputStream(fileName);
                wb.write(fileOut);
                fileOut.close();
                con.close();
            }
            else {//this is static data
                ArrayList<String> static_header=new ArrayList<String>();
                static_header.add("patient_name");
                static_header.add("gender");
                static_header.add("street_name");
                static_header.add("city");
                static_header.add("state");
                static_header.add("country");
                static_header.add("zipcode");
                //prevent corrupt code starts
                File file = new File(fileName);
                file.createNewFile();
                FileInputStream excelFile = new FileInputStream(new File(fileName));
                Workbook wb_temp = new XSSFWorkbook();
                Sheet sheet1_tempp = wb_temp.createSheet();
                FileOutputStream fileOut_temp = new FileOutputStream(fileName);
                wb_temp.write(fileOut_temp);
                fileOut_temp.close();
                wb_temp.close();
                //prevent corrupt code ends
                XSSFWorkbook wb = new XSSFWorkbook(excelFile);
                XSSFSheet sheet1 = (XSSFSheet) wb.getSheetAt(0);
                for (int sheet_i = sheet1.getLastRowNum()-1; sheet_i >= 0; sheet_i--) {
                    sheet1.removeRow(sheet1.getRow(sheet_i));
                }
                Row r = sheet1.createRow(0);
                if (r == null)
                    r = sheet1.createRow(0);
                for (Object field:static_header) {
                    Cell cell = r.createCell(i++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
                while (rs_tablename.next()) {
                    String table_name = rs_tablename.getString(1);
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema_name, "root", "complicated");
                    PreparedStatement pst = con.prepareStatement("SELECT DISTINCT " + table_name + "_name from " + table_name);
                    ArrayList<String> arr = new ArrayList<String>();
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        String temp = rs.getString(1);
                        arr.add(temp);
                    }
                    String builder = arr.toString().replace("[", "").replace("]", "");
                    DataValidation dataValidation = null;
                    DataValidationConstraint constraint = null;
                    DataValidationHelper validationHelper = null;
                    Cell c = r.getCell(i);
                    c = r.createCell(i, Cell.CELL_TYPE_STRING);
                    c.setCellValue(table_name+"_name");
                    validationHelper = new XSSFDataValidationHelper(sheet1);
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 10, i, i);
                    constraint = validationHelper.createExplicitListConstraint(new String[]{builder});
                    dataValidation = validationHelper.createValidation(constraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    sheet1.addValidationData(dataValidation);
                    i++;
                }
                FileOutputStream fileOut = new FileOutputStream(fileName);
                wb.write(fileOut);
                fileOut.close();
            }
            con1.close();
            //get template from db code ends here
            fileName=request.getParameter("param1")+".xlsx";
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            //String gurufile = "test.txt";
            String gurupath = "C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/templates/";
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
            e.printStackTrace();
        }
    }
}
