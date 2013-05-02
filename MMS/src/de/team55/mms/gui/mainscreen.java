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
import java.util.LinkedList;

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
import de.team55.mms.function.Actions;
import de.team55.mms.function.User;

public class mainscreen {

	private static JFrame frame;
	private JPanel cards = new JPanel();
	private static JPanel panel = new JPanel();
	private DefaultTableModel tmodel;
	private final Dimension btnSz = new Dimension(140, 50);
	public sql database = new sql();
	private LinkedList<User> worklist = null;
	private User current = new User("", "", "", "", false, false, false, false);
	private JButton btnModulEinreichen = new JButton("Modul Einreichen");
	private JButton btnModulVerwaltung = new JButton("Modul Verwaltung");
	private JButton btnModulBearbeiten = new JButton("Modul bearbeiten");
	private JButton btnMHB = new JButton(
			"<html>Modulhandbücher<br>Durchstöbern");
	private JButton btnUserVerwaltung = new JButton("User Verwaltung");
	private JButton btnLogin = new JButton("Einloggen");
	private String selectedCard;

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
				selectedCard = "newmodule";
				showCard();
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
				} else {
					current = new User("", "", "", "", false, false, false,
							false);
					btnLogin.setText("Einloggen");
					selectedCard = "welcome page";
					showCard();
				}
				if (database.isConnected()) {
					checkRights();
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
					for (int i = tmodel.getRowCount() - 1; i >= 0; i--) {
						tmodel.removeRow(i);
					}
					worklist = database.userload();
					for (int i = 0; i < worklist.size(); i++) {
						addToTable(worklist.get(i));
					}
					selectedCard = "user managment";
					showCard();
				} else {
					userdialog dlg = new userdialog(frame, "User bearbeiten",
							current);
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
		if (current.getManageUsers())
			btnUserVerwaltung.setEnabled(true);
		else {
			// btnUserVerwaltung.setEnabled(false);
			btnUserVerwaltung.setText("Account bearbeiten");
			if (selectedCard.equals("user managment")) {
				selectedCard = "welcome page";
				showCard();
			}

		}
		if (current.getReadModule())
			btnMHB.setEnabled(true);
		else
			btnMHB.setEnabled(false);

	}

	private void showCard() {
		((CardLayout) cards.getLayout()).show(cards, selectedCard);
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
		JPanel pnl_newmod = new JPanel();
		//final JPanel panel = new JPanel();

		final Dimension preferredSize = new Dimension(100, 20);
		pnl_newmod.setLayout(new BorderLayout(0, 0));

		JPanel pnl_bottom = new JPanel();
		pnl_newmod.add(pnl_bottom, BorderLayout.SOUTH);

		JButton btnNeuesFeld = new JButton("Neues Feld");
		btnNeuesFeld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Platzhalter
				panel.add(Box.createRigidArea(new Dimension(0, 5)));

				JPanel pnl_tmp = new JPanel();
				panel.add(pnl_tmp);
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
				btn_tmp_entf.addActionListener(new Actions());
				pnl_tmp.add(btn_tmp_entf);

				panel.revalidate();
			}
		});
		pnl_bottom.add(btnNeuesFeld);

		JButton btnOk = new JButton("Annehmen");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Eintraege der Reihe nach auslesen
				for (int i = 0; i < panel.getComponentCount(); i=i+2) {
					JPanel tmp = (JPanel) panel.getComponent(i);
					JLabel tmplbl = (JLabel) tmp.getComponent(0);
					JTextArea tmptxt = (JTextArea) tmp.getComponent(1);
					String value = tmptxt.getText();
					String name = tmplbl.getText();
					System.out.println("Feld: " + name);
					System.out.println("Inhalt: " + value);
				}
			}
		});
		pnl_bottom.add(btnOk);

		JButton btnHome = new JButton("Zurück");
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCard = "welcome page";
				showCard();
			}
		});
		pnl_bottom.add(btnHome);

		JScrollPane scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Panel fuer Abschluss erstellen
		JPanel pnl_Abschluss = new JPanel();
		panel.add(pnl_Abschluss);
		pnl_Abschluss.setLayout(new BoxLayout(pnl_Abschluss, BoxLayout.X_AXIS));

		JLabel lbl_Abschluss = new JLabel("Abschluss");
		lbl_Abschluss.setPreferredSize(preferredSize);
		pnl_Abschluss.add(lbl_Abschluss);

		JTextArea txt_Abschluss = new JTextArea();
		txt_Abschluss.setLineWrap(true);
		pnl_Abschluss.add(txt_Abschluss);

		JButton btn_Abschluss = new JButton("Entfernen");
		btn_Abschluss.addActionListener(new Actions());
		pnl_Abschluss.add(btn_Abschluss);
		// Platzhalter
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel fuer Studiengang erstellen
		JPanel pnl_Studiengang = new JPanel();
		panel.add(pnl_Studiengang);
		pnl_Studiengang.setLayout(new BoxLayout(pnl_Studiengang,
				BoxLayout.X_AXIS));

		JLabel label_Studiengang = new JLabel("Studiengang");
		label_Studiengang.setPreferredSize(preferredSize);
		pnl_Studiengang.add(label_Studiengang);

		JTextArea txt_Studiengang = new JTextArea();
		txt_Studiengang.setLineWrap(true);
		pnl_Studiengang.add(txt_Studiengang);

		JButton btn_Studiengang = new JButton("Entfernen");
		btn_Studiengang.addActionListener(new Actions());
		pnl_Studiengang.add(btn_Studiengang);
		// Platzhalter
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel fuer Pruefungsordnung erstellen
		JPanel pnl_Pruefungsordnung = new JPanel();
		panel.add(pnl_Pruefungsordnung);
		pnl_Pruefungsordnung.setLayout(new BoxLayout(pnl_Pruefungsordnung,
				BoxLayout.X_AXIS));

		JLabel label_Pruefungsordnung = new JLabel("Abschluss");
		label_Pruefungsordnung.setPreferredSize(preferredSize);
		pnl_Pruefungsordnung.add(label_Pruefungsordnung);

		JTextArea txt_Pruefungsordnung = new JTextArea();
		txt_Pruefungsordnung.setLineWrap(true);
		pnl_Pruefungsordnung.add(txt_Pruefungsordnung);

		JButton btn_Pruefungsordnung = new JButton("Entfernen");
		btn_Pruefungsordnung.addActionListener(new Actions());
		pnl_Pruefungsordnung.add(btn_Pruefungsordnung);
		// Platzhalter
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel fuer Teilbereich erstellen
		JPanel pnl_Teilbereich = new JPanel();
		panel.add(pnl_Teilbereich);
		pnl_Teilbereich.setLayout(new BoxLayout(pnl_Teilbereich,
				BoxLayout.X_AXIS));

		JLabel label_Teilbereich = new JLabel("Teilbereich");
		label_Teilbereich.setPreferredSize(preferredSize);
		pnl_Teilbereich.add(label_Teilbereich);

		JTextArea txt_Teilbereich = new JTextArea();
		txt_Teilbereich.setLineWrap(true);
		pnl_Teilbereich.add(txt_Teilbereich);

		JButton btn_Teilbereich = new JButton("Entfernen");
		btn_Teilbereich.addActionListener(new Actions());
		pnl_Teilbereich.add(btn_Teilbereich);
		// Platzhalter
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel fuer Vorlesung erstellen
		JPanel pnl_Vorlesung = new JPanel();
		panel.add(pnl_Vorlesung);
		pnl_Vorlesung.setLayout(new BoxLayout(pnl_Vorlesung, BoxLayout.X_AXIS));

		JLabel label_Vorlesung = new JLabel("Vorlesung");
		label_Vorlesung.setPreferredSize(preferredSize);
		pnl_Vorlesung.add(label_Vorlesung);

		JTextArea txt_Vorlesung = new JTextArea();
		txt_Vorlesung.setLineWrap(true);
		pnl_Vorlesung.add(txt_Vorlesung);

		JButton btn_Vorlesung = new JButton("Entfernen");
		btn_Vorlesung.addActionListener(new Actions());
		pnl_Vorlesung.add(btn_Vorlesung);

		pnl_newmod.add(scrollPane);
		cards.add(pnl_newmod, "newmodule");

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
				"Vorname", "Nachnahme", "e-Mail", "Password",
				"User bearbeiten", "Module einreichen", "Module Annehmen",
				"Module lesen" }) {
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
					String vn = (String) usrtbl.getValueAt(row, 0);
					String nn = (String) usrtbl.getValueAt(row, 1);
					String em = (String) usrtbl.getValueAt(row, 2);
					String pw = (String) usrtbl.getValueAt(row, 3);
					boolean r1 = (boolean) usrtbl.getValueAt(row, 4);
					boolean r2 = (boolean) usrtbl.getValueAt(row, 5);
					boolean r3 = (boolean) usrtbl.getValueAt(row, 6);
					boolean r4 = (boolean) usrtbl.getValueAt(row, 7);
					User alt = new User(vn, nn, em, pw, r1, r2, r3, r4);

					userdialog dlg = new userdialog(frame, "User hinzufügen",
							alt);
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
					database.deluser((String) usrtbl.getValueAt(row, 2));
					removeFromTable(row);

				}
			}
		});
		usrpan.add(btnUserDel);

		JButton btnHome = new JButton("Zurück");
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCard = "welcome page";
				showCard();
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
		tmodel.addRow(new Object[] { usr.getVorname(), usr.getNachname(),
				usr.geteMail(), usr.getPassword(), usr.getManageUsers(),
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
	
	public static void removeFromModul(Container container){
		//Panel mit Inhalt entfernen
		
	}

}
