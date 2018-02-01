package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Cliente;
import classes.Gestore;
import classes.StrutturaSportiva;
import exceptions.CampoVuotoException;
import exceptions.UtenteGiaRegistratoException;


/**
 * Permette la registrazione di un gestore..
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 * 
 */
public class FrameRegistrazioneGestore extends JFrame {
	private JLabel labelNome, labelCognome, labelEta, labelLogin, labelPassword;
	private JTextField textFieldNome, textFieldCognome, textFieldEta, textFieldLogin, textFieldPassword;
	private JPanel pannelloNord, pannelloCentro, pannelloSud;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private StrutturaSportiva struttura;
	private boolean giaPresente;

	/**
	 * Grafica che permette di inserire i parametri richiesti per un gestore.
	 * @param struttura la struttura sportiva
	 */
	public FrameRegistrazioneGestore(StrutturaSportiva struttura) {
		setTitle("Registrazione Gestore");
		setSize(500, 300);
		setResizable(false);
		this.struttura = struttura;
		giaPresente = false;
		add(addPannelloNord(), BorderLayout.NORTH);
		add(addPannelloCentro(), BorderLayout.CENTER);
		add(addPannelloSud(), BorderLayout.SOUTH);

		setVisible(false);
	}

	private JPanel addPannelloNord() {
		pannelloNord = new JPanel();
		JLabel labelRegInizio = new JLabel("Registrazione");
		pannelloNord.add(labelRegInizio);
		return pannelloNord;
	}

	private JPanel addPannelloCentro() {
		pannelloCentro = new JPanel();
		labelNome = new JLabel("Nome");
		labelCognome = new JLabel("Cognome");
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		textFieldNome = new JTextField(10);
		textFieldCognome = new JTextField(10);
		textFieldLogin = new JTextField(10);
		textFieldPassword = new JTextField(10);
		pannelloCentro.setLayout(new GridLayout(8, 1));
		pannelloCentro.add(labelNome);
		pannelloCentro.add(textFieldNome);
		pannelloCentro.add(labelCognome);
		pannelloCentro.add(textFieldCognome);
		pannelloCentro.add(labelLogin);
		pannelloCentro.add(textFieldLogin);
		pannelloCentro.add(labelPassword);
		pannelloCentro.add(textFieldPassword);
		return pannelloCentro;
	}

	private JPanel addPannelloSud() {
		pannelloSud = new JPanel();
		JButton buttonSalva = new JButton("Registra gestore");
		buttonSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				struttura.aggiornaFileGestori(textFieldNome.getText(), textFieldCognome.getText(),
						textFieldLogin.getText(), textFieldPassword.getText());
				clearFields();
				dispose();
			}
		});
		pannelloSud.add(buttonSalva);
		return pannelloSud;
	}

	private void clearFields() {
		textFieldCognome.setText("");
		textFieldLogin.setText("");
		textFieldNome.setText("");
		textFieldPassword.setText("");
	}
}
