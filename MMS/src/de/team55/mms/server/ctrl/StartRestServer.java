package de.team55.mms.server.ctrl;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.*;

public class StartRestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {
			HttpServer server = HttpServerFactory.create( "http://localhost:8080/" );
			server.start();
			JOptionPane.showMessageDialog( null, "Server Beenden" );
			server.stop( 0 );
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}

}
