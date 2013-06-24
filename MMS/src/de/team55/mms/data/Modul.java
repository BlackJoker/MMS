package de.team55.mms.data;

import java.util.ArrayList;
import java.util.Date;

public class Modul {

	

	private String name;
	private ArrayList<Studiengang> studiengang;
	private String modulhandbuch;
	private int version;
	private Date datum;
	private boolean akzeptiert;
	private boolean inbearbeitung;

	private String jahrgang;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<Boolean> dezernat = new ArrayList<Boolean>();


	public Modul(String name, ArrayList<Studiengang> studiengang2, String modulhandbuch, int version,
			Date datum, ArrayList<String> labels, ArrayList<String> values, ArrayList<Boolean> dez, boolean akzeptiert, boolean inbearbeitung) {
		this.name = name;
		this.studiengang = studiengang2;
		this.modulhandbuch=modulhandbuch;
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
			String jahrgang, ArrayList<String> labels, ArrayList<String> values, int version, ArrayList<Boolean> dez) {
		this.name = name;
		this.studiengang = studiengang;
		this.modulhandbuch = modulhandbuch;
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

	public String getJahrgang() {
		return jahrgang;
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public String getModulhandbuch() {
		return modulhandbuch;
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
