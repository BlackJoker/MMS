package de.team55.mms.server.ctrl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.core.util.MultivaluedMapImpl;
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
			User admin = new User("", "", "admin@mms.de", "a384b6463fc216a5f8ecb6670f86456a", false,
					false, false, false);
			
			//login test
			System.out.println("Login test");
			WebResource webResource = client.resource("http://localhost:8080/");
			admin=webResource.path("login").path(admin.geteMail()).path(admin.getPassword()).accept(MediaType.TEXT_XML).get(User.class);
			System.out.println(admin);
			System.out.println();
			
			//send user test
			System.out.println("Send User test");
			User test = new User("Neuer", "TestUser", "neu@m.mm", "abc", false,
					false, false, false);
			//String succes = webResource.path("user/post").type(MediaType.APPLICATION_XML).post(String.class,test);
			//System.out.println(succes);
			
			//get userlist test
			System.out.println("Get userlist test");
			LinkedList<User> list = webResource.path("users").accept(MediaType.TEXT_XML).get(new GenericType<LinkedList<User>>() {});
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			
			//userupdate test
			System.out.println("User update test");
			User old = test;
			test.setVorname("Neuer Name");
			test.seteMail("home@gmx.com");

			List<User> list1 = new ArrayList<User>();
			list1.add(old);
			list1.add(test);
			
			String succes = webResource.path("user/update").type(MediaType.TEXT_XML).post(String.class, list1);
			System.out.println(succes);
			
			//get userlist test
			list = webResource.path("users").accept(MediaType.TEXT_XML).get(new GenericType<LinkedList<User>>() {});
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
