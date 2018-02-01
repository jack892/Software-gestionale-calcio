package graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import adapters.MouseListenerAdapter;
import classes.Biglietto;
import classes.Cliente;
import classes.Partita;
import classes.Stadio;
import classes.StrutturaSportiva;
import exceptions.PostoIndisponibileException;

public class FrameDisegnoStadio extends JFrame {

	private int index;
	private ArrayList<Partita> partite;
	private StrutturaSportiva struttura;
	private StadioComponent stadioComp;
	private JPanel pannelloNord, pannelloSud, pannelloEst, pannelloOvest;
	private MouseListener myMouseListener;
	private Cliente user;
	private int i;

	public FrameDisegnoStadio(int indice, ArrayList<Partita> partiteIn, Cliente user, StrutturaSportiva struttura) {
		this.struttura = struttura;
		this.user = user;
		myMouseListener = new MouseListenerAdapter();
		stadioComp = new StadioComponent();
		index = indice;
		// array di partite ordinate secondo la selezione nella checkbox
		partite = partiteIn;
		setTitle("Stadio: " + partite.get(index).getNomeStadio() + ", Evento: " + partite.get(index).getSquadraA()
				+ " vs. " + partite.get(index).getSquadraB() + " il " + partite.get(index).getData() + " alle "
				+ partite.get(index).getOra() + ":" + partite.get(index).getMinuti() + ", Posti totali: "
				+ partite.get(index).getStadio().getCapienza());

		setSize(1200, 700);
		setResizable(false);

		add(setPannelloEst(), BorderLayout.EAST);
		add(setPannelloOvest(), BorderLayout.WEST);
		add(setPannelloNord(), BorderLayout.NORTH);
		add(setPannelloSud(), BorderLayout.SOUTH);

		generaPosti(partite, index);

		add(setPannelloCentro(stadioComp), BorderLayout.CENTER);

		setDefaultCloseOperation(this.HIDE_ON_CLOSE);

		setVisible(true);

	}

	private void generaPosti(ArrayList<Partita> partite, int index) {

		int indiceTemp = Integer.parseInt(partite.get(index).getStadio().getCapienza()) / 4;
		double sum = Integer.parseInt(partite.get(index).getStadio().getCapienza()) % 4;
		for (i = 1; i <= indiceTemp; i++) {

			JButton buttonNord = new JButton("" + i);
			buttonNord.setPreferredSize(new Dimension(20, 20));

			buttonNord.setMinimumSize(new Dimension(10, 10));

			buttonNord.setMaximumSize(new Dimension(10, 10));

			buttonNord.setSize(new Dimension(10, 10));
			inizializzaPosti(buttonNord);

			buttonNord.addMouseListener(new MouseListenerAdapter() {
				public void mousePressed(MouseEvent e) {

					aggiungiEvento(buttonNord);

				}
			});
			pannelloNord.add(buttonNord);

		}

		for (int j = (indiceTemp + 1); j < (indiceTemp * 2) + 1; j++) {
			JButton buttonOvest = new JButton("" + j);

			buttonOvest.setPreferredSize(new Dimension(20, 20));

			buttonOvest.setMinimumSize(new Dimension(10, 10));

			buttonOvest.setMaximumSize(new Dimension(10, 10));

			buttonOvest.setSize(new Dimension(10, 10));
			inizializzaPosti(buttonOvest);

			buttonOvest.addMouseListener(new MouseListenerAdapter() {
				public void mousePressed(MouseEvent e) {

					aggiungiEvento(buttonOvest);

				}
			});
			pannelloOvest.add(buttonOvest);

		}

		for (int k = ((indiceTemp * 2) + 1); k < (indiceTemp * 3) + 1; k++) {
			JButton buttonEst = new JButton("" + k);

			buttonEst.setPreferredSize(new Dimension(20, 20));

			buttonEst.setMinimumSize(new Dimension(10, 10));

			buttonEst.setMaximumSize(new Dimension(10, 10));

			buttonEst.setSize(new Dimension(10, 10));
			inizializzaPosti(buttonEst);

			buttonEst.addMouseListener(new MouseListenerAdapter() {
				public void mousePressed(MouseEvent e) {

					aggiungiEvento(buttonEst);

				}
			});
			pannelloEst.add(buttonEst);
		}

		for (int l = (indiceTemp * 3) + 1; l < ((indiceTemp * 4) + 1) + sum; l++) {
			JButton buttonSud = new JButton("" + l);

			buttonSud.setPreferredSize(new Dimension(20, 20));

			buttonSud.setMinimumSize(new Dimension(10, 10));

			buttonSud.setMaximumSize(new Dimension(10, 10));

			buttonSud.setSize(new Dimension(10, 10));
			inizializzaPosti(buttonSud);

			buttonSud.addMouseListener(new MouseListenerAdapter() {
				public void mousePressed(MouseEvent e) {

					aggiungiEvento(buttonSud);

				}
			});
			pannelloSud.add(buttonSud);

		}

	}

	private StadioComponent setPannelloCentro(StadioComponent stadioComp) {
		return stadioComp;
	}

	private JPanel setPannelloNord() {
		pannelloNord = new JPanel();
		pannelloNord.setLayout(new GridLayout(10, 10));
		return pannelloNord;
	}

	private JPanel setPannelloEst() {
		pannelloEst = new JPanel();
		pannelloEst.setLayout(new GridLayout(16, 16));
		pannelloEst.setPreferredSize(new Dimension(350, 350));
		return pannelloEst;
	}

	private JPanel setPannelloOvest() {
		pannelloOvest = new JPanel();
		pannelloOvest.setLayout(new GridLayout(16, 16));
		pannelloOvest.setPreferredSize(new Dimension(350, 350));
		return pannelloOvest;
	}

	private JPanel setPannelloSud() {
		pannelloSud = new JPanel();
		pannelloSud.setLayout(new GridLayout(10, 10));
		return pannelloSud;
	}

	// aggiunge l'evento al bottone passato per parametro e controlla se è
	// acquistabile,prenotabile, fa tutto ecc ecc ecc
	private void aggiungiEvento(JButton button) {

		int indiceBottone = Integer.parseInt(button.getText());
		int numeroPosto = Integer.parseInt(button.getText());

		if (button.getBackground().equals(Color.RED)) {
			JOptionPane.showMessageDialog(null, "Posto già acquistato");
		} else if (button.getBackground().equals(Color.YELLOW)) {

			int indiceBiglietto = struttura.trovaBiglietto(user, partite.get(index), numeroPosto);
			System.out.println("Indice biglietto = " + indiceBiglietto);

			if (indiceBiglietto != -1) {

				Object[] options = { "Acquista", "Rimuovi prenotazione", "Annulla" };
				int scelta = JOptionPane.showOptionDialog(null, "Acquista o rimuovi prenotazione.",
						"Gestione prenotazione", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[2]);

				if (scelta == 0) {

					try {

						if (struttura.getBiglietti().get(indiceBiglietto).eAcquistabile()) {
							struttura.getBiglietti().get(indiceBiglietto).setAcquistato(true);
							button.setBackground(Color.RED);
							button.setEnabled(false);
						}

					} catch (PostoIndisponibileException e1) {
						JOptionPane.showMessageDialog(null, "Acquistare 2 ore prima dell'evento");
						e1.printStackTrace();
					}
				} else if (scelta == 1) {
					System.out.println("Rimuovi prenotazione");
					struttura.getBiglietti().remove(indiceBiglietto);
					button.setBackground(Color.GREEN);
				} else if (scelta == 2) {
					System.out.println("annulla");
				}

			} else {
				try {
					struttura.getBiglietti().get(struttura.trovaBiglietto(partite.get(index), numeroPosto))
							.setPrenotato(true);
				} catch (PostoIndisponibileException e) {
					JOptionPane.showMessageDialog(null, "Biglietto già prenotato da un altro cliente");
					e.printStackTrace();
				}
			}

		} else {

			double migliorePrezzo = Math.round(struttura.getMigliorPrezzo(partite, index, user) * 100.0) / 100.0;

			Object[] options = { "Acquista", "Prenota", "Annulla" };
			int scelta = JOptionPane.showOptionDialog(null,
					"Il biglietto costa " + migliorePrezzo + "€ - Vuoi acquistarlo o prenotarlo?", "Opzione acquisto",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			if (scelta == 0) {
				try {
					Biglietto bigl = new Biglietto(partite.get(index), migliorePrezzo, indiceBottone, user);
					if (bigl.eAcquistabile()) {
						bigl.setAcquistato(true);
						struttura.getBiglietti().add(bigl);
						button.setBackground(Color.RED);
						button.setEnabled(false);
					}
				} catch (PostoIndisponibileException e1) {
					JOptionPane.showMessageDialog(null, "Acquistare il biglietto 2 ore prima dell'evento");
					e1.printStackTrace();
				}
			} else if (scelta == 1) {
				try {
					Biglietto bigl = new Biglietto(partite.get(index), migliorePrezzo, indiceBottone, user);
					if (bigl.ePrenotabile()) {
						bigl.setPrenotato(true);
						struttura.getBiglietti().add(bigl);
						button.setBackground(Color.YELLOW);
					}
				} catch (PostoIndisponibileException e1) {
					JOptionPane.showMessageDialog(null, "Prenotare il biglietto 12 ore prima dell'evento");
					e1.printStackTrace();
				}
			}

		}
	}

	// inizializza i posti in base al fatto se il biglietto c'è, è prenotato ,
	// comprato o niente
	private void inizializzaPosti(JButton buttonNord) {

		// FUNZIONA

		buttonNord.setBackground(Color.GREEN);

		for (Biglietto b : struttura.getBiglietti()) {
			if (b.getPartita().getStadio().getNome().equals(partite.get(index).getStadio().getNome())
					&& b.getCodicePosto() == Integer.parseInt(buttonNord.getText())
					&& (b.getPartita().getSquadraA().equals(partite.get(index).getSquadraA()))
					&& (b.getPartita().getSquadraB().equals(partite.get(index).getSquadraB()))
					&& (b.getPartita().getDataGregorian().equals(partite.get(index).getDataGregorian()))) {
				if (b.isAcquistato()) {
					buttonNord.setBackground(Color.RED);
					buttonNord.setEnabled(false);
				} else if (b.isPrenotato()) {
					buttonNord.setBackground(Color.YELLOW);
				}
			}
		}
	}

}
