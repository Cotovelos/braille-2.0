package com.cotovelos.braille2.document;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cotovelos.braille2.document.cloudant.MyCloudantClient;

/**
 * 
 * @author Charles.Vatin
 * 
 * Document service which handles document in the database.
 *
 */
public class DocumentService {

	//Should be injected via dependency injection
	private MyCloudantClient cloudantClient = new MyCloudantClient();
	
	/**
	 * Method which save a document in the database.
	 * 
	 * @param document
	 * 	
	 */
	public void saveDocument(Document document) {
		cloudantClient.save(document);
		System.out.println("You have inserted the document");
	}
	
	/**
	 * Method which returns a document in the database given the path.
	 * 
	 * @param path in the bdd (id)
	 * 	
	 */
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
