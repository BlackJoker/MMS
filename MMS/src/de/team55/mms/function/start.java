package de.team55.mms.function;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.team55.mms.gui.mainscreen;

public class start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Userbear passwort bearbeiten
		//User, der Modul geändert hat, anzeigen/eintragen
		//User Relationstabelle
		
		//Studiengang - Jahr - Modulhandbuch - Anwendungsfach/Vertiefungsfach  - Fach
		
		//Anwendungsfächer

		//registrierung?
		
		//User kann seine stellvertreter auswählen
		//Autoren durfen nur ihre zugeordneten Module bearbeiten/löschen/erstellen
		
		//PDF Ausgabe
		//Module bearbeiten, anzeigen
		
		//Server - Client - Schnittstelle
		
		//Tests
		
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
