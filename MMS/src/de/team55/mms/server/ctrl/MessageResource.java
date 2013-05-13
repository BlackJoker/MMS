package de.team55.mms.server.ctrl;

import java.util.LinkedList;

import javax.ws.rs.GET;
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
	 * @param pass
	 *            Password of the User
	 * @return Data of User
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("user/{user}/{pass}")
	public User getUser(@PathParam("user") String user,
			@PathParam("pass") String pass) {
		User tmp = new sql().getUser(user, pass);
		if (tmp == null)
			tmp = new User();
		return tmp;
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
}