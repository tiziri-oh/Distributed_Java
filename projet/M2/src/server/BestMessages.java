package server;

import javax.xml.bind.annotation.*;

import server.myProtocols.myfile.Message;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BestMessages {

	private int userId1;
	private int messageId1;
	private int userId2;
	private int messageId2;
	private int userId3;
	private int messageId3;
    
	public BestMessages(Message[] msg)
	{
		this.userId1    = (msg[0].isActive()) ? msg[0].getIdUser()    : 0;
		this.messageId1 = (msg[0].isActive()) ? msg[0].getIdMessage() : 0;
		this.userId2    = (msg[1].isActive()) ? msg[1].getIdUser()    : 0;
		this.messageId2 = (msg[1].isActive()) ? msg[1].getIdMessage() : 0;
		this.userId3    = (msg[2].isActive()) ? msg[2].getIdUser()    : 0;
		this.messageId3 = (msg[2].isActive()) ? msg[2].getIdMessage() : 0;
	}
	public BestMessages()
	{
		this.userId1    = 0;
		this.messageId1 = 0;
		this.userId2    = 0;
		this.messageId2 = 0;
		this.userId3    = 0;
		this.messageId3 = 0;
	}
	
	public String toString(){
		return "" + messageId1 + "|" + userId1 + "|" + messageId2 + "|" + userId2 + "|" + messageId3 + "|" + userId3;
	}
}
