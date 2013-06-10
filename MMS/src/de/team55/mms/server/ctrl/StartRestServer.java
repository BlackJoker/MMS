package de.team55.mms.server.ctrl;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.*;

import de.team55.mms.server.function.User;

public class StartRestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HttpServer server = HttpServerFactory
					.create("http://localhost:8080/");

			server.start();
			Client client = Client.create();

			WebResource webResource = client.resource("http://localhost:8080/")
					.path("test/post");
			JOptionPane.showMessageDialog(null, "warte");

			System.out.println(webResource.type(MediaType.TEXT_XML).post(
					String.class,
					new User("Max", "Mustermann", "m@m.mm", "abc", false,
							false, false, false)));

			JOptionPane.showMessageDialog(null, "Server Beenden");
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
