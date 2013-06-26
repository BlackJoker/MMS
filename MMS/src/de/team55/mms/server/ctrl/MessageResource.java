package de.team55.mms.server.ctrl;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.team55.mms.data.Modul;
import de.team55.mms.data.Modulhandbuch;
import de.team55.mms.data.Studiengang;
import de.team55.mms.data.User;
import de.team55.mms.data.UserUpdateContainer;
import de.team55.mms.server.db.sql;

@Path("")
public class MessageResource {

	/**
	 * returns a User
	 * 
	 * @param user
	 *            e-Mail of the User
	 * @param pass
	 *            Password of the User
	 * @return Data of User
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/login/{user}/{pass}")
	public User userLogin(@PathParam("user") String user,
			@PathParam("pass") String pass) {
		User tmp = new sql().getUser(user, pass);
		if (tmp == null)
			return new User();
		else
			return tmp;
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/user/get/{user}")
	public Response userLogin(@PathParam("user") String mail) {
		int status = new sql().getUser(mail);
		if (status == 1)
			return Response.status(201).build();
		else
			return Response.status(500).build();
	}

	/**
	 * returns all Users
	 * 
	 * @return List with Data of all Users
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/user/getall")
	public ArrayList<User> getAllUsers() {
		return new sql().userload();
	}

	@POST
	@Path("/user/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response userPost(User user) {
		int status = new sql().usersave(user);
		if (status == 1)
			return Response.status(201).build();
		else
			return Response.status(500).build();
	}

	@POST
	@Path("/user/update/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response userUpdate(UserUpdateContainer uuc) {
		int status = new sql().userupdate(uuc.getUser(), uuc.getEmail());
		if (status == 1)
			return Response.status(201).build();
		else
			return Response.status(500).build();
	}
	
	@DELETE
	@Path("/user/delete/{mail}")
	@Consumes(MediaType.APPLICATION_XML)
	public void userDelete(@PathParam("mail") String mail) {
		new sql().deluser(mail);
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/modul/getVersion/{name}")
	public int getModulVersion(@PathParam("name") String name) {
		return new sql().getModulVersion(name);
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/modul/get/{name}")
	public Modul getModul(@PathParam("name") String name) {
		return new sql().getModul(name);
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/modul/getList/{accepted}")
	public ArrayList<Modul> getModulList(@PathParam("accepted") String a) {
		boolean b = false;
		if(a.equals("true"))
			b = true;
		return new sql().getModule(b);
	}

	@POST
	@Path("/modul/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response modulPost(Modul m) {
		int status = new sql().setModul(m);
		if (status == 1)
			return Response.status(201).build();
		else
			return Response.status(500).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/studiengang/getID/{name}")
	public int getStudiengangID(@PathParam("name") String name) {
		return new sql().getStudiengangID(name);
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/modulhandbuch/getallat/{studiengang}")
	public ArrayList<Modulhandbuch> getModulhandbuch(@PathParam("studiengang") String studiengang) {
		return new sql().getModulhandbuch(studiengang);
	}
	

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/studiengang/getall")
	public ArrayList<Studiengang> getStudiengaenge() {
		return new sql().getStudiengaenge();
	}
	
	@POST
	@Path("/studiengang/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response studiengangPost(String name) {
		int status = new sql().setStudiengang(name);
		if (status == 1)
			return Response.status(201).build();
		else
			return Response.status(500).build();
	}

}