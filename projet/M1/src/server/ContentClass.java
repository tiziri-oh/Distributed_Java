package server;


import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import server.myProtocols.myfile.Comment;
import server.myProtocols.myfile.Content;
import server.myProtocols.myfile.Message;

public class ContentClass implements Content{
	private String    bestMessagesString; 
	ArrayList<Object> content;
	HashMap<Message, ArrayList<Comment>> current;
	
	public ContentClass(String strUrl) throws IOException {
		System.getProperties().put("java.protocol.handler.pkgs", "server.myProtocols");
		URL url = new URL(strUrl);
		content = (ArrayList) url.getContent();
		current = new HashMap<Message, ArrayList<Comment>>();
	}
	
	public void addMessage(Message msg, Date date) {
		msg.setDate(date);
		current.put(msg, new ArrayList<Comment>());
	}
	
	public void addComment (Comment comment, Date date)
	{
		comment.setDate(date);
		Message associatedMessage = null;
		if(comment.getPidMessage() != -1)
		{
			for(Message msg : current.keySet())
			{
				if(comment.getPidMessage() == msg.getIdMessage())
				{
					current.get(msg).add(comment);
					associatedMessage = msg;
					break;
				}
			}
		}else if(comment.getPidComment() != -1){
			for(Message msg : current.keySet())
			{
				for(Comment c : current.get(msg)) 
				{
					if(comment.getPidComment() == c.getIdComment())
					{
						current.get(msg).add(comment);
						associatedMessage = msg;
						break;
					}
				}
				if(associatedMessage != null)
					break;
			}
		}
		if(associatedMessage != null)
		{
			associatedMessage.increaseScore(comment.getScore());
			associatedMessage.setDate(date);
		}
	}
	
	public void read(Date date)
	{
		Object obj = content.get(0);
		if(obj instanceof Message)
		{
			addMessage((Message)obj, date);
		}else {
			addComment((Comment)obj, date);
		}		
		content.remove(obj);
	}
	
	public static boolean isMoreThan30Secs(Date now, Date lastUpdate) {
		long diff = now.getTime() - lastUpdate.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return (diffDays > 1 || diffHours > 1 || diffMinutes > 1 || diffSeconds > 30);
	}
	
	public void update30Sec(Date date)
	{
		int amount = 0;
		for (Message msg: current.keySet()) 
		{
			amount = 0;
			for (Comment cmt : current.get(msg))
			{
				if(isMoreThan30Secs(date, cmt.getlastUpdate())) 
				{
					cmt.decreaseScore();
					cmt.setDate(date);
					amount--;
				}	
			}
			
			msg.increaseScore(amount);
			if(isMoreThan30Secs(date, msg.getlastUpdate())) 
			{
				msg.decreaseScore();
				msg.setDate(date);
			}
		}
	}
	
	
	public void computeBestMessages() {
		if(current.keySet().size() < 3)
		{
			bestMessagesString = null;
			return;
		}
	    List<Message> sortedList = new ArrayList<Message>();
		for(Message msg: current.keySet())
		{
			sortedList.add(msg);
		}
		Collections.sort(
			sortedList, 
			new Comparator<Message>() {
				public int compare(Message o1, Message o2) {
			    	if(o1.getScore() > o2.getScore())
			    		return -1;
			    	else if (o1.getScore() == o2.getScore()) {
			    		return 0;
			    	}else {
			    		return 1;
			    	}
			    }
			}
		);
		Message[] bestMessages = new Message[3];
		bestMessages[0] = sortedList.get(0);
		bestMessages[1] = sortedList.get(1);
		bestMessages[2] = sortedList.get(2);

		System.out.println("Meuilleurs Messages (1) : " + bestMessages[0]);
		System.out.println("Meuilleurs Messages (2) : " + bestMessages[1]);
		System.out.println("Meuilleurs Messages (3) : " + bestMessages[2]);
		System.out.println();
		//Convert XML to String
		try{	

			StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(BestMessages.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(new BestMessages(bestMessages), sw);
			bestMessagesString = sw.toString();
		}catch(Exception e){
			System.out.println("compute best message jaxb exception : " + e);		
		}
	}
	
	@Override
	public String getBestMsg() throws RemoteException, Exception {
		return bestMessagesString;
	}
	
	public void printContent() {
		if(content != null) {
			for(Object obj : content) {
				Message message = null;
				Comment comment = null;
				if(obj != null) {
					if(obj instanceof Message) {
						message = (Message) obj;
						System.out.println(message);
					}else {
						comment = (Comment) obj;
						System.out.println(comment);
					}
				}
			}
		}
	}

	
}
