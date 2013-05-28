package de.team55.mms.function;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.team55.mms.gui.mainscreen;

public class start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//Kürzel, titel, dauer, lp, tonus, inhalt, lernziele, literatur, sprache, prüfungsform, notenbildung, dozent
			
			//Studiengang vordefinieren (mehrere)
			//Anwendungsfächer
			
			//Modul+Modulhandbuch veröffentlicht boolean
			
			//registrierung?
			
			//user bearbeiten, rechte wegmachen
			//Titel für Dozenten
			//Dozenten für Institut
			//Autoren durfen nur ihre zugeordneten Module bearbeiten/löschen/erstellen
			
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		mainscreen window = new mainscreen();
	}

}
