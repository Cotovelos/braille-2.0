package com.cotovelos.braille2.business;

import java.io.InputStream;

public class AudioFile {

	private InputStream in;
	
	public AudioFile(InputStream in)
	{
		this.in = in;
	}

	public InputStream getIn() {
		return in;
	}
	
	
	
}
