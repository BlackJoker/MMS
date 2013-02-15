package de.team55.mms.gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import de.team55.mms.function.User;

public class userdialog extends JDialog {
	private JFrame owner;

	public static final int OK_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	private int userResponse;

	private JTextField textVorname;
	private JTextField textNachname;
	private JTextField textMail;
	private JTextField textPass;
	private JCheckBox cb_ModAnn;
	private JCheckBox cb_ModErst;
	private JCheckBox cb_ModLes;
	private JCheckBox cb_BV;

	private User usr = new User("", "", "", "", false, false, false, false);

	public userdialog(JFrame owner, String title, User usr) {
		super(owner, title, true);
		this.owner = owner;
		this.setResizable(false);
		this.usr = usr;
		createDialog();
	}

	public userdialog(JFrame owner, String title) {
		super(owner, title, true);
		this.setResizable(false);
		createDialog();
	}

	private void createDialog() {
		
		JPanel pnl_Dialog = new JPanel();
		pnl_Dialog.setLayout(new BorderLayout(0, 0));

		JPanel pnl_title = new JPanel();
		pnl_Dialog.add(pnl_title, BorderLayout.NORTH);
		pnl_title.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblUserData = new JLabel("User Daten:");
		pnl_title.add(lblUserData);
		lblUserData.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblUserrechte = new JLabel("Userrechte:");
		pnl_title.add(lblUserrechte);
		lblUserrechte.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pnl_fields = new JPanel();
		pnl_Dialog.add(pnl_fields, BorderLayout.WEST);
		pnl_fields.setLayout(new BorderLayout(0, 0));

		JPanel pbl_Daten = new JPanel();
		pnl_fields.add(pbl_Daten, BorderLayout.CENTER);
		pbl_Daten.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pnl_VN = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_VN.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		pbl_Daten.add(pnl_VN);

		JLabel lblVorname = new JLabel("Vorname");
		pnl_VN.add(lblVorname);

		textVorname = new JTextField(usr.getVorname());
		pnl_VN.add(textVorname);
		textVorname.setColumns(10);

		JPanel pnl_NN = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_NN.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		pbl_Daten.add(pnl_NN);

		JLabel lblNachnahme = new JLabel("Nachnahme");
		pnl_NN.add(lblNachnahme);

		textNachname = new JTextField(usr.getNachname());
		pnl_NN.add(textNachname);
		textNachname.setColumns(10);

		JPanel pnl_Mail = new JPanel();
		pbl_Daten.add(pnl_Mail);
		pnl_Mail.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));

		JLabel lblEmail = new JLabel("e-Mail");
		pnl_Mail.add(lblEmail);

		textMail = new JTextField(usr.geteMail());
		pnl_Mail.add(textMail);
		textMail.setColumns(10);

		JPanel pnl_Pass = new JPanel();
		pbl_Daten.add(pnl_Pass);
		pnl_Pass.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));

		JLabel lblPassword = new JLabel("Password");
		pnl_Pass.add(lblPassword);

		textPass = new JTextField(usr.getPassword());
		pnl_Pass.add(textPass);
		textPass.setColumns(10);

		JPanel pnl_rechte = new JPanel();
		pnl_Dialog.add(pnl_rechte);
		pnl_rechte.setLayout(new BorderLayout(0, 0));

		JPanel pnl_checkboxes = new JPanel();
		pnl_rechte.add(pnl_checkboxes);
		pnl_checkboxes.setLayout(new GridLayout(0, 1, 0, 0));

		cb_BV = new JCheckBox("Benutzer Verwalten", usr.getManageUsers());
		pnl_checkboxes.add(cb_BV);

		cb_ModErst = new JCheckBox("Module einreichen", usr.getCreateModule());
		pnl_checkboxes.add(cb_ModErst);

		cb_ModAnn = new JCheckBox("Module Annehmen", usr.getAcceptModule());
		pnl_checkboxes.add(cb_ModAnn);

		cb_ModLes = new JCheckBox("Module lesen", usr.getReadModule());
		pnl_checkboxes.add(cb_ModLes);

		JPanel pnl_footer = new JPanel();
		pnl_Dialog.add(pnl_footer, BorderLayout.SOUTH);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textVorname.getText().isEmpty()
						|| textNachname.getText().isEmpty()
						|| textMail.getText().isEmpty()
						|| textPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(owner,
							"Geben Sie gültige Daten ein!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (validateMail(textMail.getText())) {
						userResponse = OK_OPTION;
						hide();
					} else
						JOptionPane.showMessageDialog(owner,
								"Geben Sie eine gültige e-Mail-Adresse ein!", "Fehler",
								JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		pnl_footer.add(btnOk);

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userResponse = CANCEL_OPTION;
				hide();
			}
		});
		pnl_footer.add(btnAbbrechen);
		this.setContentPane(pnl_Dialog);
		this.pack();
	}

	public User getUser() {
		usr.setVorname(textVorname.getText());
		usr.setNachname(textNachname.getText());
		usr.setPassword(textPass.getText());
		usr.seteMail(textMail.getText());
		usr.setReadModule(cb_ModLes.isSelected());
		usr.setCreateModule(cb_ModErst.isSelected());
		usr.setAcceptModule(cb_ModAnn.isSelected());
		usr.setManageUsers(cb_BV.isSelected());
		return usr;
	}

	private boolean validateMail(String eMail) {
		String pat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(eMail);
		return matcher.matches();
	}

	public int showCustomDialog() {
		this.setLocationRelativeTo(owner);
		this.show();
		return userResponse;

	}
}
