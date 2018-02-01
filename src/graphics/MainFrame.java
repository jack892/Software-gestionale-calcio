package graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import adapters.WindowListenerAdapter;
import classes.Biglietto;
import classes.Cliente;
import classes.Gestore;
import classes.Partita;
import classes.Stadio;
import classes.StrutturaSportiva;
import exceptions.UserNotFoundException;

/**
 * Collega le varie funzionalità della grafica.
 */
public class MainFrame extends JFrame {
	private JPanel pannelloNord, pannelloCentro, pannelloSud;

	private JTextField textFieldLogin, textFieldPassword;

	private JFrame frameRegCliente, frameRegGestore, frameLoginCliente, frameLoginGestore;

	private String passRegGes = "";

	private StrutturaSportiva struttura;

	/**
	 * Costruisce la gestione della struttura sportiva.
	 */
	public MainFrame() {
		setTitle("Gestione Struttura Sportiva");
		setSize(300, 200);
		struttura = new StrutturaSportiva();

		add(creaPannelloNord(), BorderLayout.NORTH);
		add(creaPannelloCentro(), BorderLayout.CENTER);
		add(creaPannelloSud(), BorderLayout.SOUTH);

		addWindowListener(new WindowListenerAdapter() {
			public void windowOpened(WindowEvent e) {

				struttura.aggiornaFileStadio();

				struttura.aggiornaFileBiglietti();

				struttura.aggiornaFileCalendarioPartite();

				struttura.aggiornaFileFasciaEta();

				System.out.println("File inizializzati");
			}

		});

		addWindowListener(new WindowListenerAdapter() {
			public void windowClosing(WindowEvent e) {
				struttura.salvaFile();
				System.out.println("File aggiornati");
			}
		});

		setResizable(false);
		setVisible(false);

	}

	public JPanel creaPannelloNord() {
		JLabel labelBenvenuto = new JLabel("Benvenuto");
		pannelloNord = new JPanel();
		pannelloNord.add(labelBenvenuto);
		return pannelloNord;
	}

	public void setVisibility(boolean flag) {
		setVisible(flag);
	}

	public JPanel creaPannelloCentro() {
		JLabel labelLogin = new JLabel("Login");
		textFieldLogin = new JTextField(10);
		JLabel labelPassword = new JLabel("Password");
		textFieldPassword = new JTextField(10);
		JButton buttonEntra = new JButton("Entra");

		buttonEntra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object utente = struttura.cercaUtente(textFieldLogin.getText(), textFieldPassword.getText());

				if ( utente instanceof Cliente){
					frameLoginCliente = new FrameBenvenutoCliente((Cliente)utente, struttura);
					frameLoginCliente.setVisible(true);
					System.out.println("c");
				} else {
					frameLoginGestore = new FrameBenvenutoGestore((Gestore)utente, struttura);
					frameLoginGestore.setVisible(true);
					System.out.println("g");
				}

			}
		});
		pannelloCentro = new JPanel();
		pannelloCentro.setLayout(new GridLayout(3, 2));
		pannelloCentro.add(labelLogin);
		pannelloCentro.add(textFieldLogin);
		pannelloCentro.add(labelPassword);
		pannelloCentro.add(textFieldPassword);
		pannelloCentro.add(buttonEntra);
		return pannelloCentro;
	}

	public JPanel creaPannelloSud() {
		pannelloSud = new JPanel();
		pannelloSud.setBorder(new TitledBorder(new EtchedBorder(), "Registrazione utente"));
		ButtonGroup gruppo = new ButtonGroup();
		JRadioButton radioTipoCliente = new JRadioButton("Cliente");
		JRadioButton radioTipoGestore = new JRadioButton("Gestore");
		gruppo.add(radioTipoCliente);
		gruppo.add(radioTipoGestore);
		gruppo.setSelected(radioTipoCliente.getModel(), true);

		JButton buttonRegistra = new JButton("Ok");

		struttura.controlloPreRegistrazione();

		buttonRegistra.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (radioTipoCliente.isSelected()) {
					frameRegCliente = new FrameRegistrazioneCliente(struttura);
					frameRegCliente.setVisible(true);
				}
				if (radioTipoGestore.isSelected()) {
					passRegGes = JOptionPane.showInputDialog("Inserire la password identificativa");
					try {
						if (passRegGes.equalsIgnoreCase("programmazione2")) {
							frameRegGestore = new FrameRegistrazioneGestore(struttura);
							frameRegGestore.setVisible(true);

						} else
							JOptionPane.showMessageDialog(null, "Password non corretta");
					} catch (NullPointerException e1) {

					}
				}
			}
		});
		pannelloSud.add(radioTipoCliente);
		pannelloSud.add(radioTipoGestore);
		pannelloSud.add(buttonRegistra);
		return pannelloSud;
	}

}
