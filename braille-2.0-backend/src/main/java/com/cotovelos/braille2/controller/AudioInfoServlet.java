package com.cotovelos.braille2.controller;

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

import com.cotovelos.braille2.document.Document;
import com.cotovelos.braille2.document.DocumentService;
import com.cotovelos.braille2.service.*;
@WebServlet("/audio/info")
public class AudioInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DocumentService documentService = new DocumentService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	response.setContentType("text/html");
    	
    	//Resource received via ajax
    	String resource = request.getParameter("resource");
    	
    	Document document = documentService.getDocument(resource);
    	
    	
    	WatsonService wSer = new WatsonService();
    	AudioService aSer = new AudioService(wSer);
    	
    	//Content should be sended to watson service
    	
    	
		response.setHeader("Content-disposition","attachment; filename=teste.wav");
        
    	ClassLoader classLoader = getClass().getClassLoader();
        //File file = new File(classLoader.getResource("/audio/teste.wav").getFile());
        
        OutputStream out = response.getOutputStream();
        InputStream in = aSer.fetchDocument(document);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    	
    }
    
}