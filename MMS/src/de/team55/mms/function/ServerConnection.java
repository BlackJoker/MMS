package de.team55.mms.function;

import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import de.team55.mms.data.*;

public class ServerConnection {

	private Client client;
	private String serverPath = "http://localhost:8080/";
	private WebResource webResource;
	private User user;
	private HTTPBasicAuthFilter filter = null;
	private int connected;
	private static final int NOCONNECTION = 0;
	private static final int LOGINFALSE = 1;
	private static final int SUCCES = 2;

	private String email;
	private String password;

	public ServerConnection() {
		client = Client.create();
	}

	public int isConnected() {
		return connected;
	}

	public ArrayList<User> userload() {
		if (connect(email, password) == SUCCES) {
			return webResource.path("user/getall")
					.accept(MediaType.TEXT_XML)
					.get(new GenericType<ArrayList<User>>() {
					});
		} else {
			return null;
		}
	}

	public int connect(String eMail, String password) {
		this.email = eMail;
		this.password = password;
		if (filter != null)
			client.removeFilter(filter);
		filter = new HTTPBasicAuthFilter(email, password);
		client.addFilter(filter);
		webResource = client.resource(serverPath);
		try {
			ClientResponse response = webResource.post(ClientResponse.class);
			if (response.getStatus() != 401)
				connected = SUCCES;
			else
				connected = LOGINFALSE;
		} catch (com.sun.jersey.api.client.ClientHandlerException e) {
			connected = NOCONNECTION;
		}
		return connected;
	}

	public User login(String eMail, String password) {
		if (connect(eMail, password) == SUCCES) {
			return webResource.path("login").path(eMail).path(password)
					.accept(MediaType.TEXT_XML).get(User.class);
		} else {
			return null;
		}

	}

	public ClientResponse userupdate(User tmp, String mail) {
		if (connect(email, password) == SUCCES) {
			UserUpdateContainer uuc = new UserUpdateContainer(tmp, mail);
			return webResource.path("user/update")
					.type(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, uuc);
		}
		return null;
	}

	public ArrayList<Modulhandbuch> getModulhandbuecher() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Studiengang> getStudiengaenge() {
		if (connect(email, password) == SUCCES) {
			return webResource.path("studiengang/getall")
					.accept(MediaType.TEXT_XML)
					.get(new GenericType<ArrayList<Studiengang>>() {
					});
		}
		return null;
	}

	public void setModulhandbuch(Modulhandbuch neu_mh) {
		// TODO Auto-generated method stub

	}

	public ClientResponse setStudiengang(String name) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("studiengang/post")
					.type(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, String.class);
		}
		return null;

	}

	public int getModulVersion(String name) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("modul/getVersion").path(name)
					.accept(MediaType.TEXT_XML)
					.get(Integer.class);
		}
		return 0;
	}

	public int getStudiengangID(String name) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("studiengang/getID").path(name)
					.accept(MediaType.TEXT_XML)
					.get(Integer.class);
		}
		return 0;
	}

	public ClientResponse setModul(Modul neu) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("modul/post")
					.type(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, neu);
		}
		return null;
	}

	public ClientResponse usersave(User tmp) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("user/post")
					.type(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, tmp);
		}
		return null;

	}

	public ClientResponse deluser(String mail) {
		if (connect(email, password) == SUCCES) {
			webResource.path("user/delete").path(mail)
					.type(MediaType.APPLICATION_XML)
					.delete();
			return webResource.path("user/get").path(mail).get(ClientResponse.class);
		}
		return null;

	}

	public ArrayList<Modul> getModule(boolean b) {
		String accepted = "false";
		if(b)
			accepted = "true";
		if (connect(email, password) == SUCCES) {
			return webResource.path("modul/getList").path(accepted)
					.accept(MediaType.TEXT_XML)
					.get(new GenericType<ArrayList<Modul>>(){});
		}
		return null;
	}
	
	public ArrayList<Modulhandbuch> getModulhandbuch(String studiengang) {
		if (connect(email, password) == SUCCES) {
			return webResource.path("modulhandbuch/getallat").path(studiengang)
					.accept(MediaType.TEXT_XML)
					.get(new GenericType<ArrayList<Modulhandbuch>>(){});
		}
		return null;
	}

}
