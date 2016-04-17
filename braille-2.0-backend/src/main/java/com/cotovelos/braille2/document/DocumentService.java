package com.cotovelos.braille2.document;

import com.cotovelos.braille2.document.cloudant.MyCloudantClient;

public class DocumentService {

	private MyCloudantClient cloudantClient = new MyCloudantClient();
	
	public void saveDocument(Document document) {
		cloudantClient.save(document);
		System.out.println("You have inserted the document");
	}
	
	public Document getDocument(String path) {
		Document doc = cloudantClient.find(path);
		System.out.println(doc);

		return doc;
	}
}
