package de.team55.mms.server.ctrl;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.team55.mms.server.db.sql;
import de.team55.mms.server.function.User;

@Path("")
public class MessageResource {

	/**
	 * returns a User
	 * 
	 * @param user
	 *            e-Mail of the User
	 * 
	 * @return Data of User
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("user/get/{user}")
	public User getUser(@PathParam("user") String user) {
		User tmp = new sql().getUser(user);
		if (tmp == null)
			tmp = new User();
		return tmp;
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
		User tmp = new sql().getUser(user);
		if (tmp == null)
			return new User();
		if (tmp.getPassword().equals(pass))
			return tmp;
		else
			return new User();
	}

	/**
	 * returns all Users
	 * 
	 * @return List with Data of all Users
	 */

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("users")
	public LinkedList<User> getAllUsers() {
		return new sql().userload();
	}

	@POST
	@Path("user/post/")
	@Consumes(MediaType.APPLICATION_XML)
	public String userPost(User user) {
		boolean tmp = new sql().usersave(user);
		return tmp+"";
	}

	@POST
	@Path("user/update/")
	@Consumes(MediaType.APPLICATION_XML)
	public String userUpdate(List<User> list) {
		User user = list.get(1);
		String email = list.get(0).geteMail();
		boolean tmp = new sql().userupdate(user, email);
		return tmp+"";
	}
}