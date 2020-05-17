package server.myProtocols.myfile;

import java.util.*;

public class Message {
	private Date date;
	private Date last_update;
	private int  idMessage;
	private int  idUser;
	private String message;
	private String user;
	private int score;
	
	
	public Message(int idMessage, int idUser, String message, String user) {
		this.date = null;
		this.last_update = null;
		this.idMessage = idMessage;
		this.idUser = idUser;
		this.message = message;
		this.user = user;
		this.score = 20;
	}
	public boolean isActive() {
		return (score > 0);
	}
	public int getScore()
	{
		return this.score;
	}
	public Date getDate()
	{
		return this.date;
	}
	public Date getlastUpdate()
	{
		return this.last_update;
	}
	public void decreaseScore()
	{
		if(isActive())
			this.score -= 1;
	}
	public void increaseScore(int amount)
	{
		if(isActive())
			this.score += amount;
	}
	
	public void setDate(Date date)
	{
		this.last_update = date;
		if(this.date == null)
			this.date = date;
		
	}
	
	public int getIdMessage() {
		return this.idMessage;
	}
	
	
	public String getMessage() {
		return this.message;
	}
	
	public int getIdUser() {
		return this.idUser;
	}
	
	
	public String getUser() {
		return this.user;
	}
	
	public String toString() {
		return ("Message: " + date +", "+ idMessage + ", " + idUser + ", "  +  message + ", " + user + ", score : "+ score + "\n");
	}
	
}
