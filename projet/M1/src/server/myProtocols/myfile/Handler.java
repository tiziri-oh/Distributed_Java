package server.myProtocols.myfile;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler{

	@Override
	protected URLConnection openConnection(URL urlc) throws IOException {
		// TODO Auto-generated method stub
		try{				
			return new MyFileURLConnection(urlc);
		}catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}

}
