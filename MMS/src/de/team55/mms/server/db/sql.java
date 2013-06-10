package de.team55.mms.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import de.team55.mms.server.function.User;
import de.team55.mms.server.function.Modul;
import de.team55.mms.gui.mainscreen;

public class sql {

	String url = "com.mysql.jdbc.Driver";
	ResultSet rs = null;
	private Connection con = null;
	private static boolean connected = false;

	// Fehlercodes

	private int FAILED = 0;
	private int SUCCES = 1;

	// Hostname
	private static String dbHost = "db4free.net";

	// Port -- Standard: 3306
	private String dbPort = "3306";

	// Datenbankname
	private String database = "mmsdb4sopra";

	// Datenbankuser
	private String dbUser = "team55";

	// Datenbankpasswort
	private String dbPassword = "qwert710";

	public boolean connect() {
		connected = false;
		try {
			// connect to the server
			Class.forName(url);
			this.con = DriverManager.getConnection("jdbc:mysql://" + dbHost
					+ ":" + dbPort + "/" + database + "?" + "user=" + dbUser
					+ "&" + "password=" + dbPassword);
			this.con.setAutoCommit(false);
			// user table
			Statement stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user" + "("
					+ "id int NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "email varchar(255) NOT NULL, "
					+ "vorname varchar(255) , " + "namen varchar(255) , "
					+ "password varchar(255)" + ");"

			);
			this.con.commit();
			// stmt.close();
			// rights table
			// stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rights" + "("
					+ "id int NOT NULL, " + "userchange BOOLEAN NOT NULL, "
					+ "modcreate BOOLEAN NOT NULL, "
					+ "modacc BOOLEAN NOT NULL, " + "modread BOOLEAN NOT NULL"
					+ ");");
			this.con.commit();
			// stmt.close();
			// module table
			// stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS module" + "("
					+ "name varchar(255) NOT NULL, "
					+ "Modulhandbuchname varchar(255) NOT NULL, "
					+ "Version int NOT NULL, " + "Datum date NOT NULL " + ");");
			this.con.commit();
			// stmt.close();
			// text table
			// stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS text" + "("
					+ "MODHuMOD varchar(255) , " + "version int , "
					+ "label varchar(255) , " + "text varchar(255));");
			this.con.commit();
			// stmt.close();
			// modulhandbuch table
			// stmt = this.con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS modulhandbuch" + "("
					+ "name varchar(255) , " + "studiengang varchar(255) , "
					+ "jahrgang varchar(255) " + ");");
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

	public int usersave(User user) {
		int ok = FAILED;
		if (connect() == true) {
			PreparedStatement state = null;
			ResultSet res = null;
			int id = -1;
			try {
				state = con
						.prepareStatement("INSERT INTO user (email,vorname,namen,password) VALUES (?,?,?,?)");
				state.setString(1, user.geteMail());
				state.setString(2, user.getVorname());
				state.setString(3, user.getNachname());
				state.setString(4, user.getPassword());
				state.executeUpdate();
				con.commit();
				state.close();

				state = con
						.prepareStatement("SELECT id FROM user WHERE email=?");
				state.setString(1, user.geteMail());
				res = state.executeQuery();
				if (res.first())
					id = res.getInt("id");
				res.close();
				state.close();
			} catch (SQLException e) {
				id = -1;
				// e.printStackTrace();
			}
			if (id != -1) {
				try {
					state = con
							.prepareStatement("INSERT INTO rights (id,userchange,modcreate,modacc,modread) VALUES (?,?,?,?,?)");
					state.setInt(1, id);
					state.setBoolean(2, user.getManageUsers());
					state.setBoolean(3, user.getCreateModule());
					state.setBoolean(4, user.getAcceptModule());
					state.setBoolean(5, user.getReadModule());
					state.executeUpdate();
					con.commit();
					state.close();
					ok = SUCCES;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				// System.out.print("Failed to write rights!");
			}
			disconnect();
		}
		return ok;
	}

	public int userupdate(User user, String email) {
		int ok = FAILED;
		if (connect() == true) {
			PreparedStatement state = null;
			ResultSet res = null;
			int id = -1;
			try {
				state = con
						.prepareStatement("SELECT id FROM user WHERE email=?;");
				state.setString(1, email);
				res = state.executeQuery();
				if (res.first())
					id = res.getInt("id");
				res.close();
				state.close();

				if (id != -1) {
					state = con
							.prepareStatement("UPDATE user SET vorname = ?, namen = ?, password = ?, email = ? WHERE id = ? ;");
					state.setString(1, user.getVorname());
					state.setString(2, user.getNachname());
					state.setString(3, user.getPassword());
					state.setString(4, user.geteMail());
					state.setInt(5, id);
					state.executeUpdate();
					state = con
							.prepareStatement("UPDATE rights SET userchange = ?, modcreate =?, modacc =?, modread =? WHERE id = ?;");
					state.setBoolean(1, user.getManageUsers());
					state.setBoolean(2, user.getCreateModule());
					state.setBoolean(3, user.getAcceptModule());
					state.setBoolean(4, user.getReadModule());
					state.setInt(5, id);
					state.executeUpdate();
					con.commit();
					state.close();
					ok = SUCCES;
				}
			} catch (SQLException e) {
				disconnect();
				ok = FAILED;
				return ok;
				// e.printStackTrace();
			}
			disconnect();
		}
		return ok;
	}

	public ArrayList<User> userload() {
		User zws = null;
		ResultSet res = null;
		Statement state = null;
		int i = 0;
		int j = 0;
		ArrayList<User> list = new ArrayList<User>();
		if (connect() == true) {
			try {
				state = con.createStatement();
				res = state
						.executeQuery("SELECT u.*,userchange,modcreate,modacc,modread FROM user AS u JOIN rights AS r ON u.id=r.id;");
				while (res.next()) {
					zws = new User(res.getString("vorname"),
							res.getString("namen"), res.getString("email"),
							res.getString("password"),
							res.getBoolean("userchange"),
							res.getBoolean("modcreate"),
							res.getBoolean("modacc"), res.getBoolean("modread"));
					list.add(zws);
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
			if (id != -1) {
				try {
					state = this.con.createStatement();
					state.executeUpdate("DELETE FROM user WHERE id = " + id
							+ ";");
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
				res = state
						.executeQuery("SELECT u.*,userchange,modcreate,modacc,modread FROM user AS u JOIN rights AS r ON u.id=r.id WHERE email='"
								+ email + "' and password='" + pass + "';");
				if (res.first()) {
					zws = new User(res.getString("vorname"),
							res.getString("namen"), res.getString("email"),
							res.getString("password"),
							res.getBoolean("userchange"),
							res.getBoolean("modcreate"),
							res.getBoolean("modacc"), res.getBoolean("modread"));
				}
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}
		return zws;

	}

	public Modul getModul(String name) {
		ResultSet res = null;
		Statement state = null;
		int version = 0;
		String Modulhandbuch = "";
		Date datum = new Date();
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT *,MAX(Version) FROM module WHERE name = '"
								+ name + "';");
				if (res.first()) {
					version = res.getInt("Version");
					Modulhandbuch = res.getString("Modulhandbuchname");
					datum = res.getDate("Datum");
				}
				res.close();
				state.close();
			} catch (SQLException e) {

			}
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT label, text FROM text WHERE name = '"
								+ name + "' AND version = " + version + ";");

				while (res.next()) {
					labels.add(res.getString("label"));
					values.add(res.getString("text"));
				}

				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}
		return new Modul(name, Modulhandbuch, version, datum, labels, values);

	}

	/*
	 * Hier muss man leider noch mehr machen, da ich nicht wei� wie du dich
	 * entschieden hast den dynmaischen text zu speichern
	 */
	public void setModul(String name, int version /* LISTE? aka Text zeug */) {
		// ResultSet res = null;
		Statement state = null;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				state.executeUpdate("");
				// res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			try {
				state = this.con.createStatement();
				state.executeUpdate("");
				// res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}

	}

	/*
	 * Hier das selbe, man muss noch die R�ckgabe bearbeiten
	 */
	public void getStudiengang() {
		ResultSet res = null;
		Statement state = null;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT studiengang FROM modulhandbuch;");
				// verarbeitung der resultset
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}

	}

	public void getModulhandbuch(String studiengang) {
		ResultSet res = null;
		Statement state = null;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT name, jahrgang FROM modulhandbuch WHERE studiengang = '"
								+ studiengang + "';");
				// verarbeitung der resultset
				res.close();
				state.close();
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}

	}

	public void setModulhandbuch(String name, String studiengang,
			String jahrgang) {
		Statement state = null;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				state.executeUpdate("INSERT INTO modulhandbuch (name, studiengang, jahrgang) VALUES ("
						+ name + ", " + studiengang + ", " + jahrgang + ");");

			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}

	}

	public boolean isConnected() {
		return connected;
	}

	public int getModulVersion(String name) {
		ResultSet res = null;
		Statement state = null;
		int version = 0;
		if (connect() == true) {
			try {
				state = this.con.createStatement();
				res = state
						.executeQuery("SELECT MAX(Version) AS Version FROM module WHERE name = '"
								+ name + "';");
				if (res.first()) {
					version = res.getInt("Version");
				}
				res.close();
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnect();
		}
		return version;
	}

	public int setModul(Modul neu) {
		int ok = FAILED;
		PreparedStatement state = null;
		if (connect() == true) {
			String name = neu.getName();
			int version = neu.getVersion();
			ArrayList<String> labels = neu.getLabels();
			ArrayList<String> values = neu.getValues();
			try {

				state = con
						.prepareStatement("INSERT INTO module (name, modulhandbuchname, version, datum) VALUES(?,?,?,?)");
				state.setString(1, name);
				state.setString(2, neu.getModulhandbuch());
				state.setInt(3, version);
				state.setDate(4, convertToSQLDate(neu.getDatum()));
				state.executeUpdate();

				state = con
						.prepareStatement("INSERT INTO modulhandbuch (name, studiengang, jahrgang) VALUES(?,?,?)");
				state.setString(1, neu.getModulhandbuch());
				state.setString(2, neu.getStudiengang());
				state.setString(3, neu.getJahrgang());
				state.executeUpdate();

				state = con
						.prepareStatement("INSERT INTO text (MODHuMOD,version, label, text) VALUES(?,?,?,?)");
				for (int i = 0; i < labels.size(); i++) {
					state.setString(1, name);
					state.setInt(2, version);
					state.setString(3, labels.get(i));
					state.setString(4, values.get(i));
					state.executeUpdate();
				}
				state.close();
				ok = SUCCES;
			} catch (SQLException e) {
				// TODO fehler fenster aufrufen
				e.printStackTrace();
			}
			disconnect();
		}
		return ok;

	}

	private java.sql.Date convertToSQLDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
}
