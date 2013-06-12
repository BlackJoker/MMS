package de.team55.mms.server.function;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserUpdateContainer") 
public class UserUpdateContainer {
	
	private String email;
	private User user;
	
	public UserUpdateContainer() {
		this.email="";
		this.user=new User();
	}
	
	public UserUpdateContainer(User user, String email) {
		this.user=user;
		this.email=email;
		
	}
	
	@XmlElement
	public String getEmail() {
		return email;
	}
	@XmlElement
	public User getUser() {
		return user;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
