package de.team55.mms.data;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Zuordnung")
public class Zuordnung {
	
	private String name;
	private String studiengang;
	private String abschluss;
	private int sid;
	private int id;
	
	public Zuordnung(){
		
	}

	public Zuordnung(int id, String name, String studiengang, int sid, String abschluss) {
		this.id=id;
		this.name=name;
		this.studiengang=studiengang;
		this.abschluss=abschluss;		
		this.sid=sid;
	}

	public Zuordnung(String name, String studiengang, int sid, String abschluss) {
		this.name=name;
		this.studiengang=studiengang;
		this.abschluss=abschluss;		
		this.sid=sid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return name + ", "+studiengang+", " + abschluss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abschluss == null) ? 0 : abschluss.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + sid;
		result = prime * result
				+ ((studiengang == null) ? 0 : studiengang.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zuordnung other = (Zuordnung) obj;
		if (abschluss == null) {
			if (other.abschluss != null)
				return false;
		} else if (!abschluss.equals(other.abschluss))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sid != other.sid)
			return false;
		if (studiengang == null) {
			if (other.studiengang != null)
				return false;
		} else if (!studiengang.equals(other.studiengang))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	public String getAbschluss() {
		return abschluss;
	}

	public void setAbschluss(String abschluss) {
		this.abschluss = abschluss;
	}

}
