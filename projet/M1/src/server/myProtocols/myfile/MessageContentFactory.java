package server.myProtocols.myfile;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;

public class MessageContentFactory implements ContentHandlerFactory{

	public MessageContentFactory() { }

	@Override
	public ContentHandler createContentHandler(String arg0) {		
		return new MessageContentHandler();
	}
}
