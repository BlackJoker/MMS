package de.team55.mms.server.ctrl;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.team55.mms.server.db.sql;
import de.team55.mms.server.function.Modul;
import de.team55.mms.server.function.User;
import de.team55.mms.server.function.UserUpdateContainer;

@Path("")
public class MessageResource {

	/**
	 * returns all Users
	 * 
	 * @return List with Data of all Users
	 */

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("users")
	public ArrayList<User> getAllUsers() {
		return new sql().userload();
	}

	@POST
	@Path("modul/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response modulPost(Modul m) {
		int status = new sql().setModul(m);
		if(status==1)
			return Response.status(201).build();
		else return Response.status(500).build();
	}

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
	@Path("login/{user}/{pass}")
	public User userLogin(@PathParam("user") String user,
			@PathParam("pass") String pass) {
		User tmp = new sql().getUser(user,pass);
		if (tmp == null)
			return new User();
		else
			return tmp;
	}

	@POST
	@Path("user/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response userPost(User user) {
		int status = new sql().usersave(user);
		if(status==1)
			return Response.status(201).build();
		else return Response.status(500).build();
	}
	
	@POST
	@Path("user/update/")
	@Consumes(MediaType.APPLICATION_XML)
	public Response userUpdate(UserUpdateContainer uuc) {
		int status = new sql().userupdate(uuc.getUser(), uuc.getEmail());
		if(status==1)
			return Response.status(201).build();
		else return Response.status(500).build();
	}
}