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

	public Modul(String name, String studiengang, String modulhandbuch,
			String jahrgang, ArrayList<String> labels, ArrayList<String> values, int version) {
		this.name = name;
		this.studiengang = studiengang;
		this.modulhandbuch = modulhandbuch;
		this.jahrgang = jahrgang;
		this.labels = labels;
		this.values = values;
		this.version =version;
		this.datum=new Date();
	}

	public Modul(String name, String modulhandbuch, int version,
			Date datum, ArrayList<String> labels, ArrayList<String> values) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.modulhandbuch=modulhandbuch;
		this.version=version;
		this.datum=datum;
		this.labels = labels;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public String getStudiengang() {
		return studiengang;
	}

	public String getModulhandbuch() {
		return modulhandbuch;
	}

	public int getVersion() {
		return version;
	}

	public Date getDatum() {
		return datum;
	}

	public String getJahrgang() {
		return jahrgang;
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public ArrayList<String> getValues() {
		return values;
	}

}
