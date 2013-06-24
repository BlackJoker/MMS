package de.team55.mms.server.ctrl;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import de.team55.mms.data.User;
import de.team55.mms.data.UserUpdateContainer;
import de.team55.mms.server.db.sql;

public class StartRestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HttpServer server = HttpServerFactory
					.create("http://localhost:8080/");
			HttpHandler handler = ContainerFactory
					.createContainer(HttpHandler.class);
			server.removeContext("/");
			HttpContext cc = server.createContext("/", handler);
			
			cc.setAuthenticator(new BasicAuthenticator("mms") {
				@Override
				public boolean checkCredentials(String user, String pass) {
					User tmp = new sql().getUser(user, pass);
					if (tmp == null)
						return false;
					else
						return true;
				}
			});

			server.start();
			JOptionPane.showMessageDialog(null, "Server beenden",
					"Close", JOptionPane.ERROR_MESSAGE);
			server.stop(0);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
