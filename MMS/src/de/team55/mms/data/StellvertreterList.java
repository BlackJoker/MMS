package de.team55.mms.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Stellvertreter")
public class StellvertreterList {

	private String eMail;
	private ArrayList<String> usr;

	public StellvertreterList() {
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public ArrayList<String> getUsr() {
		return usr;
	}

	public void setUsr(ArrayList<String> usr) {
		this.usr = usr;
	}

	public StellvertreterList(String eMail, ArrayList<String> usr) {
		this.eMail = eMail;
		this.usr = usr;
	}

}
