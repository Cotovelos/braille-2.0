package com.cotovelos.braille2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cotovelos.braille2.service.*;
@WebServlet("/audio/response")
public class AudioResponseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	response.setContentType("text/html");
    	
    	response.setHeader("Content-disposition","attachment; filename=teste.wav");
        
    	ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("/audio/rec_7s.wav").getFile());
        
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
        
        WatsonService wSer = new WatsonService();
        AudioService aSer = new AudioService(wSer);
        aSer.getResponseText(file);
        
        
    }
    
}