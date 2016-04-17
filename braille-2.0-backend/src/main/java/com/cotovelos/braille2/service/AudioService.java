package com.cotovelos.braille2.service;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import com.cotovelos.braille2.document.*;

import com.cotovelos.braille2.business.*;
public class AudioService {

	private  final String BASE = "Código braille 2.0 sobre ";
	private  final String FIM = ", localizado. Deseja ouvir mais?";
	
			
	private WatsonService wSer;
	public AudioService(WatsonService wSer)
	{
		this.wSer = wSer;
	}
	
	
	public InputStream getCategoryAudio(String category)
	{
		Voice v = new Voice("pt-BR_IsabelaVoice","Brazilian Portuguese","Female");
		InputStream in = wSer.getTextToSpeech().synthesize(BASE+category+FIM,v , HttpMediaType.AUDIO_FLAC);
		/*
		 * DEBUG
		 * try {
			Files.copy(in, Paths.get("output123123.wav"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return in;
	}


	public Answer getResponseText(File file) {
		//File audio = new File("src/test/resources/speech_to_text/sample1.wav");
		RecognizeOptions re = new RecognizeOptions();
		re.model("pt-BR_BroadbandModel");
		re.contentType("audio/wav");
		
	    SpeechResults transcript = wSer.getSpeechToText().recognize(file,re);

	    
	    for(Transcript t : transcript.getResults())
	    {
	    	for(SpeechAlternative sp : t.getAlternatives())
	    	{
	    		
	    		for(String st : sp.getTranscript().split(" "))
	    		{
	    		
		    		
			    	if(st.toLowerCase().trim().equals("não") || st.toLowerCase().trim().equals("nao") )
			    	{
			    		System.out.println("NAO");
			    		return Answer.NAO;
			    	}
			    	if(st.toLowerCase().trim().equals("sim") )
			    	{System.out.println("SIM");
			    		return Answer.SIM;
			    	}
	    		}
	    	}
	    	
	    }
	   
		return Answer.NA;
	}


	public InputStream fetchDocument(Document doc) {
		Voice v = new Voice("pt-BR_IsabelaVoice","Brazilian Portuguese","Female");
		InputStream in = wSer.getTextToSpeech().synthesize(doc.getContent(),v , HttpMediaType.AUDIO_FLAC);
		/*
		 * DEBUG
		 * try {
			Files.copy(in, Paths.get("output123123.wav"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return in;
		
	}
	
}
