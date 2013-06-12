package de.team55.mms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.team55.mms.db.sql;
import de.team55.mms.function.Hash;
import de.team55.mms.function.User;

public class logindialog extends JDialog {
	private JFrame owner;

	public static final int OK_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	private int userResponse;

	private JTextField textMail;
	private JPasswordField textPass;
	public sql database = new sql();

	private User usr = null;

	public logindialog(JFrame owner, String title) {
		super(owner, title, true);
		this.setResizable(false);
		createDialog();
	}

	public User getUser() {
		return usr;
	}

	public int showCustomDialog() {
		this.setLocationRelativeTo(owner);
		this.show();
		return userResponse;

	}

	private boolean checkLogin() {
		try {
			usr = database.getUser(textMail.getText(), Hash.getMD5(textPass.getText()));
			if (usr != null) {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	private void createDialog() {

		JPanel pnl_Dialog = new JPanel();
		pnl_Dialog.setLayout(new BorderLayout(0, 0));

		JLabel lblText = new JLabel("Geben Sie ihre Login Daten ein");
		pnl_Dialog.add(lblText, BorderLayout.NORTH);
		lblText.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pbl_Daten = new JPanel();
		pnl_Dialog.add(pbl_Daten, BorderLayout.EAST);
		pbl_Daten.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pnl_Mail = new JPanel();
		pbl_Daten.add(pnl_Mail);
		pnl_Mail.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));

		JLabel lblEmail = new JLabel("e-Mail");
		pnl_Mail.add(lblEmail);

		textMail = new JTextField();
		pnl_Mail.add(textMail);
		textMail.setColumns(10);

		JPanel pnl_Pass = new JPanel();
		pbl_Daten.add(pnl_Pass);
		pnl_Pass.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));

		JLabel lblPassword = new JLabel("Password");
		pnl_Pass.add(lblPassword);

		textPass = new JPasswordField();
		pnl_Pass.add(textPass);
		textPass.setColumns(10);

		JPanel pnl_footer = new JPanel();
		pnl_Dialog.add(pnl_footer, BorderLayout.SOUTH);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textMail.getText().isEmpty()
						|| textPass.getText().isEmpty()) {
					textPass.setText("");
					JOptionPane.showMessageDialog(owner,
							"Geben Sie gültige Daten ein!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				} else {

					if (checkLogin()) {
						userResponse = OK_OPTION;
						hide();
					} else if (database.isConnected()) {
						textPass.setText("");
						JOptionPane.showMessageDialog(owner,
								"Login Daten falsch", "Fehler",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		});
		pnl_footer.add(btnOk);
		getRootPane().setDefaultButton(btnOk);

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userResponse = CANCEL_OPTION;
				hide();
			}
		});
		pnl_footer.add(btnAbbrechen);
		this.setContentPane(pnl_Dialog);
		this.pack();
	}

	private boolean validateMail(String eMail) {
		String pat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(eMail);
		return matcher.matches();
	}
}
