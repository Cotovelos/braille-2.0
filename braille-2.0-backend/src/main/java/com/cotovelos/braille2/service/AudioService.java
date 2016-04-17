package com.cotovelos.braille2.service;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.cotovelos.braille2.business.*;
public class AudioService {

	private  final String BASE = "Código braille 2.0 sobre ";
	private  final String FIM = ", localizado. Deseja ouvir mais?";
	
			
	private WatsonService wSer;
	public AudioService(WatsonService wSer)
	{
		this.wSer = wSer;
	}
	
	
	public AudioFile getCategoryAudio(String category)
	{
		Voice v = new Voice("pt-BR_IsabelaVoice","Brazilian Portuguese","Female");
		InputStream in = wSer.getTextToSpeech().synthesize(BASE+category+FIM,v , HttpMediaType.AUDIO_WAV);
		/*
		 * DEBUG
		 * try {
			Files.copy(in, Paths.get("output123123.wav"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return new AudioFile(in);
	}


	public String getResponseText(File file) {
		//File audio = new File("src/test/resources/speech_to_text/sample1.wav");
		RecognizeOptions re = new RecognizeOptions();
		re.model("pt-BR_BroadbandModel");
		re.contentType("audio/wav");
		
	    SpeechResults transcript = wSer.getSpeechToText().recognize(file,re);

	    
		return transcript.toString();
	}
	
}
