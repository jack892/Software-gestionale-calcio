package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import classes.Biglietto;
import classes.Cliente;
import classes.Partita;
import classes.Stadio;
import classes.StrutturaSportiva;
import comparators.OrdinamentoDataDesc;
import comparators.OrdinamentoNomeSquadre;
import comparators.OrdinamentoNomeStadioCresc;

/**
 * Permette di utilizzare le varie funzionalità concesse a un cliente.
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 * 
 */

public class FrameBenvenutoCliente extends JFrame {
	private String nomeUtente;
	private JComboBox<String> comboStadi, comboPartite;
	private JComboBox<Partita> comboPartite2;
	private JPanel pannelloNord, pannelloCentro;
	private JPanel pannelloVisualizzaGenerico;
	private StrutturaSportiva struttura;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private Cliente user;

	/**
	 * Grafica che permette di utilizzare le varie funzionalità concesse a un
	 * cliente.
	 * 
	 * @param user
	 *            cliente
	 * @param struttura
	 *            struttura sportiva
	 */
	public FrameBenvenutoCliente(Cliente user, StrutturaSportiva struttura) {

		this.user = user;
		this.struttura = struttura;
		nomeUtente = user.getNome();
		pannelloVisualizzaGenerico = new JPanel();
		textArea = new JTextArea(15, 50);
		setTitle("Gestione Cliente");
		setSize(900, 400);
		setJMenuBar(getBarraMenu());
		add(setPannelloNord(), BorderLayout.NORTH);
		add(setPannelloCentro(), BorderLayout.CENTER);

		setVisible(false);
	}

	private JMenuBar getBarraMenu() {
		JMenuBar menu = new JMenuBar();
		menu.add(setJMenuMenu());
		return menu;
	}

	private JMenu setJMenuMenu() {
		JMenu menu = new JMenu("Menu");
		menu.add(setMenuVisualizzaTutte());
		menu.add(setMenuVisualizzaNonIniziate());
		menu.add(setMenuAcquistaPrenota());
		menu.add(setMenuVisualizzaAcquistatiPrenotati());
		menu.addSeparator();
		menu.add(setJMenuEsci());
		return menu;
	}

	private JMenuItem setMenuVisualizzaAcquistatiPrenotati() {
		JMenuItem menuItem = new JMenuItem("Visualizza biglietti acquistati/prenotati");
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.removeAll();
				addPannelloVisualizzaPrenotazioniAcquisti();
				pannelloVisualizzaGenerico.revalidate();
			}
		});
		return menuItem;
	}

	private JMenuItem setMenuAcquistaPrenota() {
		pannelloVisualizzaGenerico.setLayout(new FlowLayout());
		JMenuItem menuItem = new JMenuItem("Acquista e/o compra biglietti");
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.removeAll();
				addPannelloVisualizzaComboPartite();
				pannelloVisualizzaGenerico.revalidate();
			}
		});
		return menuItem;
	}

	private JMenuItem setMenuVisualizzaNonIniziate() {
		pannelloVisualizzaGenerico.setLayout(new FlowLayout());
		JMenuItem menuItem = new JMenuItem("Visualizza partite non ancora iniziate");
		JRadioButton ordinaCrono = new JRadioButton("Ordina cronologicamente");
		JRadioButton ordinaCrescStadio = new JRadioButton("Ordina per nome stadio");
		JRadioButton ordinaLessSquadre = new JRadioButton("Ordina per nome squadre");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(ordinaCrono);
		gruppo.add(ordinaCrescStadio);
		gruppo.add(ordinaLessSquadre);
		JPanel pannelloRadio = new JPanel();
		pannelloRadio.add(ordinaCrono);
		pannelloRadio.add(ordinaCrescStadio);
		pannelloRadio.add(ordinaLessSquadre);
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.removeAll();
				pannelloVisualizzaGenerico.add(pannelloRadio);
				ordinaCrono.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						addPannelloVisualizzaTutte(
								visualizzaTutteArray(struttura.getListaPartiteOrdinate(new OrdinamentoDataDesc())));
						revalidate();
					}
				});
				ordinaCrescStadio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						addPannelloVisualizzaTutte(visualizzaTutteArray(
								struttura.getListaPartiteOrdinate(new OrdinamentoNomeStadioCresc())));
						revalidate();
					}
				});

				ordinaLessSquadre.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						addPannelloVisualizzaTutte(
								visualizzaTutteArray(struttura.getListaPartiteOrdinate(new OrdinamentoNomeSquadre())));
						revalidate();
					}
				});

				pannelloVisualizzaGenerico.revalidate();
			}
		});
		return menuItem;
	}

	private JMenu setMenuVisualizzaTutte() {
		pannelloVisualizzaGenerico.setLayout(new FlowLayout());
		JMenu visualizzaTutti = new JMenu("Visualizza tutte le partite");
		JMenuItem visualizzaPerStadio = new JMenuItem("Per stadio");
		JMenuItem visualizzaPerSettimana = new JMenuItem("Per numero di settimana");

		visualizzaTutti.add(visualizzaPerStadio);
		visualizzaTutti.add(visualizzaPerSettimana);

		visualizzaPerStadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.removeAll();
				addPannelloVisualizzaComboStadi();
				addPannelloVisualizzaTutte(visualizzaTutteArray(struttura.getListaPartite()));
				pannelloVisualizzaGenerico.revalidate();
			}
		});

		visualizzaPerSettimana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.removeAll();
				addPannelloVisualizzaSettimana();
				addPannelloVisualizzaTutte(visualizzaTutteArray(struttura.getListaPartite()));
				pannelloVisualizzaGenerico.revalidate();

			}
		});

		return visualizzaTutti;
	}

	private JMenuItem setJMenuEsci() {
		JMenuItem esci = new JMenuItem("Esci");
		esci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
		return esci;
	}

	private JPanel setPannelloNord() {
		pannelloNord = new JPanel();
		pannelloNord.add(new JLabel("Benvenuto " + nomeUtente.toUpperCase()));
		return pannelloNord;
	}

	private JPanel setPannelloCentro() {
		pannelloCentro = new JPanel();
		pannelloCentro.add(pannelloVisualizzaGenerico);
		return pannelloCentro;
	}

	private void addPannelloVisualizzaComboStadi() {
		pannelloVisualizzaGenerico.setLayout(new FlowLayout());
		comboStadi = new JComboBox<>(struttura.getNomeElencoStadi());
		pannelloVisualizzaGenerico.add(comboStadi, BorderLayout.EAST);

		comboStadi.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				textArea.setText("");
				textArea = visualizzaTutteArray(
						struttura.getListaPartitePerStadio((String) comboStadi.getSelectedItem()));
			}
		});
	}

	private void addPannelloVisualizzaComboPartite() {
		ArrayList<Partita> temp = struttura.getListaPartiteOrdinateObj(new OrdinamentoDataDesc());
		comboPartite = new JComboBox<>();
		for (Partita p : temp) {
			comboPartite.addItem(p.toString());
		}
		comboPartite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameDisegnoStadio(comboPartite.getSelectedIndex(), temp, user, struttura);
			}
		});
		pannelloVisualizzaGenerico.add(new JLabel("Scegli la partita: "));
		pannelloVisualizzaGenerico.add(comboPartite, BorderLayout.CENTER);

	}

	private void addPannelloVisualizzaSettimana() {
		pannelloVisualizzaGenerico.setLayout(new FlowLayout());
		JLabel labelInserisciSettimana = new JLabel("Inserisci il numero della settimana");
		JTextField textFieldNumeroSett = new JTextField(3);
		JButton buttonOk = new JButton("ok");
		buttonOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textFieldNumeroSett.getText().equals("")) {
					textArea.setText("");
					textArea = visualizzaTutteArray(struttura.getListaPartite());
				} else {
					int numSett = Integer.parseInt(textFieldNumeroSett.getText());
					textArea.setText("");
					textArea = visualizzaTutteArray(struttura.getListaPartitePerNumSettimana(numSett));
				}
			}
		});
		JPanel pannelloTemp = new JPanel();
		pannelloTemp.setLayout(new GridLayout(3, 2));
		pannelloTemp.add(labelInserisciSettimana);
		pannelloTemp.add(textFieldNumeroSett);
		pannelloTemp.add(buttonOk);
		pannelloVisualizzaGenerico.add(pannelloTemp, BorderLayout.EAST);
	}

	private void addPannelloVisualizzaPrenotazioniAcquisti() {
		JPanel pannelloTemp = new JPanel();
		// struttura.caricaBiglietti(biglietti);
		JLabel labelVisualizza = new JLabel("Visualizza biglietti");
		JRadioButton radioPrenotati = new JRadioButton("prenotati");
		JRadioButton radioAcquistati = new JRadioButton("acquistati");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(radioPrenotati);
		gruppo.add(radioAcquistati);
		pannelloTemp.add(labelVisualizza);
		pannelloTemp.add(radioPrenotati);
		pannelloTemp.add(radioAcquistati);
		pannelloVisualizzaGenerico.setLayout(new GridLayout(2, 1));
		radioAcquistati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.add(visualizzaTutteArray(struttura.getBigliettiAqcuistati(user)));
				pannelloVisualizzaGenerico.revalidate();

			}
		});

		radioPrenotati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pannelloVisualizzaGenerico.add(visualizzaTutteArray(struttura.getBigliettiPrenotati(user)));
				pannelloVisualizzaGenerico.revalidate();

			}
		});
		pannelloVisualizzaGenerico.add(pannelloTemp);
	}

	private JTextArea visualizzaTutteArray(ArrayList<String> arrayList) {
		textArea.setText("");
		textArea.setEditable(false);
		for (String s : arrayList) {
			textArea.append(s + "\n");
		}
		return textArea;
	}

	private void addPannelloVisualizzaTutte(JTextArea textArea) {
		// JScrollPane scrollPane = new JScrollPane(textArea);
		pannelloVisualizzaGenerico.add(textArea);
	}

}
