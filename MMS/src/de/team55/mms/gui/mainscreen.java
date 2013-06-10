package de.team55.mms.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import de.team55.mms.db.sql;
import de.team55.mms.function.Modul;
import de.team55.mms.function.User;

public class mainscreen {

	private static JFrame frame;
	private JPanel cards = new JPanel();
	private static JPanel panel = new JPanel();
	private DefaultTableModel tmodel;
	private final Dimension btnSz = new Dimension(140, 50);
	public sql database = new sql();
	private ArrayList<User> worklist = null;
	private User current = new User("", "", "", "", "", false, false, false,
			false);
	private JButton btnModulEinreichen = new JButton("Modul Einreichen");
	private JButton btnModulVerwaltung = new JButton("Modul Verwaltung");
	private JButton btnModulBearbeiten = new JButton("Modul bearbeiten");
	private JButton btnMHB = new JButton(
			"<html>Modulhandbücher<br>Durchstöbern");
	private JButton btnUserVerwaltung = new JButton("User Verwaltung");
	private JButton btnLogin = new JButton("Einloggen");
	// private String selectedCard;
	private HashMap<JButton, Integer> buttonmap = new HashMap<JButton, Integer>();

	public mainscreen() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		centerscr();
		topscr();
		leftscr();

		frame.setVisible(true);
	}

	private void leftscr() {
		JPanel leftpan = new JPanel();
		frame.getContentPane().add(leftpan, BorderLayout.WEST);

		JPanel left = new JPanel();
		leftpan.add(left);
		left.setLayout(new GridLayout(0, 1, 5, 20));

		left.add(btnModulEinreichen);
		btnModulEinreichen.setEnabled(false);
		btnModulEinreichen.setPreferredSize(btnSz);
		btnModulEinreichen.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnModulEinreichen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				showCard("newmodule");
			}

		});

		left.add(btnModulBearbeiten);
		btnModulBearbeiten.setEnabled(false);
		btnModulBearbeiten.setPreferredSize(btnSz);
		btnModulBearbeiten.setAlignmentX(Component.CENTER_ALIGNMENT);

		left.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current.geteMail().isEmpty()) {
					JTextField name = new JTextField();
					JPasswordField pwd = new JPasswordField();
					Object[] message = { "Geben Sie ihre Login-Daten ein\n",
							name, pwd };
					logindialog log = new logindialog(frame, "Login");
					int resp = log.showCustomDialog();
					if (resp == 1) {
						current = log.getUser();
						btnLogin.setText("Ausloggen");
					}
					if (database.isConnected()) {
						checkRights();
					}
				} else {
					current = new User("", "", "", "", "", false, false, false,
							false);
					if (database.isConnected()) {
						checkRights();
					}
					btnLogin.setText("Einloggen");
					btnUserVerwaltung.setText("User Verwaltung");
					btnUserVerwaltung.setEnabled(false);
					showCard("welcome page");
				}

			}
		});
		btnLogin.setPreferredSize(btnSz);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnUserVerwaltung.setEnabled(false);
		left.add(btnUserVerwaltung);
		btnUserVerwaltung.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current.getManageUsers()) {
					// Tabelle leeren
					tmodel.setRowCount(0);

					// Tabelle mit neuen daten füllen
					worklist = database.userload();
					for (int i = 0; i < worklist.size(); i++) {
						addToTable(worklist.get(i));
					}
					showCard("user managment");
				} else {
					userdialog dlg = new userdialog(frame, "User bearbeiten",
							current, false);
					int response = dlg.showCustomDialog();
					// Wenn ok gedückt wird
					// neuen User abfragen
					if (response == 1) {
						User tmp = dlg.getUser();
						database.userupdate(tmp, current.geteMail());
						current = tmp;
						checkRights();

					}
				}
			}
		});
		btnUserVerwaltung.setPreferredSize(btnSz);
		btnUserVerwaltung.setAlignmentX(Component.CENTER_ALIGNMENT);

		left.add(btnModulVerwaltung);
		btnModulVerwaltung.setEnabled(false);
		btnModulVerwaltung.setPreferredSize(btnSz);
		btnModulVerwaltung.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Jemand ne bessere idee für einen Button mit Zeilenumbruch?
		left.add(btnMHB);
		btnMHB.setEnabled(false);
		btnMHB.setPreferredSize(btnSz);
		btnMHB.setAlignmentX(Component.CENTER_ALIGNMENT);

	}

	protected void checkRights() {
		if (current.getCreateModule())
			btnModulEinreichen.setEnabled(true);
		else
			btnModulEinreichen.setEnabled(false);
		if (current.getAcceptModule()) {
			btnModulVerwaltung.setEnabled(true);
			btnModulBearbeiten.setEnabled(true);
		} else {
			btnModulVerwaltung.setEnabled(false);
			btnModulBearbeiten.setEnabled(false);
		}
		btnUserVerwaltung.setEnabled(true);
		if (current.getManageUsers()) {
			btnUserVerwaltung.setText("User Verwaltung");
		} else {
			btnUserVerwaltung.setText("Account bearbeiten");
			showCard("welcome page");
		}
		if (current.getReadModule())
			btnMHB.setEnabled(true);
		else
			btnMHB.setEnabled(false);

	}

	private void showCard(String card) {
		((CardLayout) cards.getLayout()).show(cards, card);
	}

	public void topscr() {
		JPanel top = new JPanel();
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(top, BorderLayout.NORTH);

		JLabel lblMMS = new JLabel("Modul Management System");
		lblMMS.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMMS.setHorizontalAlignment(SwingConstants.LEFT);
		lblMMS.setLabelFor(frame);
		top.add(lblMMS);
	}

	public void centerscr() {

		frame.getContentPane().add(cards, BorderLayout.CENTER);
		cards.setLayout(new CardLayout(0, 0));

		homecard();
		usermgtcard();
		newmodulecard();

	}

	private void newmodulecard() {
		final JPanel pnl_newmod = new JPanel();

		final Dimension preferredSize = new Dimension(120, 20);
		pnl_newmod.setLayout(new BorderLayout(0, 0));

		JPanel pnl_bottom = new JPanel();
		pnl_newmod.add(pnl_bottom, BorderLayout.SOUTH);

		JButton btnNeuesFeld = new JButton("Neues Feld");
		btnNeuesFeld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Platzhalter
				JPanel pnl_tmp = new JPanel();
				panel.add(pnl_tmp);
				panel.add(Box.createRigidArea(new Dimension(0, 5)));

				int numOfPanels = panel.getComponentCount();
				pnl_tmp.setLayout(new BoxLayout(pnl_tmp, BoxLayout.X_AXIS));

				String name = JOptionPane.showInputDialog(frame,
						"Name des Feldes");
				JLabel label_tmp = new JLabel(name);
				label_tmp.setPreferredSize(preferredSize);
				pnl_tmp.add(label_tmp);

				JTextArea txt_tmp = new JTextArea();
				txt_tmp.setLineWrap(true);
				pnl_tmp.add(txt_tmp);

				JButton btn_tmp_entf = new JButton("Entfernen");
				btn_tmp_entf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int id = buttonmap.get(e.getSource());
						// Feld mit ID id von Panel entfernen
						panel.remove(id);
						// Platzhalter entfernen
						panel.remove(id - 1);
						// Aus ButtonMap entfernen
						buttonmap.remove(e.getSource());

						// ids der Buttons ändern, damit auch ein Feld aus der
						// Mitte gelöscht werden kann
						HashMap<JButton, Integer> tmpmap = new HashMap<JButton, Integer>();
						Iterator<Entry<JButton, Integer>> entries = buttonmap
								.entrySet().iterator();
						while (entries.hasNext()) {
							Entry<JButton, Integer> thisEntry = entries.next();
							JButton key = thisEntry.getKey();
							int value = thisEntry.getValue();
							if (value > id) {
								value = value - 2;
							}
							tmpmap.put(key, value);
						}
						buttonmap = tmpmap;
						panel.revalidate();

					}
				});

				// Button btn_tmp_entf mit ID (numOfPanels-2) zu ButtonMap
				buttonmap.put(btn_tmp_entf, numOfPanels - 2);

				pnl_tmp.add(btn_tmp_entf);

				panel.revalidate();
			}
		});
		pnl_bottom.add(btnNeuesFeld);

		JButton btnHome = new JButton("Zurück");
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.revalidate();
				newmodulecard();
				showCard("welcome page");
			}
		});
		pnl_bottom.add(btnHome);

		JScrollPane scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Panel Modulhandbuch + Platzhalter
		panel.add(defaultmodulPanel("Modulhandbuch"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Modulhandbuch + Platzhalter
		panel.add(defaultmodulPanel("Jahrgang"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Name + Platzhalter
		panel.add(defaultmodulPanel("Name"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Kürzel + Platzhalter
		panel.add(defaultmodulPanel("Kürzel"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Titel + Platzhaler
		panel.add(defaultmodulPanel("Titel"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel LP + Platzhalter
		panel.add(defaultmodulPanel("Leistungspunkte"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Dauer + Platzhalter
		panel.add(defaultmodulPanel("Dauer"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Turnus + Platzhalter
		panel.add(defaultmodulPanel("Turnus"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Modulverantwortlicher + Platzhalter
		panel.add(defaultmodulPanel("Modulverantwortlicher"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Dozenten + Platzhalter
		panel.add(defaultmodulPanel("Dozenten"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Studiengang + Platzhalter
		panel.add(defaultmodulPanel("Studiengang"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Inhalt + Platzhalter
		panel.add(defaultmodulPanel("Inhalt"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Ziele + Platzhalter
		panel.add(defaultmodulPanel("Lernziele"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Literatur + Platzhalter
		panel.add(defaultmodulPanel("Literatur"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Sprache + Platzhalter
		panel.add(defaultmodulPanel("Sprache"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Prüfungsform + Platzhalter
		panel.add(defaultmodulPanel("Prüfungsform"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel Notenbildung + Platzhalter
		panel.add(defaultmodulPanel("Notenbildung"));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton btnOk = new JButton("Annehmen");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Name = "";
				String Studiengang = "";
				String Modulhandbuch = "";
				String Jahrgang = "";
				ArrayList<String> labels = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();

				// Eintraege der Reihe nach auslesen
				for (int i = 0; i < panel.getComponentCount(); i = i + 2) {
					JPanel tmp = (JPanel) panel.getComponent(i);
					JLabel tmplbl = (JLabel) tmp.getComponent(0);
					JTextArea tmptxt = (JTextArea) tmp.getComponent(1);
					String value = tmptxt.getText();
					String label = tmplbl.getText();
					switch (i) {
					case 0:
						Name = value;
						break;
					case 2:
						Studiengang = value;
						break;
					case 4:
						Modulhandbuch = value;
						break;
					case 6:
						Jahrgang = value;
						break;
					default:
						labels.add(label);
						values.add(value);
					}
				}
				int version = database.getModulVersion(Name) + 1;
				Modul neu = new Modul(Name, Studiengang, Modulhandbuch,
						Jahrgang, labels, values, version);
				database.setModul(neu);

				panel.removeAll();
				panel.revalidate();
				newmodulecard();
				showCard("newmodule");
			}
		});
		pnl_bottom.add(btnOk);

		pnl_newmod.add(scrollPane);
		cards.add(pnl_newmod, "newmodule");

	}

	private JPanel defaultmodulPanel(String name) {
		final Dimension preferredSize = new Dimension(120, 20);

		JPanel pnl = new JPanel();
		panel.add(pnl);
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));

		JLabel label = new JLabel(name);
		label.setPreferredSize(preferredSize);
		pnl.add(label);

		JTextArea txt = new JTextArea();
		txt.setLineWrap(true);
		pnl.add(txt);

		return pnl;
	}

	private void usermgtcard() {
		JPanel usrmg = new JPanel();
		cards.add(usrmg, "user managment");
		usrmg.setLayout(new BorderLayout(0, 0));

		JPanel usrpan = new JPanel();
		FlowLayout fl_usrpan = (FlowLayout) usrpan.getLayout();
		fl_usrpan.setAlignment(FlowLayout.RIGHT);
		usrmg.add(usrpan, BorderLayout.SOUTH);

		final JTable usrtbl = new JTable();
		JScrollPane ussrscp = new JScrollPane(usrtbl);
		usrtbl.setBorder(new LineBorder(new Color(0, 0, 0)));
		usrtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//
		// Inhalt der Tabelle
		//
		tmodel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Titel", "Vorname", "Nachnahme", "e-Mail", "User bearbeiten",
				"Module einreichen", "Module Annehmen", "Module lesen" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}

			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, boolean.class, boolean.class,
					boolean.class, boolean.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		usrtbl.setModel(tmodel);

		JButton btnUserAdd = new JButton("User hinzufügen");
		btnUserAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userdialog dlg = new userdialog(frame, "User hinzufügen");
				int response = dlg.showCustomDialog();
				// Wenn ok gedückt wird
				// neuen User abfragen
				if (response == 1) {
					User tmp = dlg.getUser();
					database.usersave(tmp);
					addToTable(tmp);
				}
			}

		});
		usrpan.add(btnUserAdd);

		JButton btnUserEdit = new JButton("User bearbeiten");
		btnUserEdit
				.setToolTipText("Zum Bearbeiten Benutzer in der Tabelle markieren");
		btnUserEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = usrtbl.getSelectedRow();
				if (row != -1) {
					String t = (String) usrtbl.getValueAt(row, 0);
					String vn = (String) usrtbl.getValueAt(row, 1);
					String nn = (String) usrtbl.getValueAt(row, 2);
					String em = (String) usrtbl.getValueAt(row, 3);
					boolean r1 = (boolean) usrtbl.getValueAt(row, 4);
					boolean r2 = (boolean) usrtbl.getValueAt(row, 5);
					boolean r3 = (boolean) usrtbl.getValueAt(row, 6);
					boolean r4 = (boolean) usrtbl.getValueAt(row, 7);
					User alt = new User(vn, nn, t, em, null, r1, r2, r3, r4);

					userdialog dlg = new userdialog(frame, "User bearbeiten",
							alt, true);
					int response = dlg.showCustomDialog();
					// Wenn ok gedückt wird
					// neuen User abfragen
					if (response == 1) {
						User tmp = dlg.getUser();
						removeFromTable(row);
						addToTable(tmp);
						database.userupdate(tmp, em);
						if (em.equals(current.geteMail())) {
							current = tmp;
							checkRights();
						}

					}

				}
			}
		});
		usrpan.add(btnUserEdit);

		JButton btnUserDel = new JButton("User löschen");
		btnUserDel
				.setToolTipText("Zum Löschen Benutzer in der Tabelle markieren");
		btnUserDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = usrtbl.getSelectedRow();
				if (row != -1) {
					database.deluser((String) usrtbl.getValueAt(row, 3));
					removeFromTable(row);

				}
			}
		});
		usrpan.add(btnUserDel);

		JButton btnHome = new JButton("Zurück");
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showCard("welcome page");
			}
		});
		usrpan.add(btnHome);

		JPanel usrcenter = new JPanel();
		usrmg.add(usrcenter, BorderLayout.CENTER);
		usrcenter.setLayout(new BorderLayout(5, 5));

		usrcenter.add(ussrscp);
		JPanel leftpan = new JPanel();
		frame.getContentPane().add(leftpan, BorderLayout.WEST);

	}

	private void homecard() {
		JPanel welcome = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) welcome.getLayout();
		flowLayout_2.setVgap(20);
		cards.add(welcome, "welcome page");

		JLabel lblNewLabel = new JLabel(
				"Willkommen beim Modul Management System");
		welcome.add(lblNewLabel);

	}

	private void addToTable(User usr) {
		tmodel.addRow(new Object[] { usr.getTitel(), usr.getVorname(),
				usr.getNachname(), usr.geteMail(), usr.getManageUsers(),
				usr.getCreateModule(), usr.getAcceptModule(),
				usr.getReadModule() });
	}

	private void removeFromTable(int rowid) {
		tmodel.removeRow(rowid);
	}

	public static void noConnection() {
		JOptionPane.showMessageDialog(frame, "Keine Verbingung zur Datenbank!",
				"Connection error", JOptionPane.ERROR_MESSAGE);
	}

	public static void removeFromModul(Container container) {
		// Panel mit Inhalt entfernen

	}

}
