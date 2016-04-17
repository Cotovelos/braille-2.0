package com.cotovelos.braille2.document;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cotovelos.braille2.document.cloudant.MyCloudantClient;

public class DocumentService {

	private MyCloudantClient cloudantClient = new MyCloudantClient();
	
	public void saveDocument(Document document) {
		cloudantClient.save(document);
		System.out.println("You have inserted the document");
	}
	
	public Document getDocument(String path) {
		try {
		
			Document doc = cloudantClient.find(path);
			System.out.println(doc);
			return doc;
			
		} catch (NoDocumentException nde) {
			return null;
		}
	}
}
