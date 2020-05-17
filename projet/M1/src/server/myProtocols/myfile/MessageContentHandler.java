package server.myProtocols.myfile;

import java.io.*;
import java.net.*;
import java.util.*;

public class MessageContentHandler extends ContentHandler {

	@Override
	public Object getContent(URLConnection urlc) throws IOException {
		URL url = urlc.getURL();
		File file = new File(url.getFile()); 
		System.out.println(url.getFile());
		BufferedReader input = new BufferedReader(new FileReader(file)); 
		
		ArrayList<Object> content = new ArrayList<Object>();
		String delim = "|";
		String str = null;
		while ((str = input.readLine()) != null)
		{
			StringTokenizer tok = new StringTokenizer(str, delim, true);
			ArrayList<String> tab = new ArrayList<String>();
			boolean expectDelim = false;
			while (tok.hasMoreTokens()) {
			    String token = tok.nextToken();
			    if (delim.equals(token)) {
			        if (expectDelim) {
			            expectDelim = false;
			            continue;
			        } else {
			            // le champs vide
			            token = null;
			        }
			    }
			    tab.add(token);
			    expectDelim = true;
			}
			int nbTokens = tab.size();
			if(nbTokens == 6) {
				content.add(new Comment(Integer.parseInt(tab.get(0)), Integer.parseInt(tab.get(1)), tab.get(2), tab.get(3), -1, Integer.parseInt(tab.get(5))));
			}else if(nbTokens == 5) {
				if(tab.get(4) == null) {
					content.add(new Message(Integer.parseInt(tab.get(0)), Integer.parseInt(tab.get(1)), tab.get(2), tab.get(3)));
				}else {
					content.add(new Comment(Integer.parseInt(tab.get(0)), Integer.parseInt(tab.get(1)), tab.get(2), tab.get(3), Integer.parseInt(tab.get(4)), -1));
				}
			}
		}
		input.close();
		return content;
	}

}
