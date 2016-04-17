package com.cotovelos.braille2.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.util.CredentialUtils;

public class WatsonService {
	
	private static final SpeechToText s2t = new SpeechToText();
	private static final TextToSpeech t2s = new TextToSpeech();
	public WatsonService()
	{
		 String vcap = System.getProperty("VCAP_SERVICES");
		 if (vcap == null){
		 try {
		 vcap = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource("Braille2_VCAP_Services.json").getFile()));
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }
		 CredentialUtils.setServices(vcap);
		 s2t.setApiKey(CredentialUtils.getAPIKey("speech_to_text"));
		 t2s.setApiKey(CredentialUtils.getAPIKey("text_to_speech"));
		 s2t.setUsernameAndPassword("2a136d08-6dce-4b28-8ca3-0110edc63979", "blBoUPTmG5UR");
		 t2s.setUsernameAndPassword("1a2ff291-a095-40e8-b705-164ded9731d2", "Vhgfda2JmSUw");
		 
	}
	
	public SpeechToText getSpeechToText() {
		return s2t;
	}
	public TextToSpeech getTextToSpeech() {
		return t2s;
	}

	
	
	
}
