package de.team55.mms.function;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.team55.mms.gui.mainscreen;

public class start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**
		 * Bugs
		 */
		
		
		
		/**
		 * Aufgaben
		 */
		//User, der Modul geändert hat, anzeigen/eintragen
		//done: User Relationstabelle
		
		//beim wechsel der cards auf verbindung zum server prüfen
		
		//Studiengang - Jahr - Modulhandbuch - Anwendungsfach/Vertiefungsfach  - Fach
		
		//Anwendungsfächer

		//registrierung?
		
		//statistiken
		
		//User kann seine stellvertreter auswählen
		//Autoren durfen nur ihre zugeordneten Module bearbeiten/löschen/erstellen
		
		//PDF Ausgabe
		//Module bearbeiten, anzeigen
		
		//Server - Client - Schnittstelle
		
		//Tests
		
		//Review kritischer Prozeduren
		
		try {			
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
