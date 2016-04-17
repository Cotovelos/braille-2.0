package com.cotovelos.braille2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.cotovelos.braille2.business.Answer;
import com.cotovelos.braille2.service.*;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
@WebServlet("/audio/response")
@MultipartConfig
public class AudioResponseServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;
    private WatsonService wSer = new WatsonService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	
    	final Part filePart = request.getPart("file");
    	
    	System.out.println(getFileName(filePart));
    	
		File gppfile = new File(getClass().getClassLoader().getResource("/audio/temp.wav").getFile());
		OutputStream outputStream = new FileOutputStream(gppfile);
		IOUtils.copy(filePart.getInputStream(), outputStream);
		outputStream.close();
    	
    	
		File target = new File("/audio/temp.wav");
		AudioAttributes audio = new AudioAttributes();
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		Encoder encoder = new Encoder();
		try {
			encoder.encode(gppfile, target, attrs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InputFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        AudioService aSer = new AudioService(wSer);
        Answer ans = aSer.getResponseText(target);
        
        OutputStream out = response.getOutputStream();
        if(Answer.NAO.equals(ans)){
        	out.write("2".getBytes());
        } else if(Answer.SIM.equals(ans)) {
        	out.write("1".getBytes());
        } else {
        	out.write("0".getBytes());
        }
        
        out.flush();
        
    }
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
}