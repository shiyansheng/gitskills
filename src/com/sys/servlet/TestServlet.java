package com.sys.servlet;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


public class TestServlet extends HttpServlet {
	
	public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
        for(Cookie c :cookies ){
            System.out.println(c.getName()+"--->"+c.getValue());
        }
		
		
		response.sendRedirect("http://localhost:7001/SysPro/index.jsp");
	}
	
	public  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	
	public static String getWindowsMACAddress() {     
        String mac = null;     
        BufferedReader bufferedReader = null;     
        Process process = null;     
        try {     
              /**  
               * windows下的命令，显示信息中包含有mac地址信息    
               */  
            process = Runtime.getRuntime().exec("ipconfig /all");   
            bufferedReader = new BufferedReader(new InputStreamReader(process     
                    .getInputStream()));     
            String line = null;     
            int index = -1;     
            while ((line = bufferedReader.readLine()) != null) {     
                   /**  
                    *  寻找标示字符串[physical address]   
                    */  
                index = line.toLowerCase().indexOf("physical address");    
                if (index != -1) {   
                    index = line.indexOf(":");   
                    if (index != -1) {   
                           /**  
                            *   取出mac地址并去除2边空格  
                            */  
                       mac = line.substring(index + 1).trim();    
                   }   
                    break;     
                }   
            }   
        } catch (IOException e) {     
            e.printStackTrace();     
        }finally {     
            try {     
                if (bufferedReader != null) {     
                    bufferedReader.close();     
                  }     
            }catch (IOException e1) {     
                e1.printStackTrace();     
              }     
            bufferedReader = null;     
            process = null;     
        }     
     
        return mac;     
    }     


	
}
