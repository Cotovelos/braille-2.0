package com.cotovelos.braille2.controller;

import com.cotovelos.braille2.service.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/audio/category")
public class AudioCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	response.setContentType("text/html");
    	
    	//Category received via ajax from QR CODE
    	String cat = request.getParameter("category");
    	
    	response.setHeader("Content-disposition","attachment; filename=teste.wav");
        
    	ClassLoader classLoader = getClass().getClassLoader();
    	
    	WatsonService wSer = new WatsonService();
    	AudioService aSer = new AudioService(wSer);
    	
    	
        File file = new File(classLoader.getResource("/audio/teste.wav").getFile());
        
        OutputStream out = response.getOutputStream();
        InputStream in = aSer.getCategoryAudio(cat).getIn();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
    
}