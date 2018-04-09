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

@WebServlet(name = "UploadServlet",urlPatterns = {"/uploadservlet"})
public class UploadServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String admin_username =(String) session.getAttribute("USERNAME");
        session.setAttribute("USERNAME", admin_username);
        String ctxPath=request.getRealPath("/");
        File dir=new File(ctxPath,"uploaded");
        if(!dir.exists()){
            dir.mkdir();
        }
        Writer out=response.getWriter();
        boolean uploadData=ServletFileUpload.isMultipartContent(request);
        if(uploadData){
            DiskFileItemFactory factory=new DiskFileItemFactory();
            factory.setRepository(new File("c:\\temp"));
            ServletFileUpload upload=new ServletFileUpload(factory);
            try{
                List<FileItem> fileItems=upload.parseRequest(request);
                Iterator<FileItem> i=fileItems.iterator();
                while(i.hasNext()){
                    FileItem fi=i.next();
                    if(!fi.isFormField()){
                        String fieldName=fi.getFieldName();
                        String fileName=fi.getName();
                        String contentType=fi.getContentType();
                        boolean isInMemory=fi.isInMemory();
                        long sizeInByte=fi.getSize();
                        StringTokenizer tok=new
                                StringTokenizer(fileName,"/");
                        String fileToWrite="";
                        while(tok.hasMoreTokens()){
                            fileToWrite=tok.nextToken();
                        }
                        File file=new File(dir,fileToWrite);
                        fi.write(file);
                    }
                }
                System.out.println("<h1>File Uploaded in <br/>"+dir.getAbsolutePath());
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("No File Uploaded");
        }

    }
}