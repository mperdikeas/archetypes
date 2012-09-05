package model;

public class User // implements java.io.Serializable // toggle that on and off to observe an interesting difference in behavior
{
	
	private long userID;
	
	private String username;
	
	private String password ;
	
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
