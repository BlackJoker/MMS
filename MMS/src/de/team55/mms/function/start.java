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
			//K�rzel, titel, dauer, lp, tonus, inhalt, lernziele, literatur, sprache, pr�fungsform, notenbildung, dozent
			
			//Studiengang vordefinieren (mehrere)
			//Anwendungsf�cher
			
			//Modul+Modulhandbuch ver�ffentlicht boolean
			
			//registrierung?
			
			//user bearbeiten, rechte wegmachen
			//Titel f�r Dozenten
			//Dozenten f�r Institut
			//Autoren durfen nur ihre zugeordneten Module bearbeiten/l�schen/erstellen
			
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
