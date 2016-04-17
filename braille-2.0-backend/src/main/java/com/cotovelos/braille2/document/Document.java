package com.cotovelos.braille2.document;


public class Document {
	private String _id = "example_id";
	private String _rev = null;
	private String content;
	
	public Document(String path, String content) {
	 this._id = path;
	 this.content = content;
	}
	
	public String toString() {
		return "{ path: " + _id + ",\ncontent: " + content + "\n}";
	}

	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}