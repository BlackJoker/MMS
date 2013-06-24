package de.team55.mms.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "User")
@XmlType(propOrder = { "eMail", "titel", "vorname", "nachname", "password",
		"manageUsers", "acceptModule", "createModule", "readModule" })
public class User {
	private String Vorname;
	private String Nachname;
	private String eMail;
	private String Password;
	private String Titel;
	private boolean manageUsers;
	private boolean readModule;
	private boolean createModule;
	private boolean acceptModule;

	public User() {
		this.Vorname = "null";
		this.Nachname = "null";
		this.Titel = "null";
		this.eMail = "null";
		this.Password = "null";
		this.manageUsers = false;
		this.readModule = false;
		this.createModule = false;
		this.acceptModule = false;
	}

	public User(String Vorname, String Nachname, String Titel, String eMail,
			String Password, boolean manageUsers, boolean createModule,
			boolean acceptModule, boolean readModule) {

		this.Vorname = Vorname;
		this.Nachname = Nachname;
		this.Titel = Titel;
		this.eMail = eMail;
		this.Password = Password;
		this.manageUsers = manageUsers;
		this.readModule = readModule;
		this.createModule = createModule;
		this.acceptModule = acceptModule;

	}

	public String getTitel() {
		return Titel;
	}

	public void setTitel(String titel) {
		Titel = titel;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Nachname == null) {
			if (other.Nachname != null)
				return false;
		} else if (!Nachname.equals(other.Nachname))
			return false;
		if (Password == null) {
			if (other.Password != null)
				return false;
		} else if (!Password.equals(other.Password))
			return false;
		if (Titel == null) {
			if (other.Titel != null)
				return false;
		} else if (!Titel.equals(other.Titel))
			return false;
		if (Vorname == null) {
			if (other.Vorname != null)
				return false;
		} else if (!Vorname.equals(other.Vorname))
			return false;
		if (acceptModule != other.acceptModule)
			return false;
		if (createModule != other.createModule)
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (manageUsers != other.manageUsers)
			return false;
		if (readModule != other.readModule)
			return false;
		return true;
	}

	public boolean getAcceptModule() {
		return acceptModule;
	}

	public boolean getCreateModule() {
		return createModule;
	}

	public String geteMail() {
		return eMail;
	}

	public boolean getManageUsers() {
		return manageUsers;
	}

	public String getNachname() {
		return Nachname;
	}

	// @XmlTransient
	public String getPassword() {
		return Password;
	}

	public boolean getReadModule() {
		return readModule;
	}

	public String getVorname() {
		return Vorname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Nachname == null) ? 0 : Nachname.hashCode());
		result = prime * result
				+ ((Password == null) ? 0 : Password.hashCode());
		result = prime * result + ((Titel == null) ? 0 : Titel.hashCode());
		result = prime * result + ((Vorname == null) ? 0 : Vorname.hashCode());
		result = prime * result + (acceptModule ? 1231 : 1237);
		result = prime * result + (createModule ? 1231 : 1237);
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + (manageUsers ? 1231 : 1237);
		result = prime * result + (readModule ? 1231 : 1237);
		return result;
	}

	public void setAcceptModule(boolean acceptModule) {
		this.acceptModule = acceptModule;
	}

	public void setCreateModule(boolean createModule) {
		this.createModule = createModule;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void setManageUsers(boolean manageUsers) {
		this.manageUsers = manageUsers;
	}

	public void setNachname(String nachname) {
		Nachname = nachname;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setReadModule(boolean readModule) {
		this.readModule = readModule;
	}

	public void setVorname(String vorname) {
		Vorname = vorname;
	}

	@Override
	public String toString() {
		return "User [Vorname=" + Vorname + ", Nachname=" + Nachname
				+ ", eMail=" + eMail + ", Password=" + Password + ", Titel="
				+ Titel + ", manageUsers=" + manageUsers + ", readModule="
				+ readModule + ", createModule=" + createModule
				+ ", acceptModule=" + acceptModule + "]";
	}

}
