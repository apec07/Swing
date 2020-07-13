

import java.io.*;

public class Account implements Serializable{
	
	private int _id;
	private String user;
	private String password;
	private String note;
	
	public void setId(int _id){
		this._id = _id;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setNote(String note){
		this.note = note;
	}
	
	public int getId(){
		return _id;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getNote(){
		return note;
	}
	
	@Override
	public String toString(){
		String ans = "User = "+user + " Password = "+password + " note = "+note;
		return user;
	}
	


}