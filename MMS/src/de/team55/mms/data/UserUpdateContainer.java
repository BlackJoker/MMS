package de.team55.mms.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import de.team55.mms.data.*;


@XmlRootElement(name = "UserUpdateContainer") 
public class UserUpdateContainer {
	
	private String email;
	private User user;
	
	public UserUpdateContainer() {
		this.email="";
		this.user=new User();
	}
	
	public UserUpdateContainer(User tmp, String email) {
		this.user=tmp;
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
