package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import adapters.MouseListenerAdapter;
import classes.Biglietto;
import classes.Cliente;
import classes.Gestore;
import classes.Partita;
import classes.Stadio;
import classes.StrutturaSportiva;
import comparators.OrdinamentoDataCresc;
import comparators.OrdinamentoDataDesc;
import comparators.OrdinamentoStadiCresc;
import comparators.OrdinamentoStadiDesc;
import exceptions.AnnoNonInseritoException;

/**
 * Mermetta la registrazione di un gestore.
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 * 
 */
public class FrameBenvenutoGestore extends JFrame {
	private JPanel pannelloNord, pannelloCentro, pannelloInserisci, pannelloVisualizza, pannelloPrezzo,
			pannelloVisualizzaOrdinamento, pannelloAttivaSconti, pannelloScontiPartite, pannelloScontoPartita,
			pannelloScontoStadi, pannelloScontofasciaEta, pannelloCapienzaStadi, pannelloVisualizzaIncasso;
	private JMenuBar barraMenu = new JMenuBar();
	private String[] mesi = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
			"Settembre", "Ottobre", "Novembre", "Dicembre" };
	private GregorianCalendar dataOdierna = new GregorianCalendar();
	private GregorianCalendar dataInserimento;
	private ObjectOutputStream outCalendario;
	private ObjectInputStream inCalendario;
	private JTextArea textAreaPartite;
	private JScrollPane scrollPane;
	private JTextField prezzoField, textFieldScontoStadi, textFieldAssegnaScontoPartita, textFieldCapienzaStadio,
			textFieldIncasso, textFieldIncassoTot;
	private JComboBox<String> comboboxStadi, comboPartite;
	private JRadioButton radioStadioCre, radioStadioDec, radioDataCre, radioDataDec, radioScelta1, radioScelta2,
			radioScelta3;
	private boolean trovato;
	private StrutturaSportiva struttura;
	private Gestore gestore;

	/**
	 * Grafica che permette di inserire i parametri richiesti per un gestore.
	 * 
	 * @param biglietti
	 * 
	 * @param cognome:
	 *            il cognome del gestore
	 * @param nome:
	 *            il nome del gestore
	 * @param stadi:
	 *            la lista degli stadi
	 * @param calendarioPartite:
	 *            il calendario delle partite inserite
	 */
	public FrameBenvenutoGestore(Gestore manager, StrutturaSportiva struttura) {
		setTitle("Gestione gestore");
		setSize(900, 400);
		setJMenuBar(getBarraMenu());

		this.struttura = struttura;
		this.gestore = manager;

		textAreaPartite = new JTextArea(15, 50);
		scrollPane = new JScrollPane(textAreaPartite);

		add(creaPannelloNord(), BorderLayout.NORTH);
		add(creaPannelloCentro(), BorderLayout.CENTER);

		setVisible(false);
	}

	private JPanel creaPannelloCentro() {

		pannelloCentro = new JPanel();
		pannelloCentro.add(getPannelloInserisci());
		pannelloCentro.add(getPannelloVisualizza());
		pannelloCentro.add(getPannelloCambiaPrezzo());
		pannelloCentro.add(getPannelloSconti());
		pannelloCentro.add(getPannelloCapienzaStadi());
		pannelloCentro.add(getPannelloVisualizzaIncasso());

		return pannelloCentro;
	}

	private JMenu setMenu() {
		JMenu menu = new JMenu("Menù");
		menu.add(setInserisciPartite());
		menu.add(setVisualizzaPartite());
		menu.add(setAttivaSconti());
		menu.add(setAssegnaPrezzoPartite());
		menu.add(setModificaCapienzaStadi());
		menu.add(setVisualizzaIncasso());
		menu.addSeparator();
		menu.add(setExit());
		return menu;
	}

	private JMenuItem setVisualizzaPartite() {
		JMenuItem visualizzaPartite = new JMenuItem("Visualizza partite");
		visualizzaPartite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				textAreaPartite.setEditable(false);
				textAreaPartite.setText("");

				if (struttura.getCalendarioPartite().size() == 0) {
					textAreaPartite.append("\t\t*************** Calendario Vuoto ***************" + "\n");
				} else {
					for (Partita p : struttura.getCalendarioPartite()) {
						textAreaPartite.append(p.toString() + "\n");
					}
				}

				pannelloVisualizza.setVisible(true);
				pannelloPrezzo.setVisible(false);
				pannelloInserisci.setVisible(false);
				pannelloAttivaSconti.setVisible(false);
				pannelloCapienzaStadi.setVisible(false);
				pannelloVisualizzaIncasso.setVisible(false);
			}
		});
		return visualizzaPartite;
	}

	private JMenuItem setAttivaSconti() {
		JMenuItem attivaSconti = new JMenuItem("Attiva sconti su biglietti");
		attivaSconti.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pannelloVisualizza.setVisible(false);
				pannelloInserisci.setVisible(false);
				pannelloPrezzo.setVisible(false);
				pannelloAttivaSconti.setVisible(true);
				pannelloCapienzaStadi.setVisible(false);
				pannelloVisualizzaIncasso.setVisible(false);
			}
		});
		return attivaSconti;
	}

	private JMenuItem setAssegnaPrezzoPartite() {
		JMenuItem assegnaPrezzo = new JMenuItem("Assegna prezzo alle partite");
		assegnaPrezzo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaIncasso.setVisible(false);
				pannelloVisualizza.setVisible(false);
				pannelloInserisci.setVisible(false);
				pannelloPrezzo.setVisible(true);
				pannelloAttivaSconti.setVisible(false);
				pannelloCapienzaStadi.setVisible(false);
			}
		});
		return assegnaPrezzo;
	}

	private JMenuItem setModificaCapienzaStadi() {
		JMenuItem modifica = new JMenuItem("Modifica capienza stadi");
		modifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaIncasso.setVisible(false);
				pannelloCapienzaStadi.setVisible(true);
				pannelloVisualizza.setVisible(false);
				pannelloInserisci.setVisible(false);
				pannelloPrezzo.setVisible(false);
				pannelloAttivaSconti.setVisible(false);
			}
		});
		return modifica;
	}

	private JMenuItem setVisualizzaIncasso() {
		JMenuItem visualizzaIncasso = new JMenuItem("Visualizza incasso");
		visualizzaIncasso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaIncasso.setVisible(true);
				pannelloCapienzaStadi.setVisible(false);
				pannelloVisualizza.setVisible(false);
				pannelloInserisci.setVisible(false);
				pannelloPrezzo.setVisible(false);
				pannelloAttivaSconti.setVisible(false);
			}
		});
		return visualizzaIncasso;
	}

	private JMenuItem setExit() {
		JMenuItem esci = new JMenuItem("Logout");
		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		return esci;
	}

	private JMenuItem setInserisciPartite() {
		JMenuItem inserisci = new JMenuItem("Inserisci partite");
		inserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pannelloInserisci.setVisible(true);
				pannelloVisualizza.setVisible(false);
				pannelloPrezzo.setVisible(false);
				pannelloAttivaSconti.setVisible(false);
				pannelloCapienzaStadi.setVisible(false);
				pannelloVisualizzaIncasso.setVisible(false);
			}
		});
		return inserisci;
	}

	private JMenuBar getBarraMenu() {
		barraMenu.add(setMenu());
		return barraMenu;
	}

	private JPanel getPannelloInserisci() {
		JLabel labelSquadraA = new JLabel("Inserire la prima squadra: ");
		JTextField textFieldSquadraA = new JTextField(15);
		JLabel labelSquadraB = new JLabel("Inserire la seconda squadra: ");
		JTextField textFieldSquadraB = new JTextField(15);
		JLabel labelStadio = new JLabel("Scegli lo stadio: ");
		JButton buttonInserisci = new JButton("Inserisci");
		JLabel labelAnno = new JLabel("Anno");
		JLabel labelMese = new JLabel("Mese");
		JLabel labelGiorno = new JLabel("Giorno");
		JPanel panelOrario = new JPanel();
		JLabel labelOrario = new JLabel("Orario");
		JComboBox<String> comboBoxOre = new JComboBox<>();
		JComboBox<String> comboBoxMinuti = new JComboBox<>();
		comboboxStadi = new JComboBox<String>();
		JComboBox<String> comboBoxGiorno = new JComboBox<>();
		JComboBox<String> comboBoxMesi = new JComboBox<>(mesi);

		JTextField textFieldAnno = new JTextField(10);

		textFieldAnno.addMouseListener(new MouseListenerAdapter() {
			public void mouseExited(MouseEvent e) {
				try {
					if (!textFieldAnno.getText().equals("")) {
						comboBoxGiorno.setEnabled(true);
						dataInserimento = new GregorianCalendar(Integer.parseInt(textFieldAnno.getText()),
								comboBoxMesi.getSelectedIndex(), 1);
						comboBoxGiorno.removeAllItems();
						for (int i = 0; i < dataInserimento.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); i++)
							comboBoxGiorno.addItem("" + (i + 1));
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "L'Anno deve essere numerico");
				}

			}
		});
		comboBoxMesi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textFieldAnno.getText().equals(""))
						throw new AnnoNonInseritoException();

					else {
						comboBoxGiorno.setEnabled(true);
						dataInserimento = new GregorianCalendar(Integer.parseInt(textFieldAnno.getText()),
								comboBoxMesi.getSelectedIndex(), 1);
						comboBoxGiorno.removeAllItems();
						for (int i = 0; i < dataInserimento.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); i++)
							comboBoxGiorno.addItem("" + (i + 1));
					}
				} catch (AnnoNonInseritoException e1) {

					JOptionPane.showMessageDialog(null, "Inserire prima l'anno");
					comboBoxGiorno.setEnabled(false);
				}
			}
		});

		panelOrario.add(comboBoxOre);
		panelOrario.add(comboBoxMinuti);

		for (Stadio s : struttura.getStadi()) {
			comboboxStadi.addItem(s.getNome());
		}

		for (int i = 0; i < 60; i++) {
			if (i >= 0 && i < 10)
				comboBoxMinuti.addItem("0" + i);
			else
				comboBoxMinuti.addItem("" + i);
		}

		for (int i = 0; i < 24; i++) {
			if (i >= 0 && i < 10)
				comboBoxOre.addItem("0" + i);
			else
				comboBoxOre.addItem("" + i);
		}

		pannelloInserisci = new JPanel();
		pannelloInserisci.setLayout(new GridLayout(8, 2));
		pannelloInserisci.add(labelSquadraA);
		pannelloInserisci.add(textFieldSquadraA);
		pannelloInserisci.add(labelSquadraB);
		pannelloInserisci.add(textFieldSquadraB);
		pannelloInserisci.add(labelStadio);
		pannelloInserisci.add(comboboxStadi);
		pannelloInserisci.add(labelAnno);
		pannelloInserisci.add(textFieldAnno);
		pannelloInserisci.add(labelMese);
		pannelloInserisci.add(comboBoxMesi);
		pannelloInserisci.add(labelGiorno);
		pannelloInserisci.add(comboBoxGiorno);
		pannelloInserisci.add(labelOrario);
		pannelloInserisci.add(panelOrario);
		pannelloInserisci.add(buttonInserisci);

		buttonInserisci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (textFieldSquadraA.getText().equalsIgnoreCase(textFieldSquadraB.getText()))
					JOptionPane.showMessageDialog(null, "Inserire squadre diverse");
				else if (textFieldSquadraA.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Inserire la squadra di casa");
				else if (textFieldSquadraB.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Inserire la squadra fuori casa");
				else if (textFieldAnno.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Inserire l'anno");

				else if (dataOdierna.after(new GregorianCalendar(Integer.parseInt(textFieldAnno.getText()),
						comboBoxMesi.getSelectedIndex(), comboBoxGiorno.getSelectedIndex() + 1,
						comboBoxOre.getSelectedIndex(), comboBoxMinuti.getSelectedIndex()))) {
					JOptionPane.showMessageDialog(null, "Impossibile inserire la data nel passato");
				} else if (dataOdierna.after(new GregorianCalendar(Integer.parseInt(textFieldAnno.getText()),
						comboBoxMesi.getSelectedIndex(), comboBoxGiorno.getSelectedIndex() + 1,
						comboBoxOre.getSelectedIndex() - 8, comboBoxMinuti.getSelectedIndex()))) {
					JOptionPane.showMessageDialog(null, "Inserire la partita 8 ore prima dell'evento");
				}

				else {

					dataInserimento = new GregorianCalendar(Integer.parseInt(textFieldAnno.getText()),
							comboBoxMesi.getSelectedIndex(), comboBoxGiorno.getSelectedIndex() + 1,
							comboBoxOre.getSelectedIndex(), comboBoxMinuti.getSelectedIndex());
					System.out.println(dataInserimento);

					if (!partitaTrovata(struttura.getCalendarioPartite(),
							new Partita(textFieldSquadraA.getText(), textFieldSquadraB.getText(),
									comboboxStadi.getSelectedItem().toString(), dataInserimento))) {

						scriviPartita(struttura.getCalendarioPartite(),
								new Partita(textFieldSquadraA.getText(), textFieldSquadraB.getText(),
										comboboxStadi.getSelectedItem().toString(), dataInserimento));

					} else {
						JOptionPane.showMessageDialog(null,
								"Evento esistente - Pianificare un nuovo evento o una data differente.");
					}

				}
			}

		});

		// pannelloInserisci.setVisible(false);
		return pannelloInserisci;

	}

	private JPanel getPannelloVisualizza() {
		pannelloVisualizza = new JPanel();
		pannelloVisualizza.setLayout(new GridLayout(2, 1));
		pannelloVisualizza.add(scrollPane);
		pannelloVisualizza.add(getPannelloVisualizzaOrdinamento());
		pannelloVisualizza.setVisible(false);
		return pannelloVisualizza;
	}

	private JPanel getPannelloVisualizzaOrdinamento() {
		pannelloVisualizzaOrdinamento = new JPanel();
		pannelloVisualizzaOrdinamento.setBorder(new TitledBorder(new EtchedBorder(), "Scelta Ordinamento"));
		radioStadioCre = new JRadioButton("Capienza degli stadi crescente");
		radioStadioDec = new JRadioButton("Capienza degli stadi decrescente");
		radioDataCre = new JRadioButton("Data partita crescente");
		radioDataDec = new JRadioButton("Data partita drescente");
		ButtonGroup gruppoCapienza = new ButtonGroup();

		gruppoCapienza.add(getRadioStadioCrescente());
		gruppoCapienza.add(getRadioStadioDecrescente());
		gruppoCapienza.add(getRadioDataCrescente());
		gruppoCapienza.add(getRadioDataDecrescente());

		pannelloVisualizzaOrdinamento.add(getRadioStadioCrescente());
		pannelloVisualizzaOrdinamento.add(getRadioStadioDecrescente());
		pannelloVisualizzaOrdinamento.add(getRadioDataCrescente());
		pannelloVisualizzaOrdinamento.add(getRadioDataDecrescente());
		return pannelloVisualizzaOrdinamento;
	}

	private JPanel getPannelloCapienzaStadi() {
		JComboBox<String> listaStadi = new JComboBox<String>();
		textFieldCapienzaStadio = new JTextField(5);
		// debug
		if (struttura.getStadi().size() > 0)
			textFieldCapienzaStadio.setText("" + struttura.getStadi().get(0).getCapienza());
		JButton confirmButton = new JButton("Aggiorna capienza");

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Stadio s : struttura.getStadi()) {
					if (s.getNome().equals(listaStadi.getSelectedItem().toString())) {
						s.setCapienza(textFieldCapienzaStadio.getText());
						JOptionPane.showMessageDialog(null, "Capienza aggiornata");
					}
				}
			}
		});

		for (Stadio s : struttura.getStadi()) {
			listaStadi.addItem(s.getNome());
		}

		listaStadi.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				for (Stadio s : struttura.getStadi()) {
					if (s.getNome().equals(listaStadi.getSelectedItem().toString())) {
						textFieldCapienzaStadio.setText(s.getCapienza());
					}
				}
			}
		});
		pannelloCapienzaStadi = new JPanel();
		pannelloCapienzaStadi.add(listaStadi);
		pannelloCapienzaStadi.add(textFieldCapienzaStadio);
		pannelloCapienzaStadi.add(confirmButton);
		pannelloCapienzaStadi.setVisible(false);
		return pannelloCapienzaStadi;
	}

	private Component getPannelloVisualizzaIncasso() {
		JComboBox<String> listaStadi = new JComboBox<String>();
		JTextField textFieldIncassoTot = new JTextField(10);
		textFieldIncasso = new JTextField(5);
		struttura.caricaBiglietti(struttura.getBiglietti());
		// debug
		if (struttura.getStadi().size() > 0)
			textFieldIncasso.setText("" + struttura.getIncassoPerStadio(struttura.getStadi().get(0).getNome()));
		textFieldIncassoTot.setText("" + struttura.getIncassoTotale());
		for (Stadio s : struttura.getStadi()) {
			listaStadi.addItem(s.getNome());
		}

		listaStadi.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				for (Stadio s : struttura.getStadi()) {
					if (s.getNome().equals(listaStadi.getSelectedItem().toString())) {
						textFieldIncasso.setText(struttura.getIncassoPerStadio(s.getNome()));
					}
				}
			}
		});
		pannelloVisualizzaIncasso = new JPanel();
		pannelloVisualizzaIncasso.add(listaStadi);
		pannelloVisualizzaIncasso.add(textFieldIncasso);
		pannelloVisualizzaIncasso.add(new JLabel(" incasso totale:"));
		pannelloVisualizzaIncasso.add(textFieldIncassoTot);
		pannelloVisualizzaIncasso.setVisible(false);
		return pannelloVisualizzaIncasso;
	}

	private JRadioButton getRadioStadioCrescente() {
		radioStadioCre.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setOrdinamentoStadiCrescente();
			}

		});

		return radioStadioCre;
	}

	private JRadioButton getRadioStadioDecrescente() {
		radioStadioDec.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setOrdinamentoStadiDecrescente();
			}
		});
		return radioStadioDec;
	}

	private JRadioButton getRadioDataCrescente() {
		radioDataCre.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setOrdinamentoDataCrescente();

			}
		});
		return radioDataCre;
	}

	private JRadioButton getRadioDataDecrescente() {
		radioDataDec.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOrdinamentoDataDecrescente();
			}
		});
		return radioDataDec;
	}

	private void setOrdinamentoStadiCrescente() {
		textAreaPartite.setText("");
		for (Partita p : struttura.getListaTuttePartiteOrdinate(new OrdinamentoStadiCresc())) {
			textAreaPartite.append(p.toString() + "\n");
		}
	}

	private void setOrdinamentoStadiDecrescente() {
		textAreaPartite.setText("");
		for (Partita p : struttura.getListaTuttePartiteOrdinate(new OrdinamentoStadiDesc())) {
			textAreaPartite.append(p.toString() + "\n");
		}
	}

	private void setOrdinamentoDataCrescente() {
		textAreaPartite.setText("");
		for (Partita p : struttura.getListaTuttePartiteOrdinate(new OrdinamentoDataCresc())) {
			textAreaPartite.append(p.toString() + "\n");
		}
	}

	private void setOrdinamentoDataDecrescente() {
		textAreaPartite.setText("");
		for (Partita p : struttura.getListaTuttePartiteOrdinate(new OrdinamentoDataDesc())) {
			textAreaPartite.append(p.toString() + "\n");
		}
	}

	private JPanel getPannelloCambiaPrezzo() {
		JComboBox<String> listaStadi = new JComboBox<String>();
		prezzoField = new JTextField(5);
		// debug
		if (struttura.getStadi().size() > 0)
			prezzoField.setText("" + struttura.getStadi().get(0).getPrezzo());
		JButton confirmButton = new JButton("Aggiorna prezzo");

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Stadio s : struttura.getStadi()) {
					if (s.getNome().equals(listaStadi.getSelectedItem().toString())) {
						s.setPrezzo(Double.parseDouble(prezzoField.getText()));
						JOptionPane.showMessageDialog(null, "Prezzo aggiornato");
					}
				}
			}
		});

		for (Stadio s : struttura.getStadi()) {
			listaStadi.addItem(s.getNome());
		}

		listaStadi.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				for (Stadio s : struttura.getStadi()) {
					if (s.getNome().equals(listaStadi.getSelectedItem().toString())) {
						prezzoField.setText(String.valueOf(s.getPrezzo()));
					}
				}
			}
		});
		pannelloPrezzo = new JPanel();
		pannelloPrezzo.add(listaStadi);
		pannelloPrezzo.add(prezzoField);
		pannelloPrezzo.add(new JLabel("€ "));
		pannelloPrezzo.add(confirmButton);
		pannelloPrezzo.setVisible(false);
		return pannelloPrezzo;
	}

	private JPanel getPannelloSconti() {
		JPanel pannelloRadioSconti = new JPanel();
		pannelloAttivaSconti = new JPanel();
		pannelloAttivaSconti.setBorder(new TitledBorder(new EtchedBorder()));
		pannelloAttivaSconti.setLayout(new GridLayout(2, 1));
		pannelloScontiPartite = new JPanel();
		JButton buttonScegli = new JButton("Scegli");
		// pannelloScontiPartite.setVisible(false);

		radioScelta1 = new JRadioButton("Applica sconto per partita");
		radioScelta2 = new JRadioButton("Applica sconto per stadio");
		radioScelta3 = new JRadioButton("Applica sconto per fascia età");
		ButtonGroup gruppoScelta = new ButtonGroup();
		gruppoScelta.add(radioScelta1);
		gruppoScelta.add(radioScelta2);
		gruppoScelta.add(radioScelta3);

		pannelloRadioSconti.add(radioScelta1);
		pannelloRadioSconti.add(radioScelta2);
		pannelloRadioSconti.add(radioScelta3);
		pannelloRadioSconti.add(buttonScegli);

		buttonScegli.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (radioScelta1.isSelected()) {

					comboPartite.removeAllItems();

					comboPartite.addItem("Scegli la partita");

					for (Partita p : struttura.getCalendarioPartite()) {
						if (p.getDataGregorian().after(dataOdierna)) {
							comboPartite.addItem(p.getSquadraA().toUpperCase() + " Vs. " + p.getSquadraB().toUpperCase()
									+ " " + p.getGiorno() + "/" + p.getMese() + "/" + p.getAnno() + "  " + p.getOra()
									+ ":" + p.getMinuti());
						}
					}

					pannelloScontoPartita.setVisible(true);
					pannelloScontoStadi.setVisible(false);
					pannelloScontofasciaEta.setVisible(false);
				}
				if (radioScelta2.isSelected()) {
					pannelloScontoStadi.setVisible(true);
					pannelloScontoPartita.setVisible(false);
					pannelloScontofasciaEta.setVisible(false);
				}
				if (radioScelta3.isSelected()) {
					pannelloScontofasciaEta.setVisible(true);
					pannelloScontoPartita.setVisible(false);
					pannelloScontoStadi.setVisible(false);
				}

			}
		});

		pannelloAttivaSconti.add(pannelloRadioSconti);
		pannelloScontiPartite.add(getPannelloScontoPartita());
		pannelloScontiPartite.add(getPannelloScontoStadio());
		pannelloScontiPartite.add(getPannelloScontoFasciaEta());
		pannelloAttivaSconti.add(pannelloScontiPartite);
		pannelloAttivaSconti.setVisible(false);
		return pannelloAttivaSconti;
	}

	private JPanel getPannelloScontoPartita() {
		pannelloScontoPartita = new JPanel();

		comboPartite = new JComboBox<>();
		textFieldAssegnaScontoPartita = new JTextField(5);

		JButton buttonAssegnaPrezzo = new JButton("Assegna sconto");

		buttonAssegnaPrezzo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double assegnaSconto = Double.parseDouble(textFieldAssegnaScontoPartita.getText());
					if (assegnaSconto >= 0 && assegnaSconto <= 100) {
						String temp = "";
						for (Partita p : struttura.getCalendarioPartite()) {
							temp = p.getSquadraA().toUpperCase() + " Vs. " + p.getSquadraB().toUpperCase() + " "
									+ p.getGiorno() + "/" + p.getMese() + "/" + p.getAnno() + "  " + p.getOra() + ":"
									+ p.getMinuti();
							if (temp.equalsIgnoreCase(comboPartite.getSelectedItem().toString())) {
								p.assegnaPercentualeSconto(assegnaSconto);
								JOptionPane.showMessageDialog(null, "Sconto assegnato");
							}
						}
					} else
						throw new NumberFormatException();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Inserire la percentuale sconto correttamente");
				}
			}
		});

		comboPartite.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				String temp = "";

				if (comboPartite.getItemCount() != 0) {
					if (comboPartite.getSelectedIndex() == 0)
						textFieldAssegnaScontoPartita.setText("");

					else {
						for (Partita p : struttura.getCalendarioPartite()) {
							temp = p.getSquadraA().toUpperCase() + " Vs. " + p.getSquadraB().toUpperCase() + " "
									+ p.getGiorno() + "/" + p.getMese() + "/" + p.getAnno() + "  " + p.getOra() + ":"
									+ p.getMinuti();
							if (temp.equalsIgnoreCase(comboPartite.getSelectedItem().toString()))
								textFieldAssegnaScontoPartita.setText("" + p.getPercentualeSconto());
						}
					}
				}
			}
		});

		pannelloScontoPartita.add(comboPartite);
		pannelloScontoPartita.add(textFieldAssegnaScontoPartita);
		pannelloScontoPartita.add(new JLabel("% "));
		pannelloScontoPartita.add(buttonAssegnaPrezzo);
		pannelloScontoPartita.setVisible(false);
		return pannelloScontoPartita;
	}

	private JPanel getPannelloScontoStadio() {
		pannelloScontoStadi = new JPanel();
		ArrayList<Stadio> elencoStadi = (ArrayList<Stadio>) struttura.getStadi().clone();
		JComboBox<String> comboStadi = new JComboBox<>();
		comboStadi.addItem("Scegli uno stadio");
		textFieldScontoStadi = new JTextField(5);
		JButton buttonAssegnaPrezzo = new JButton("Assegna sconto");

		buttonAssegnaPrezzo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double assegnaSconto = Double.parseDouble(textFieldScontoStadi.getText());

					if (assegnaSconto >= 0 && assegnaSconto <= 100) {
						for (Stadio s : struttura.getStadi()) {
							if (s.getNome().equals(comboStadi.getSelectedItem().toString())) {
								s.assegnaPercentualeSconto(assegnaSconto);
								JOptionPane.showMessageDialog(null, "Sconto assegnato");
							}
						}
					} else
						throw new NumberFormatException();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Inserire la percentuale sconto correttamente");
				}
			}
		});

		for (Stadio s : struttura.getStadi()) {
			comboStadi.addItem(s.getNome());
		}

		comboStadi.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (comboStadi.getSelectedIndex() == 0) {
					textFieldScontoStadi.setText("");
				} else {
					for (Stadio s : struttura.getStadi()) {
						if (s.getNome().equals(comboStadi.getSelectedItem().toString())) {
							textFieldScontoStadi.setText(String.valueOf(s.getPercentualeSconto()));
						}
					}
				}
			}
		});
		pannelloScontoStadi.add(comboStadi);
		pannelloScontoStadi.add(textFieldScontoStadi);
		pannelloScontoStadi.add(new JLabel("% "));
		pannelloScontoStadi.add(buttonAssegnaPrezzo);
		pannelloScontoStadi.setVisible(false);
		return pannelloScontoStadi;
	}

	private JPanel getPannelloScontoFasciaEta() {
		pannelloScontofasciaEta = new JPanel();
		JComboBox<String> comboFasciaEta = new JComboBox<>();
		JTextField textFieldFascia = new JTextField(5);
		JButton assegnaSconto = new JButton("Assegna sconto");
		comboFasciaEta.addItem("Scegli la fascia età");
		comboFasciaEta.addItem("Minorenni ( < 18 anni)");
		comboFasciaEta.addItem("Adulti (da 18 a 60 anni)");
		comboFasciaEta.addItem("Anziani ( > 60 anni)");

		comboFasciaEta.setSelectedItem("Scegli la fascia");
		pannelloScontofasciaEta.add(comboFasciaEta);
		pannelloScontofasciaEta.add(textFieldFascia);
		pannelloScontofasciaEta.add(new JLabel("% "));
		pannelloScontofasciaEta.add(assegnaSconto);

		assegnaSconto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double assegnaSconto = Double.parseDouble(textFieldFascia.getText());

					if (assegnaSconto >= 0 && assegnaSconto <= 100) {

						if (comboFasciaEta.getSelectedIndex() == 1) {
							struttura.getFasciaEta().set(0, Double.parseDouble(textFieldFascia.getText()));
						} else if (comboFasciaEta.getSelectedIndex() == 2) {
							struttura.getFasciaEta().set(1, Double.parseDouble(textFieldFascia.getText()));
						} else if (comboFasciaEta.getSelectedIndex() == 3) {
							struttura.getFasciaEta().set(2, Double.parseDouble(textFieldFascia.getText()));
						}
						JOptionPane.showMessageDialog(null, "Sconto assegnato");
					} else
						throw new NumberFormatException();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Inserire la percentuale sconto correttamente");
				}
			}
		});

		comboFasciaEta.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (comboFasciaEta.getSelectedIndex() == 0) {
					textFieldFascia.setText("");
				} else {
					if (comboFasciaEta.getSelectedIndex() == 1) {
						textFieldFascia.setText("" + struttura.getFasciaEta().get(0));
					} else if (comboFasciaEta.getSelectedIndex() == 2) {
						textFieldFascia.setText("" + struttura.getFasciaEta().get(1));
					} else if (comboFasciaEta.getSelectedIndex() == 3) {
						textFieldFascia.setText("" + struttura.getFasciaEta().get(2));
					}
				}
			}
		});

		pannelloScontofasciaEta.setVisible(false);
		return pannelloScontofasciaEta;
	}

	private JPanel creaPannelloNord() {
		pannelloNord = new JPanel();
		JLabel labelBenvenuto = new JLabel(
				"Benvenuto " + gestore.getCognome().toUpperCase() + " " + gestore.getNome().toUpperCase());
		pannelloNord.add(labelBenvenuto);
		return pannelloNord;
	}

	private String getGregorianCalendarToString(GregorianCalendar dataInserimento) {
		String temp = dataInserimento.get(GregorianCalendar.DAY_OF_MONTH) + " / "
				+ dataInserimento.get(GregorianCalendar.MONTH) + " / " + dataInserimento.get(GregorianCalendar.YEAR)
				+ " - " + dataInserimento.get(GregorianCalendar.HOUR_OF_DAY) + ":"
				+ dataInserimento.get(GregorianCalendar.MINUTE);
		return temp;
	}

	private boolean partitaTrovata(ArrayList<Partita> calendarioPartite, Partita partitaCorrente) {
		trovato = false;
		Partita p;

		for (int i = 0; i < calendarioPartite.size(); i++) {
			p = calendarioPartite.get(i);

			if (p.getData().equalsIgnoreCase(partitaCorrente.getData())) {
				if ((p.getSquadraA().equalsIgnoreCase(partitaCorrente.getSquadraA()))
						|| (p.getSquadraA().equalsIgnoreCase(partitaCorrente.getSquadraB()))) {
					trovato = true;
					JOptionPane.showMessageDialog(null, "Squadra A già presente");
					break;
				} else if ((p.getSquadraB().equalsIgnoreCase(partitaCorrente.getSquadraA()))
						|| (p.getSquadraB().equalsIgnoreCase(partitaCorrente.getSquadraB()))) {
					trovato = true;
					JOptionPane.showMessageDialog(null, "Squadra B già presente");
					break;
				} else if (p.getNomeStadio().equalsIgnoreCase(partitaCorrente.getNomeStadio())) {
					trovato = true;
					JOptionPane.showMessageDialog(null, "Stadio già occupato");
					break;
				}
			}
		}

		return trovato;
	}

	private void scriviPartita(ArrayList<Partita> calendarioPartite, Partita partita) {
		calendarioPartite.add(partita);

		try {
			outCalendario = new ObjectOutputStream(new FileOutputStream("CalendarioPartite.dat"));
			outCalendario.writeObject(calendarioPartite);
			outCalendario.close();
			JOptionPane.showMessageDialog(null, "Partita inserita");
			for (Partita p : calendarioPartite) {
				System.out.println(p.getNomeStadio());
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
