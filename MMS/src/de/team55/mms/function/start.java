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
			//User, der Modul ge�ndert hat, anzeigen
			
			//Studiengang - Jahr - Modulhandbuch - Anwendungsfach/Vertiefungsfach  - Fach
			
			//Studiengang vordefinieren (mehrere)
			//Anwendungsf�cher

			//registrierung?
			
			//User kann seine stellvertreter ausw�hlen
			//Autoren durfen nur ihre zugeordneten Module bearbeiten/l�schen/erstellen
			
			//PDF Ausgabe
			//Module bearbeiten, anzeigen
			
			//Server - Client - Schnittstelle
			
			//Tests
			
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
