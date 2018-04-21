package com.privacy.preservation;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "UploadAnnonymizedServlet",urlPatterns = {"/uploadannonymizedservlet"})
public class UploadAnnonymizedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String admin_username =(String) session.getAttribute("USERNAME");
            session.setAttribute("USERNAME", admin_username);
            String fileName=(String)session.getAttribute("fileName");
            System.out.println("upload servlet "+fileName);
            session.setAttribute("fileName", fileName);
            String upload_message= (String) session.getAttribute("upload_message");
            session.setAttribute("upload_message",upload_message);
            if(fileName.equals("patient_data")){
                System.out.println("control inside "+fileName);
                FileInputStream file = new FileInputStream(new File("C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/uploaded/"+fileName+".xlsx"));
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String database_name1="health_dataset";
                String url = "jdbc:mysql://localhost:3306/"+database_name1;
                Connection conn1 = DriverManager.getConnection(url, "root", "complicated");
                DatabaseMetaData dbmd = conn1.getMetaData();

                String database_name2="patient_data";
                String url2 = "jdbc:mysql://localhost:3306/"+database_name2;
                Connection conn2 = DriverManager.getConnection(url2, "root", "complicated");
                DatabaseMetaData dbmd2 = conn2.getMetaData();

                String database_name3="transactional_data";
                String url3 = "jdbc:mysql://localhost:3306/"+database_name3;
                Connection conn3 = DriverManager.getConnection(url3, "root", "complicated");
                DatabaseMetaData dbmd3 = conn3.getMetaData();


//            PreparedStatement pst = conn.prepareStatement("SELECT username,password FROM login_details where username=?");
//            ResultSet rs = pst.executeQuery();
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            HSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                System.out.println("2");

                for(int i=1; i<=sheet.getLastRowNum(); i++){  //points to the starting of excel i.e excel first row

                    row = (Row) sheet.getRow(i);  //sheet number

                    String patient_name;
                    if( row.getCell(0)==null) { patient_name = "0"; }
                    else patient_name= row.getCell(0).toString();

                    String gender;
                    if( row.getCell(1)==null) { gender = "null";}  //suppose excel cell is empty then its set to 0 the variable
                    else gender = row.getCell(1).toString();   //else copies cell data to name variable

                    String street_name;
                    if( row.getCell(2)==null) { street_name = "null";   }
                    else  street_name = row.getCell(2).toString();

                    String city;
                    if( row.getCell(3)==null) { city = "null";   }
                    else  city = row.getCell(3).toString();

                    String state;
                    if( row.getCell(4)==null) { state = "null";   }
                    else  state = row.getCell(4).toString();

                    String country;
                    if( row.getCell(5)==null) { country = "null";   }
                    else  country = row.getCell(5).toString();

                    String zip;
                    if( row.getCell(6)==null) { zip = "null";   }
                    else  zip = row.getCell(6).toString();

                    String bank;
                    if( row.getCell(7)==null) { bank = "null";   }
                    else  bank = row.getCell(7).toString();

                    String disease;
                    if( row.getCell(8)==null) { disease = "null";   }
                    else  disease = row.getCell(8).toString();

                    String doctor;
                    if( row.getCell(9)==null) { doctor = "null";   }
                    else  doctor = row.getCell(9).toString();

                    String hospital;
                    if( row.getCell(10)==null) { hospital = "null";   }
                    else  hospital = row.getCell(10).toString();

                    String insurance;
                    if( row.getCell(11)==null) { insurance = "null";   }
                    else  insurance = row.getCell(11).toString();

                    String relationship;
                    if( row.getCell(11)==null) { relationship = "null";   }
                    else  relationship = row.getCell(11).toString();

                    System.out.println("3");

                    String sql="INSERT INTO patient(patient_name,gender,street_name,city,state,country,zipcode) " +
                            "VALUES('"+patient_name+"','"+gender+"','"+street_name+"','"+city+"','"+state+"','"+country+"','"+zip+"')";
                    PreparedStatement ps = conn1.prepareStatement(sql);
                    ps.executeUpdate();

                    String sql1="SELECT max(patient_master_id) from patient";
                    PreparedStatement ps1=conn1.prepareStatement(sql1);
                    ResultSet pat_id=ps1.executeQuery();
                    String patient_ref="";
                    if(pat_id.next()){
                        patient_ref=pat_id.getString(1);
                    }
                    System.out.println(patient_ref);

                    String sql2="SELECT bank_master_id FROM bank where bank_name='"+bank+"'";
                    PreparedStatement ps2=conn2.prepareStatement(sql2);
                    ResultSet bank_id=ps2.executeQuery();
                    String bank_ref="";
                    if(bank_id.next()){
                        bank_ref=bank_id.getString(1);
                    }
                    System.out.println(bank_ref);

                    String sql3="SELECT disease_master_id FROM disease where disease_name='"+disease+"'";
                    PreparedStatement ps3=conn2.prepareStatement(sql3);
                    ResultSet disease_id=ps3.executeQuery();
                    String disease_ref="";
                    if(disease_id.next()){
                        disease_ref=disease_id.getString(1);
                    }
                    System.out.println(disease_ref);

                    String sql4="SELECT doctor_master_id FROM doctor where doctor_name='"+doctor+"'";
                    PreparedStatement ps4=conn2.prepareStatement(sql4);
                    ResultSet doctor_id=ps4.executeQuery();
                    String doctor_ref="";
                    if(doctor_id.next()){
                        doctor_ref=doctor_id.getString(1);
                    }
                    System.out.println(doctor_ref);

                    String sql5="SELECT hospital_master_id FROM hospital where hospital_name='"+hospital+"'";
                    PreparedStatement ps5=conn2.prepareStatement(sql5);
                    ResultSet hospital_id=ps5.executeQuery();
                    String hospital_ref="";
                    if(hospital_id.next()){
                        hospital_ref=hospital_id.getString(1);
                    }
                    System.out.println(hospital_ref);


                    String sql6="SELECT insurance_master_id FROM insurance_company where insurance_company_name='"+insurance+"'";
                    PreparedStatement ps6=conn2.prepareStatement(sql6);
                    ResultSet insurance_company_id=ps6.executeQuery();
                    String insurance_company_ref="";
                    if(insurance_company_id.next()){
                        insurance_company_ref=insurance_company_id.getString(1);
                    }
                    System.out.println(insurance_company_ref);


                    String sql7="SELECT relationship_master_id FROM relationship where relationship_name='"+relationship+"'";
                    PreparedStatement ps7=conn2.prepareStatement(sql7);
                    ResultSet relationship_id=ps7.executeQuery();
                    String relationship_ref="";
                    if(relationship_id.next()){
                        relationship_ref=relationship_id.getString(1);
                    }
                    System.out.println(relationship_ref);

                    String sql8="INSERT INTO records(patient_ref_id,bank_ref_id,disease_ref_id,doctor_ref_id,hospital_ref_id,insurance_ref_id,relationship_ref_id) " +

                            "VALUES('"+patient_ref+"','"+bank_ref+"','"+disease_ref+"','"+doctor_ref+"','"+hospital_ref+"','"+insurance_company_ref+"','"+relationship_ref+"')";
                    PreparedStatement ps8 = conn3.prepareStatement(sql8);
                    ps8.executeUpdate();

                }

                System.out.println("4");
                File file_delete = new File("C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/uploaded/"+fileName+".xlsx");
                if(file_delete.delete()){
                    System.out.println("File deleted successfully");
                }
                else{
                    System.out.println("Failed to delete the file");
                }
                request.setAttribute("upload_msg","Annonymized file has been uploaded");
                RequestDispatcher disp = null;
                disp = this.getServletContext().getRequestDispatcher("/Review.jsp");
                disp.forward(request, response);
            }
            else if(fileName.equals("transactional_data")){
                FileInputStream file = new FileInputStream(new File("C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/uploaded/"+fileName+".xlsx"));
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String database_name1="health_dataset";
                String url = "jdbc:mysql://localhost:3306/"+database_name1;
                Connection conn1 = DriverManager.getConnection(url, "root", "complicated");
                DatabaseMetaData dbmd = conn1.getMetaData();

                String database_name2="patient_data";
                String url2 = "jdbc:mysql://localhost:3306/"+database_name2;
                Connection conn2 = DriverManager.getConnection(url2, "root", "complicated");
                DatabaseMetaData dbmd2 = conn2.getMetaData();

                String database_name3="transactional_data";
                String url3 = "jdbc:mysql://localhost:3306/"+database_name3;
                Connection conn3 = DriverManager.getConnection(url3, "root", "complicated");
                DatabaseMetaData dbmd3 = conn3.getMetaData();
                Row row;
                for(int i=1; i<=sheet.getLastRowNum(); i++){  //points to the starting of excel i.e excel first row
                    row = (Row) sheet.getRow(i);  //sheet number
                    String patient_name;
                    if( row.getCell(0)==null) { patient_name = "0"; }
                    else patient_name= row.getCell(0).toString();
                    String age;
                    if( row.getCell(1)==null) { age = "null";}  //suppose excel cell is empty then its set to 0 the variable
                    else age = row.getCell(1).toString();   //else copies cell data to name variable

                    String length_of_stay;
                    if( row.getCell(2)==null) { length_of_stay = "null";   }
                    else  length_of_stay = row.getCell(2).toString();

                    String cost;
                    if( row.getCell(3)==null) { cost = "null";   }
                    else  cost = row.getCell(3).toString();

                    String admission_date;
                    if( row.getCell(4)==null) { admission_date = "null";   }
                    else  admission_date = row.getCell(4).toString();


                    String admission_day_of_week;
                    if( row.getCell(5)==null) { admission_day_of_week = "null";   }
                    else  admission_day_of_week = row.getCell(5).toString();

                    String admission_month;
                    if( row.getCell(6)==null) { admission_month = "null";   }
                    else  admission_month = row.getCell(6).toString();

                    String admission_year;
                    if( row.getCell(7)==null) { admission_year = "null";   }
                    else  admission_year = row.getCell(7).toString();

                    String discharge_date;
                    if( row.getCell(8)==null) { discharge_date = "null";   }
                    else  discharge_date = row.getCell(8).toString();

                    String discharge_day_of_week;
                    if( row.getCell(9)==null) { discharge_day_of_week = "null";   }
                    else  discharge_day_of_week = row.getCell(9).toString();

                    String discharge_month;
                    if( row.getCell(10)==null) { discharge_month = "null";   }
                    else  discharge_month = row.getCell(10).toString();

                    String discharge_year;
                    if( row.getCell(11)==null) { discharge_year = "null";   }
                    else  discharge_year = row.getCell(11).toString();

                    System.out.println(admission_date);
                    System.out.println(discharge_date);

                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy"); // your template here
                    java.util.Date admissiondateStr = formatter.parse(admission_date);
                    java.sql.Date admissiondateDB = new java.sql.Date(admissiondateStr.getTime());

                    formatter = new SimpleDateFormat("dd-MMM-yyyy"); // your template here
                    java.util.Date dischargedateStr = formatter.parse(discharge_date);
                    java.sql.Date dischargedateDB = new java.sql.Date(dischargedateStr.getTime());

                    System.out.println("3");

                    String sql11="SELECT patient_master_id from patient where patient_name='"+patient_name+"'";
                    PreparedStatement ps11=conn1.prepareStatement(sql11);
                    ResultSet pat_id=ps11.executeQuery();
                    String patient_ref="";
                    if(pat_id.next()){
                        patient_ref=pat_id.getString(1);
                    }
                    System.out.println(patient_ref);


                    String sql12="UPDATE records set age='"+age+"',"+"length_of_stay='"+length_of_stay+"',"+"cost='"+cost+"',"
                            +"admission_date='"+admissiondateDB+"',"+"admission_day_of_week='"+admission_day_of_week+"',"+"admission_month='"+admission_month+"',"+"admission_year='"+admission_year+"',"
                            +"discharge_date='"+dischargedateDB+"',"+"discharge_day_of_week='"+discharge_day_of_week+"',"+"discharge_month='"+discharge_month+"',"+"discharge_year='"+discharge_year+"'" +
                            "where patient_ref_id='"+patient_ref+"'";

                    PreparedStatement ps12 = conn3.prepareStatement(sql12);
                    ps12.executeUpdate();
                }
                request.setAttribute("upload_msg","Annonymized file has been uploaded");
                RequestDispatcher disp = null;
                disp = this.getServletContext().getRequestDispatcher("/Review.jsp");
                disp.forward(request, response);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get clicked");

    }

}
