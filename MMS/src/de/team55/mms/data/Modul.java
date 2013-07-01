package de.team55.mms.data;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import de.team55.mms.data.Zuordnung;

@XmlRootElement(name = "Modul")
public class Modul {

	

	private String name;
	private ArrayList<Studiengang> studiengang;
	private int jahrgang;
	private int version;
	private Date datum;
	private boolean akzeptiert=false;
	private boolean inbearbeitung=false;

	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<Boolean> dezernat = new ArrayList<Boolean>();
	private ArrayList<Zuordnung> zuordnungen = new ArrayList<Zuordnung>();
	private ArrayList<Modulhandbuch> modulhandbuch = new ArrayList<Modulhandbuch>();
	
	public Modul(String name, ArrayList<Zuordnung> zlist, int jahrgang, ArrayList<String> labels, ArrayList<String> values, int version, ArrayList<Boolean> dezernat){
		this.name=name;
		this.zuordnungen=zlist;
		this.jahrgang=jahrgang;
		this.labels=labels;
		this.values=values;
		this.dezernat=dezernat;
		this.version=version;
		this.datum= new Date();
	}
	
	public Modul(){
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (akzeptiert ? 1231 : 1237);
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result
				+ ((dezernat == null) ? 0 : dezernat.hashCode());
		result = prime * result + (inbearbeitung ? 1231 : 1237);
		result = prime * result + jahrgang;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result
				+ ((modulhandbuch == null) ? 0 : modulhandbuch.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((studiengang == null) ? 0 : studiengang.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		result = prime * result + version;
		result = prime * result
				+ ((zuordnungen == null) ? 0 : zuordnungen.hashCode());
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
		Modul other = (Modul) obj;
		if (akzeptiert != other.akzeptiert)
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (dezernat == null) {
			if (other.dezernat != null)
				return false;
		} else if (!dezernat.equals(other.dezernat))
			return false;
		if (inbearbeitung != other.inbearbeitung)
			return false;
		if (jahrgang != other.jahrgang)
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (modulhandbuch == null) {
			if (other.modulhandbuch != null)
				return false;
		} else if (!modulhandbuch.equals(other.modulhandbuch))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (studiengang == null) {
			if (other.studiengang != null)
				return false;
		} else if (!studiengang.equals(other.studiengang))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		if (version != other.version)
			return false;
		if (zuordnungen == null) {
			if (other.zuordnungen != null)
				return false;
		} else if (!zuordnungen.equals(other.zuordnungen))
			return false;
		return true;
	}

	public void setJahrgang(int jahrgang) {
		this.jahrgang = jahrgang;
	}


	public ArrayList<Zuordnung> getZuordnungen() {
		return zuordnungen;
	}


	public void setZuordnungen(ArrayList<Zuordnung> zuordnungen) {
		this.zuordnungen = zuordnungen;
	}


	public ArrayList<Modulhandbuch> getModulhandbuch() {
		return modulhandbuch;
	}


	public void setModulhandbuch(ArrayList<Modulhandbuch> modulhandbuch) {
		this.modulhandbuch = modulhandbuch;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setStudiengang(ArrayList<Studiengang> studiengang) {
		this.studiengang = studiengang;
	}




	public void setVersion(int version) {
		this.version = version;
	}


	public void setDatum(Date datum) {
		this.datum = datum;
	}


	public void setAkzeptiert(boolean akzeptiert) {
		this.akzeptiert = akzeptiert;
	}


	public void setInbearbeitung(boolean inbearbeitung) {
		this.inbearbeitung = inbearbeitung;
	}



	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}


	public void setValues(ArrayList<String> values) {
		this.values = values;
	}


	public void setDezernat(ArrayList<Boolean> dezernat) {
		this.dezernat = dezernat;
	}


	public Modul(String name, ArrayList<Studiengang> studiengang2, String modulhandbuch, int version,
			Date datum, ArrayList<String> labels, ArrayList<String> values, ArrayList<Boolean> dez, boolean akzeptiert, boolean inbearbeitung) {
		this.name = name;
		this.studiengang = studiengang2;
		this.version=version;
		this.datum=datum;
		this.labels = labels;
		this.values = values;
		this.dezernat= dez;
		this.akzeptiert=akzeptiert;
		this.inbearbeitung=inbearbeitung;
	}

	public boolean isAkzeptiert() {
		return akzeptiert;
	}

	public boolean isInbearbeitung() {
		return inbearbeitung;
	}

	public Modul(String name, ArrayList<Studiengang> studiengang, String modulhandbuch,
			int jahrgang, ArrayList<String> labels, ArrayList<String> values, int version, ArrayList<Boolean> dez) {
		this.name = name;
		this.studiengang = studiengang;
		this.jahrgang = jahrgang;
		this.labels = labels;
		this.values = values;
		this.version =version;
		this.datum=new Date();
		this.dezernat= dez;
		this.akzeptiert=false;
		this.inbearbeitung=false;
	}

	public Date getDatum() {
		return datum;
	}

	public ArrayList<Boolean> getDezernat() {
		return dezernat;
	}

	public int getJahrgang() {
		return jahrgang;
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	

	public String getName() {
		return name;
	}

	public ArrayList<Studiengang> getStudiengang() {
		return studiengang;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public int getVersion() {
		return version;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
