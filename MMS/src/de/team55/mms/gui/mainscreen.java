package de.team55.mms.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import de.team55.mms.db.sql;
import de.team55.mms.function.User;

public class mainscreen {

	private JFrame frame;
	private JPanel cards = new JPanel();
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
				checkRights();
			}
		});
		btnLogin.setPreferredSize(btnSz);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnUserVerwaltung.setEnabled(false);
		left.add(btnUserVerwaltung);
		btnUserVerwaltung.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = tmodel.getRowCount() - 1; i >= 0; i--) {
					tmodel.removeRow(i);
				}
				worklist = database.userload();
				for (int i = 0; i < worklist.size(); i++) {
					addToTable(worklist.get(i));
				}
				selectedCard = "user managment";
				showCard();
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
			btnUserVerwaltung.setEnabled(false);
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
						database.userupdate(tmp);
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

}
