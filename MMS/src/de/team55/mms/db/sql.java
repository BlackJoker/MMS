package de.team55.mms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import de.team55.mms.function.User;
import de.team55.mms.gui.mainscreen;

public class sql {

	String url = "com.mysql.jdbc.Driver";
	ResultSet rs = null;
	private Connection con = null;
	private static boolean connected = false;

	// Hostname
	private static String dbHost = "sql2.freesqldatabase.com";

	// Port -- Standard: 3306
	private String dbPort = "3306";

	// Datenbankname
	private String database = "sql28997";

	// Datenbankuser
	private String dbUser = "sql28997";

	// Datenbankpasswort
	private String dbPassword = "kQ6!qV9%25";

	public boolean connect() {
		connected = false;
		try {
			//connect to the server
			Class.forName(url);
			this.con = DriverManager.getConnection("jdbc:mysql://" + dbHost
					+ ":" + dbPort + "/" + database + "?" + "user=" + dbUser
					+ "&" + "password=" + dbPassword);
			this.con.setAutoCommit(false);
			//user table
			Statement stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user" + "("
					+ "id int NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "email varchar(255) NOT NULL, "
					+ "vorname varchar(255) , " + "namen varchar(255) , "
					+ "password varchar(255)" + ");"

			);
			this.con.commit();
			//stmt.close();
			// rights table
			//stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rights" + "("
					+ "id int NOT NULL, " 
					+ "userchange BOOLEAN NOT NULL, "
					+ "modcreate BOOLEAN NOT NULL, "
					+ "modacc BOOLEAN NOT NULL, " 
					+ "modread BOOLEAN NOT NULL"
					+ ");");
			this.con.commit();
			//stmt.close();
			//module table
			//stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS module" + "("
					+ "name varchar(255) NOT NULL, " 
					+ "Modulhandbuchname varchar(255) NOT NULL, "
					+ "Version int NOT NULL, "
					+ "Datum date NOT NULL " 
					+ ");");
			this.con.commit();
			//stmt.close();
			//text table
			//stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS text" + "("
					+ "MODHuMOD varchar(255) , " 
					+ "version int , "
					+ "label varchar(255) , "
					+ "text varchar(255));");
			this.con.commit();
			//stmt.close();
			//modulhandbuch table
			//stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS modulhandbuch" + "("
					+ "name varchar(255) , " 
					+ "studiengang varchar(255) , "
					+ "jahrgang varchar(255) " 
					+ ");");
			this.con.commit();
			stmt.close();
			
			connected = true;

		} catch (SQLException e) {
			// TODO fehler fenster aufrufen
			 e.printStackTrace();
			mainscreen.noConnection();
			connected = false;

		} catch (ClassNotFoundException e) {
			// TODO fehler fenster aufrufen
			e.printStackTrace();
			connected = false;
		}
		return connected;
	}

	public void disconnect() {
		try {
			this.con.commit();
			this.con.setAutoCommit(true);
			this.con.close();
		} catch (SQLException e) {
			// TODO fehler fenster aufrufen
			System.out.print(e.getMessage());
		}

	}

	public void usersave(User user) {

		if (connect() == true) {
			Statement state = null;
			ResultSet res = null;
			int id = -1;
			try {
				state = con.createStatement();
				state.executeUpdate("INSERT INTO user (email,vorname,namen,password) VALUES ('"
						+ user.geteMail()
						+ "','"
						+ user.getVorname()
						+ "','"
						+ user.getNachname()
						+ "','"
						+ user.getPassword()
						+ "');");
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				System.out.print(e.getMessage());
			} finally {
				try {
					con.commit();
				} catch (Exception e) {
				}
				try {
					state.close();
				} catch (Exception e) {
				}
			}
			try {
				state = this.con.createStatement();
				res = state.executeQuery("SELECT id FROM user WHERE email='"
						+ user.geteMail() + "';");
				if (res.first())
					id = res.getInt("id");
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			if (id != -1) {
				try {
					state = con.createStatement();
					state.executeUpdate("INSERT INTO rights (id,userchange,modcreate,modacc,modread) VALUES ("
							+ id
							+ ","
							+ user.getManageUsers()
							+ ","
							+ user.getCreateModule()
							+ ","
							+ user.getAcceptModule()
							+ ","
							+ user.getReadModule() + ");");
				} catch (SQLException e) {
					// TODO fehler fenster aufrufen
					System.out.print(e.getMessage());
				} finally {
					try {
						con.commit();
					} catch (Exception e) {
					}
					try {
						state.close();
					} catch (Exception e) {
					}
				}
			} else {
				System.out.print("Failed to write rights!");
			}
			disconnect();
		}
	}

	public void userupdate(User user, String email) {
		if (connect() == true) {
			Statement state = null;
			ResultSet res = null;
			int id = -1;
			try {
				state = this.con.createStatement();
				state.executeUpdate("UPDATE user SET vorname = '"
						+ user.getVorname() + "', namen = '"
						+ user.getNachname() + "', password = '"
						+ user.getPassword() + "', email = '"
						+ user.geteMail() + "' WHERE email = '"
						+ email + "' ;");
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				System.out.print(e.getMessage());
			} finally {
				try {
					this.con.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					state.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				state = this.con.createStatement();
				res = state.executeQuery("SELECT id FROM user WHERE email='"
						+ user.geteMail() + "';");
				if (res.first())
					id = res.getInt("id");
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			if (id != -1) {
				try {
					state = this.con.createStatement();
					state.executeUpdate("UPDATE rights SET userchange = "
							+ user.getManageUsers() + ", modcreate = "
							+ user.getCreateModule() + ", modacc = "
							+ user.getAcceptModule() + ", modread = "
							+ user.getReadModule() + " WHERE id = " + id + " ;");
				} catch (SQLException e) {
					// TODO fehler fenster aufrufen
					System.out.print(e.getMessage());
				} finally {
					try {
						this.con.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						state.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.print("Failed to write rights!");
			}
			disconnect();
		}
	}

	public LinkedList<User> userload() {
		User zws = null;
		ResultSet res = null;
		Statement state = null;
		int i = 0;
		int j = 0;
		LinkedList<User> list = new LinkedList<>();
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state.executeQuery("SELECT * FROM user ;");
				if (res.first()) {
					zws = new User(res.getString("vorname"),
							res.getString("namen"), res.getString("email"),
							res.getString("password"), false, false, false,
							false);
					list.add(zws);
					while (res.next()) {
						zws = new User(res.getString("vorname"),
								res.getString("namen"), res.getString("email"),
								res.getString("password"), false, false, false,
								false);
						list.add(zws);
						i++;
					}
				}
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT userchange, modcreate, modacc, modread FROM rights ;");
				if (res.first()) {
					j = list.size();
					list.getFirst()
							.setManageUsers(res.getBoolean("userchange"));
					list.getFirst()
							.setCreateModule(res.getBoolean("modcreate"));
					list.getFirst().setAcceptModule(res.getBoolean("modacc"));
					list.getFirst().setReadModule(res.getBoolean("modread"));
					for (int k = 0; k < j; k++) {
						if (res.next()) {
							list.get(k).setManageUsers(
									res.getBoolean("userchange"));
							list.get(k).setCreateModule(
									res.getBoolean("modcreate"));
							list.get(k).setAcceptModule(
									res.getBoolean("modacc"));
							list.get(k)
									.setReadModule(res.getBoolean("modread"));
						}
					}
				}
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}
		return list;

	}

	public void deluser(String email) {
		if (connect() == true) {
			Statement state = null;
			ResultSet res = null;
			int id = -1;
			try {
				state = this.con.createStatement();
				res = state.executeQuery("SELECT id FROM user WHERE email='"
						+ email + "';");
				if (res.first())
					id = res.getInt("id");
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			try {
				state = this.con.createStatement();
				state.executeUpdate("DELETE FROM user WHERE id = " + id + ";");
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				System.out.print(e.getMessage());
			} finally {
				try {
					this.con.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					state.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (id != -1) {
				try {
					state = this.con.createStatement();
					state.executeUpdate("DELETE FROM rights WHERE id = " + id
							+ ";");
				} catch (SQLException e) {
					// TODO fehler fenster aufrufen
					System.out.print(e.getMessage());
				} finally {
					try {
						this.con.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						state.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.print("Failed to write rights!");
			}
			disconnect();
		}
	}

	public User getUser(String email, String pass) {
		User zws = null;
		ResultSet res = null;
		Statement state = null;
		int id = -1;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state.executeQuery("SELECT * FROM user WHERE email='"
						+ email + "' and password='" + pass + "';");
				if (res.first()) {
					zws = new User(res.getString("vorname"),
							res.getString("namen"), res.getString("email"),
							res.getString("password"), false, false, false,
							false);
					id = res.getInt("id");
				}
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			if (zws != null) {
				try {
					state = this.con.createStatement();
					res = state.executeQuery("SELECT * FROM rights WHERE id='"
							+ id + "';");
					if (res.first()) {
						zws.setManageUsers(res.getBoolean("userchange"));
						zws.setCreateModule(res.getBoolean("modcreate"));
						zws.setAcceptModule(res.getBoolean("modacc"));
						zws.setReadModule(res.getBoolean("modread"));
					}
					res.close();
					state.close();
				} catch (SQLException e) {
					// TODO fehler fenster aufrufen
					e.printStackTrace();
				}
			}
			disconnect();
		}
		return zws;

	}
	
	public void getModul(String name){
		ResultSet res = null;
		Statement state = null;
		int version = 0;
		if(connect() == true){
			try{
				state = this.con.createStatement();
				res = state.executeQuery("SELECT MAX(Version) FROM module WHERE name = '" + name + "';");
				if(res.first()){
					version = res.getInt("Version");
				}
				res.close();
				state.close();
			}catch(SQLException e){
				
			}
			try{
				state = this.con.createStatement();
				res = state.executeQuery("SELECT label, text FROM text WHERE name = '" + name + "' AND version = " + version + ";");
				/*
				 * hier musst du noch den part machen wie du halt die daten zurück geben willst 
				 * */
				
				res.close();
				state.close();
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
		}
		
	}
	/*
	 * Hier muss man leider noch mehr machen, da ich nicht weiß wie du dich entschieden hast den dynmaischen text zu speichern
	 * 
	 */
	public void setModul(String name, int version /* LISTE? aka Text zeug*/){
		//ResultSet res = null;
		Statement state = null;
		if(connect() == true){
			try{
				state = this.con.createStatement();
				state.executeUpdate("");
				//res.close();
				state.close();
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			try{
				state = this.con.createStatement();
				state.executeUpdate("");
				//res.close();
				state.close();
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
		}
		
	}
	/*
	 * Hier das selbe, man muss noch die Rückgabe bearbeiten
	 * 
	 * 
	 */
	public void getStudiengang() {
		ResultSet res = null;
		Statement state = null;
		if(connect() == true){
			try{
				state = this.con.createStatement();
				res = state.executeQuery("SELECT studiengang FROM modulhandbuch;");
				// verarbeitung der resultset
				res.close();
				state.close();
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void getModulhandbuch(String studiengang) {
		ResultSet res = null;
		Statement state = null;
		if(connect() == true){
			try{
				state = this.con.createStatement();
				res = state.executeQuery("SELECT name, jahrgang FROM modulhandbuch WHERE studiengang = '"+ studiengang +"';");
				// verarbeitung der resultset
				res.close();
				state.close();
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void setModulhandbuch(String name, String studiengang, String jahrgang){
		Statement state = null;
		if(connect() == true){
			try{
				state = this.con.createStatement();
				state.executeUpdate("INSERT INTO modulhandbuch (name, studiengang, jahrgang) VALUES (" 
							+ name
							+", " 
							+ studiengang
							+", " 
							+ jahrgang
							+";" 
						);
				
			}catch(SQLException e){
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
		}
		
	}

	public boolean isConnected() {
		return connected;
	}
}
