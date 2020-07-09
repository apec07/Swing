

import java.io.*;

public class Account implements Serializable{
	
	private String user;
	private String password;
	
	public void setUser(String user){
		this.user = user;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getUser(){
		return user;
	}
	public String getPassword(){
		return password;
	}
	@Override
	public String toString(){
		String ans = "User = "+user + " Password = "+password;
		return user;
	}
	


}