package de.team55.mms.function;

import java.util.ArrayList;
import java.util.Date;

public class Modul {

	private String name;
	private String studiengang;
	private String modulhandbuch;
	private int version;
	private Date datum;

	private String jahrgang;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<Boolean> dezernat = new ArrayList<Boolean>();


	public Modul(String name, String modulhandbuch, int version,
			Date datum, ArrayList<String> labels, ArrayList<String> values, ArrayList<Boolean> dez) {
		this.name = name;
		this.modulhandbuch=modulhandbuch;
		this.version=version;
		this.datum=datum;
		this.labels = labels;
		this.values = values;
		this.dezernat= dez;
	}

	public Modul(String name, String studiengang, String modulhandbuch,
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

	public String getStudiengang() {
		return studiengang;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public int getVersion() {
		return version;
	}

}
