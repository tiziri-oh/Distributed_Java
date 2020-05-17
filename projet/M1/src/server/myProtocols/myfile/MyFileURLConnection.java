package server.myProtocols.myfile;

import java.net.URLConnection;
import java.io.*;
import java.net.*;

public class MyFileURLConnection extends URLConnection {

	public MyFileURLConnection(URL url) throws MalformedURLException{
		super(url);
		setContentHandlerFactory(new MessageContentFactory());
	}

	public void connect(){

	}

	public InputStream getInputStream(){
		return null;
	}

	public String getHeaderField(String name){
		if(name.equals("content-type")){
			return "myfile";
		} 
		return null;
	}
}
