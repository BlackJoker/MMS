package de.team55.mms.server.ctrl;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.LinkedList;

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

import de.team55.mms.server.db.sql;
import de.team55.mms.server.function.User;
import de.team55.mms.server.function.UserUpdateContainer;

public class StartRestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HttpServer server = HttpServerFactory
					.create("http://localhost:8080/");
			 HttpHandler handler =
					 ContainerFactory.createContainer(HttpHandler.class); 
			 server.removeContext("/"); 
			HttpContext cc  = server.createContext("/", handler);
		    cc.setAuthenticator(new BasicAuthenticator("mms") {
		        @Override
		        public boolean checkCredentials(String user, String pass) {
		        	User tmp = new sql().getUser(user,pass);
		        	if (tmp == null)
		    			return false;
		    		else
		    			return true;
		        }
		    });
		    server.setExecutor(null); // creates a default executor

			server.start();
			Client client = Client.create();
			
			User admin = new User("", "","", "admin@mms.de",
					"a384b6463fc216a5f8ecb6670f86456a", false, false, false,
					false);
client.addFilter(new HTTPBasicAuthFilter(admin.geteMail(), admin.getPassword()));

			// login test
			System.out.println("-----Login test-----");
			WebResource webResource = client.resource("http://localhost:8080/");
			admin = webResource.path("login").path(admin.geteMail())
					.path(admin.getPassword()).accept(MediaType.TEXT_XML)
					.get(User.class);
			System.out.println(admin);
			System.out.println();

			// send user test
			System.out.println("-----Send User test-----");
			User test = new User("Neuer", "TestUser","TESTTITEL", "neu@m.mm", "abc", false,
					false, false, false);
			ClientResponse response = webResource.path("user/post")
					.type(MediaType.APPLICATION_XML).post(ClientResponse.class, test);
			System.out.println(response.toString());
			System.out.println();

			// get userlist test
			System.out.println("-----Get userlist test-----");
			LinkedList<User> list = webResource.path("users")
					.accept(MediaType.TEXT_XML)
					.get(new GenericType<LinkedList<User>>() {
					});
			for (int i = 0; i < list.size(); i++)
				System.out.println(list.get(i));
			System.out.println();

			// userupdate test
			System.out.println("-----User update test-----");
			String email = test.geteMail();
			test.setVorname("UPDATED");
			test.seteMail("update@hotmail.de");

			UserUpdateContainer uuc = new UserUpdateContainer(test, email);
			response  = webResource.path("user/update")
					.type(MediaType.APPLICATION_XML).post(ClientResponse.class, uuc);
			if (response.getStatus()==201) {

				// get userlist test
				list = webResource.path("users").accept(MediaType.TEXT_XML)
						.get(new GenericType<LinkedList<User>>() {
						});
				for (int i = 0; i < list.size(); i++)
					System.out.println(list.get(i));
			} else System.out.println(response.toString());
			System.out.println(response.getClientResponseStatus().getReasonPhrase());			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
