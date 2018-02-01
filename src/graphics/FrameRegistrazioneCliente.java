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
 * Permette la registrazione di un cliente.
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 * 
 */
public class FrameRegistrazioneCliente extends JFrame {
	private JLabel labelNome, labelCognome, labelEta, labelLogin, labelPassword;
	private JTextField textFieldNome, textFieldCognome, textFieldEta, textFieldLogin, textFieldPassword;
	private JPanel pannelloNord, pannelloCentro, pannelloSud;
	private ArrayList<Cliente> clienti;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private StrutturaSportiva struttura;
	private File file;
	private boolean giaPresente;

	/**
	 * Grafica che permette di inserire i parametri richiesti per un cliente.
	 * @param struttura la struttura sportiva
	 */
	public FrameRegistrazioneCliente(StrutturaSportiva struttura) {
		setTitle("Registrazione Cliente");
		setSize(500, 300);
		this.struttura = struttura;
		setResizable(false);
		clienti = new ArrayList<Cliente>();
		giaPresente = false;
		file = new File("ClientiDB.dat");
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
		labelEta = new JLabel("Eta");
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		textFieldNome = new JTextField(10);
		textFieldCognome = new JTextField(10);
		textFieldEta = new JTextField(10);
		textFieldLogin = new JTextField(10);
		textFieldPassword = new JTextField(10);
		pannelloCentro.setLayout(new GridLayout(5, 2));
		pannelloCentro.add(labelNome);
		pannelloCentro.add(textFieldNome);
		pannelloCentro.add(labelCognome);
		pannelloCentro.add(textFieldCognome);
		pannelloCentro.add(labelEta);
		pannelloCentro.add(textFieldEta);
		pannelloCentro.add(labelLogin);
		pannelloCentro.add(textFieldLogin);
		pannelloCentro.add(labelPassword);
		pannelloCentro.add(textFieldPassword);
		return pannelloCentro;
	}

	private JPanel addPannelloSud() {
		pannelloSud = new JPanel();
		JButton buttonSalva = new JButton("Registra cliente");
		buttonSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				struttura.aggiornaFileClienti(textFieldNome.getText(), textFieldCognome.getText(),
						textFieldLogin.getText(), textFieldPassword.getText(), textFieldEta.getText());
				clearFields();
				dispose();
			}
		});
		pannelloSud.add(buttonSalva);
		return pannelloSud;
	}

	private void clearFields() {
		textFieldCognome.setText("");
		textFieldEta.setText("");
		textFieldLogin.setText("");
		textFieldNome.setText("");
		textFieldPassword.setText("");
	}
}
