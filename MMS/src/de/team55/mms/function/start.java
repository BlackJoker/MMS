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
		//behoben: Wenn man einloggen klickt und dann abbrechen, kann man Account Bearbeiten klicken
		//behoben: wenn man sich ausloggt wird der durchst�bern button wieder auf false gesetzt (ka wo :/)
		
		/**
		 * Aufgaben
		 */
		//User, der Modul ge�ndert hat, anzeigen/eintragen
		//User Relationstabelle
		
		//beim wechsel der cards auf verbindung zum server pr�fen
		
		//Studiengang - Jahr - Modulhandbuch - Anwendungsfach/Vertiefungsfach  - Fach
		
		//Anwendungsf�cher

		//registrierung?
		
		//User kann seine stellvertreter ausw�hlen
		//Autoren durfen nur ihre zugeordneten Module bearbeiten/l�schen/erstellen
		
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
