package com.cotovelos.braille2.document.cloudant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cotovelos.braille2.document.Document;
// Cliente Cloudant

public class MyCloudantClient {
	
	private CloudantClient client;
	private Database db;
	
	public MyCloudantClient() {
		//Create a new CloudantClient instance for account endpoint example.cloudant.com
		System.out.println("Connect to Cloudant client.......");
		try {
			this.client = ClientBuilder.url(new URL("https://0a7b8d1b-49ef-406c-884e-e564c0c2431b-bluemix:e10b2d450a4b0067abf613164d44c7276d7b27d0e165804bc0b64f45f57fe40e@0a7b8d1b-49ef-406c-884e-e564c0c2431b-bluemix.cloudant.com"))
			                                  .username("0a7b8d1b-49ef-406c-884e-e564c0c2431b-bluemix")
			                                  .password("e10b2d450a4b0067abf613164d44c7276d7b27d0e165804bc0b64f45f57fe40e")
			                                  .build();
		} catch (MalformedURLException e) {
			System.out.println("Impossible to connect to the CloudAnt");
		}
		
		//Show the server version
		System.out.println("Server Version: " + client.serverVersion());
		
		//Get a List of all the databases this Cloudant account
		List<String> databases = client.getAllDbs();
		//System.out.println("All my databases : ");
		for ( String db : databases ) {
		 System.out.println(db);
		}
		
		//Get a Database instance to interact with, but don't create it if it doesn't already exist
		this.db = client.database("braille2_db", true);
		
//		save(new Document("/cardapio", "Menu de carnes:\n"+
//				"Opção 1, Contra filé. Acompanha arroz, feijão, batatas fritas e farofa. 15 reais.\n" +
//				"Opção 2, Picanha. Acompanha arroz, feijão, batatas fritas e farofa. 20 reais.\n" +
//				"Opção 3, Frango. Acompanha arroz, feijão, batatas fritas e farofa. 14 reais."));
//		
//		save(new Document("/onibus", "Linhas de ônibus que param neste ponto.\n" + 
//				"Ônibus 004  E.M.T.U. - São Bernado do Campo  Parque alvarenga.\n" + 
//				"Ônibus 431  E.M.T.U. - Jardim Las Palmas.\n" +
//				"Ônibus 152  E.M.T.U. - São Bernado do Campo   ÁREA VERDE."));
		
	}
	
	public void save(Document doc){
		this.db.save(doc);
	}
	
	public Document find(String path){
		return this.db.find(Document.class, path);
	}

}