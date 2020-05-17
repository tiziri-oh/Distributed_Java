package server.myProtocols.myfile;


import java.util.*;

public class Comment {
	private Date date;
	private Date last_update;
	private int idComment;
	private int idUser;
	protected String comment;
	protected String user;
	protected int pidComment;
	private int pidMessage;
	private int score;

	
	public Comment(int idComment, int idUser, String comment,
				   String user, int pidComment, int pidMessage) {
		this.date = null;
		this.last_update = null;
		this.idUser = idUser;
		this.idComment = idComment;
		this.comment = comment;
		this.user = user;
		this.pidComment = pidComment;
		this.pidMessage = pidMessage;
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
	
	public int getIdser() {
		return this.idUser;
	}
	
	public int getPidComment() {
		return this.pidComment;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public int getPidMessage() {
		return this.pidMessage;
	}
	
	public int getIdComment() {
		return this.idComment;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public String toString() {
		return ("Comment: " + date +", "+ idComment +", " + idUser + ", "+ comment + ", " + user + ", "   + ", "+ pidComment + ", "+ pidMessage + ", score : "+ score +  "\n");
	}
	
}
