package classes;

import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exceptions.CampoVuotoException;
import exceptions.UserNotFoundException;
import exceptions.UtenteGiaRegistratoException;

/**
 * La classe Struttura Sportiva contiene l'insieme dei gestori e dei clienti,
 * del calendario contenente le partite programmate, i biglietti, gli stadi
 * predefiniti e la variabile incasso per la visualizzazione del guadagno totale
 * della struttura e per singolo stadio.
 */
public class StrutturaSportiva {
	private ArrayList<Gestore> gestori;
	private ArrayList<Biglietto> biglietti;
	private ArrayList<Cliente> clienti;
	private static ArrayList<Stadio> stadi;
	private ArrayList<Partita> calendarioPartite;
	private ArrayList<Double> listaFasciaEta;

	private Comparator<Partita> ordina;

	private File fileGestori, fileClienti, fileStadi, fileCalendario, fileFasciaEta, fileBiglietti;
	private ObjectInputStream inClienti, inGestori, inStadi, inCalendario, inFasciaEta, inBiglietti;
	private ObjectOutputStream outStadi, outCalendario, outFasciaEta, outBiglietti, outClienti, outGestori;
	private GregorianCalendar oggi;
	private double incasso;

	private boolean giaPresente;

	/**
	 * Costruisce una nuova struttura i cui utenti, stadi,quadra e biglietti
	 * sono specificati dai contenuti dei relativi file.
	 * 
	 */
	public StrutturaSportiva() {
		calendarioPartite = new ArrayList<Partita>();
		listaFasciaEta = new ArrayList<Double>();
		biglietti = new ArrayList<Biglietto>();
		gestori = new ArrayList<Gestore>();
		clienti = new ArrayList<Cliente>();
		stadi = new ArrayList<Stadio>();

		fileBiglietti = new File("Biglietti.dat");
		fileGestori = new File("GestoriDb.dat");
		fileClienti = new File("ClientiDb.dat");
		fileStadi = new File("StadiDb.dat");
		fileCalendario = new File("CalendarioPartite.dat");
		fileFasciaEta = new File("FasciaEtaDB.dat");

		giaPresente = false;
		oggi = new GregorianCalendar();
		incasso = 0;
	}

	/**
	 * Tale metodo è utilizzato per tener aggiornato l'insieme dei biglietti.
	 * 
	 * @param biglietti
	 *            ArrayList di biglietti
	 */
	public void caricaBiglietti(ArrayList<Biglietto> biglietti) {
		this.biglietti = biglietti;
	}

	/**
	 * Restituisce l'incasso totale di un determinato stadio passato come
	 * parametro.
	 * 
	 * @param nomeStadio
	 *            il nome dello stadio
	 * @return incasso l'incasso dello stadio
	 */
	public String getIncassoPerStadio(String nomeStadio) {
		incasso = 0;
		for (Biglietto b : biglietti) {
			if (b.getPartita().getNomeStadio().equals(nomeStadio) && b.isAcquistato()) {
				incasso += b.getPrezzo();
			}
		}
		return "" + incasso;
	}

	/**
	 * Restituisce l'incasso totale della struttura sportiva.
	 * 
	 * @return incasso l'incasso della struttura sportiva
	 */
	public String getIncassoTotale() {
		incasso = 0;
		for (Biglietto b : biglietti) {
			if (b.isAcquistato())
				incasso += b.getPrezzo();
		}
		return "" + incasso;
	}

	/**
	 * Restituisce lo stadio filtrato per il nome passato da parametro.
	 * 
	 * @param s
	 *            il nome dello stadio
	 * @return stadio il riferimento allo stadio
	 */
	public static Stadio cercaStadi(String s) {
		for (Stadio inS : stadi) {
			if (inS.getNome().equalsIgnoreCase(s))
				return inS;
		}
		return null;
	}

	/**
	 * Restituisce l'insieme delle partite presenti nel calendario.
	 * 
	 * @return arrayList insieme delle partite in formato stringa
	 */
	public ArrayList<String> getListaPartite() {
		ArrayList<String> arrayList = new ArrayList<>();
		for (Partita p : calendarioPartite) {
			arrayList.add(p.toString());
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme dei biglietti prenotati dal cliente passato come
	 * parametro.
	 * 
	 * @param cliente
	 *            il cliente che ha prenotato il biglietto
	 * @return arrayList insieme dei biglietti prenotati dal cliente in formato
	 *         stringa
	 */
	public ArrayList<String> getBigliettiPrenotati(Cliente cliente) {
		ArrayList<String> arrayList = new ArrayList<>();
		for (Biglietto b : biglietti) {
			if (cliente.getLogin().equals(b.getCliente().getLogin()) && b.isPrenotato() && !b.isAcquistato())
				arrayList.add(b.toString());
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme dei biglietti Acquistati dal cliente passato come
	 * parametro.
	 * 
	 * @param cliente
	 *            il cliente che ha acquistato il biglietto
	 * @return arrayList insieme dei biglietti acquistati dal cliente in formato
	 *         stringa
	 */
	public ArrayList<String> getBigliettiAqcuistati(Cliente cliente) {
		ArrayList<String> arrayList = new ArrayList<>();
		for (Biglietto b : biglietti) {
			if (cliente.getLogin().equals(b.getCliente().getLogin()) && b.isAcquistato())
				arrayList.add(b.toString());
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme delle partite non ancora iniziate in modo ordinato
	 * grazie al comparatore passato come parametro, in formato stringa.
	 * 
	 * @param comparatorIn
	 *            è il comparatore utilizzato per l'ordinamento della lista
	 * @return listaPartiteNonIniziate l'insieme delle partite non iniziate in
	 *         modo ordinato
	 */
	public ArrayList<String> getListaPartiteOrdinate(Comparator<Partita> comparatorIn) {
		ordina = comparatorIn;
		calendarioPartite.sort(ordina);
		return getListaPartiteNonIniziate();
	}

	/**
	 * Restituisce l'insieme delle partite non ancora iniziate in modo ordinato
	 * grazie al comparatore passato come parametro.
	 * 
	 * @param comparatorIn
	 *            è il comparatore utilizzato per l'ordinamento della lista
	 * @return arrayList l'insieme delle partite non iniziate in modo ordinato
	 */
	public ArrayList<Partita> getListaPartiteOrdinateObj(Comparator<Partita> comparatorIn) {
		ArrayList<Partita> arrayList = new ArrayList<>();
		ordina = comparatorIn;
		calendarioPartite.sort(ordina);
		for (Partita p : calendarioPartite) {
			if (p.getDataGregorian().after(oggi)) {
				arrayList.add(p);
			}
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme di tutte le partite in modo ordinato grazie al
	 * comparatore passato come parametro.
	 * 
	 * @param comparatorIn
	 *            è il comparatore utilizzato per l'ordinamento della lista
	 * @return calendaruiPartite l'insieme di tutte le partite in modo ordinato
	 */
	public ArrayList<Partita> getListaTuttePartiteOrdinate(Comparator<Partita> comparatorIn) {
		ordina = comparatorIn;
		calendarioPartite.sort(ordina);
		return calendarioPartite;
	}

	/**
	 * Restituisce l'insieme delle partite non ancora iniziate in formato
	 * stringa.
	 * 
	 * @return arrayList l'insieme delle partite
	 */
	public ArrayList<String> getListaPartiteNonIniziate() {
		ArrayList<String> arrayList = new ArrayList<>();

		for (Partita p : calendarioPartite) {
			if (p.getDataGregorian().after(oggi)) {
				arrayList.add(p.toString());
			}
		}
		return arrayList;
	}

	/**
	 * Restituiesc l'insieme delle partite filtrate per il nome dello stadio
	 * passato come parametro.
	 * 
	 * @param nomeStadio
	 *            il nome dello stadio
	 * @return arrayList l'insieme delle partite per nome dello stadio
	 */
	public ArrayList<String> getListaPartitePerStadio(String nomeStadio) {
		ArrayList<String> arrayList = new ArrayList<>();
		for (Partita p : calendarioPartite) {
			if (p.getNomeStadio().equalsIgnoreCase(nomeStadio)) {
				arrayList.add(p.toString());
			}
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme delle partite filtrate per il numero settimana
	 * passato come parametro.
	 * 
	 * @param numSett
	 *            l'intero indentificante il numero settimana
	 * @return arrayList l'insieme delle partite per numero settimana
	 */
	public ArrayList<String> getListaPartitePerNumSettimana(int numSett) {
		ArrayList<String> arrayList = new ArrayList<>();
		for (Partita p : calendarioPartite) {
			if (p.getDataGregorian().get(Calendar.WEEK_OF_YEAR) == numSett) {
				arrayList.add(p.toString());
			}
		}
		return arrayList;
	}

	/**
	 * Restituisce l'insieme dei nomi degli stadi passato come parametro
	 * 
	 * @param stadi
	 *            arrayList di stadi
	 * @return array insieme degli stadi
	 */
	public String[] getNomeElencoStadi() {
		String[] array = new String[stadi.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = "" + stadi.get(i).getNome();
		}
		return array;
	}

	/**
	 * Aggiunge uno stadio alla lista di stadi.
	 */
	private void inizializzaStadi() {
		stadi = new ArrayList<Stadio>();
		stadi.add(new Stadio("Juventus stadium", "150"));
		stadi.add(new Stadio("San Paolo", "100"));
		stadi.add(new Stadio("Olimpico", "140"));
		stadi.add(new Stadio("G.Meazza", "125"));
		stadi.add(new Stadio("L.Ferraris", "100"));
		stadi.add(new Stadio("Friuli", "160"));
		stadi.add(new Stadio("A.Braglia", "130"));
		stadi.add(new Stadio("Sant'Elia", "100"));
		stadi.add(new Stadio("R.Barbera", "100"));
		stadi.add(new Stadio("M.Bentegodi", "100"));
	}

	/**
	 * Aggiorna il file fileStadi.
	 */
	public void aggiornaFileStadio() {
		System.out.println("sto capendo gli stadi");
		if (!fileStadi.exists()) {
			System.out.println("il file non esiste");
			try {
				inizializzaStadi();
				outStadi = new ObjectOutputStream(new FileOutputStream(fileStadi));
				outStadi.writeObject(stadi);
				System.out.println(stadi.size());
				outStadi.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (fileStadi.exists()) {
			System.out.println("il file esiste");
			try {
				inStadi = new ObjectInputStream(new FileInputStream(fileStadi));
				stadi = (ArrayList<Stadio>) inStadi.readObject();
				inStadi.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Aggiorna il file fileBiglietti.
	 */
	public void aggiornaFileBiglietti() {
		if (!fileBiglietti.exists()) {
			try {
				outBiglietti = new ObjectOutputStream(new FileOutputStream(fileBiglietti));
				outBiglietti.writeObject(biglietti);
				outBiglietti.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (fileBiglietti.exists()) {
			try {

				inBiglietti = new ObjectInputStream(new FileInputStream(fileBiglietti));
				biglietti = (ArrayList<Biglietto>) inBiglietti.readObject();
				inBiglietti.close();
				System.out.println("File biglietti caricato");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Aggiorna il file fileClendarioPartite.
	 */
	public void aggiornaFileCalendarioPartite() {
		if (!fileCalendario.exists()) {
			try {
				outCalendario = new ObjectOutputStream(new FileOutputStream(fileCalendario));
				outCalendario.writeObject(calendarioPartite);
				outCalendario.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (fileCalendario.exists()) {
			try {

				inCalendario = new ObjectInputStream(new FileInputStream(fileCalendario));
				calendarioPartite = (ArrayList<Partita>) inCalendario.readObject();
				inCalendario.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Aggiorna il file fileFasciaEta.
	 */
	public void aggiornaFileFasciaEta() {
		if (!fileFasciaEta.exists()) {

			listaFasciaEta.add(0, (double) 0);
			listaFasciaEta.add(1, (double) 0);
			listaFasciaEta.add(2, (double) 0);

			try {
				outFasciaEta = new ObjectOutputStream(new FileOutputStream(fileFasciaEta));
				outFasciaEta.writeObject(listaFasciaEta);
				outFasciaEta.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (fileFasciaEta.exists()) {
			try {

				inFasciaEta = new ObjectInputStream(new FileInputStream(fileFasciaEta));
				listaFasciaEta = (ArrayList<Double>) inFasciaEta.readObject();
				inFasciaEta.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * Aggiorna il file fileClienti.
	 * 
	 * @param nome
	 *            nome del cliente
	 * @param cognome
	 *            cognome del cliente
	 * @param login
	 *            login del cliente
	 * @param password
	 *            password cliente
	 * @param eta
	 *            età del cliente
	 */
	public void aggiornaFileClienti(String nome, String cognome, String login, String password, String eta) {
		if (fileClienti.exists()) {

			if (nome.equals("") || cognome.equals("") || login.equals("") || password.equals(""))
				try {
					throw new CampoVuotoException();
				} catch (CampoVuotoException e2) {
					JOptionPane.showMessageDialog(null, "Riempire correttamente tutti i campi");
				}
			else {
				try {
					ArrayList<Gestore> gestori = new ArrayList<>();
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileClienti));
					ObjectInputStream inGestore = new ObjectInputStream(new FileInputStream(new File("GestoriDB.dat")));
					clienti = (ArrayList<Cliente>) in.readObject();
					gestori = (ArrayList<Gestore>) inGestore.readObject();

					for (Cliente temp : clienti) {
						if (temp.getLogin().equalsIgnoreCase(login))
							throw new UtenteGiaRegistratoException();
					}

					for (Gestore temp : gestori) {
						if (temp.getLogin().equalsIgnoreCase(login))
							throw new UtenteGiaRegistratoException();
					}

					in.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (UtenteGiaRegistratoException e1) {
					JOptionPane.showMessageDialog(null, "Utente già presente nel database");
					giaPresente = true;
					e1.printStackTrace();
				}

				try {
					if (giaPresente == false) {

						clienti.add(new Cliente(nome, cognome, login, password, Integer.parseInt(eta)));

						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileClienti));
						out.writeObject(clienti);
						out.close();
						JOptionPane.showMessageDialog(null, "Utente inserito nel database correttamente");
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Riempire i campi del cliente in modo corretto");
				}
			}
		}
	}

	/**
	 * Aggiorna il file fileGestori.
	 * 
	 * @param nome
	 *            nome del gestore
	 * @param cognome
	 *            cognome del gestore
	 * @param login
	 *            login del gestore
	 * @param passwordpassword
	 *            del gestore
	 */
	public void aggiornaFileGestori(String nome, String cognome, String login, String password) {
		if (fileGestori.exists()) {

			if (nome.equals("") || cognome.equals("") || login.equals("") || password.equals(""))
				try {
					throw new CampoVuotoException();
				} catch (CampoVuotoException e2) {
					JOptionPane.showMessageDialog(null, "Per favore, completa tutti i campi");
				}
			else {

				try {
					ArrayList<Cliente> clienti = new ArrayList<>();
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileGestori));
					ObjectInputStream inCliente = new ObjectInputStream(new FileInputStream(new File("ClientiDB.dat")));
					clienti = (ArrayList<Cliente>) inCliente.readObject();
					gestori = (ArrayList<Gestore>) in.readObject();

					for (Gestore temp : gestori) {
						if (temp.getLogin().equals(login))
							throw new UtenteGiaRegistratoException();
					}

					for (Cliente temp : clienti) {
						if (temp.getLogin().equals(login))
							throw new UtenteGiaRegistratoException();
					}

					in.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (UtenteGiaRegistratoException e1) {
					JOptionPane.showMessageDialog(null, "Gestore già presente nel database");
					giaPresente = true;
					e1.printStackTrace();
				}

				try {
					if (giaPresente == false) {

						gestori.add(new Gestore(nome, cognome, login, password));

						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileGestori));
						out.writeObject(gestori);
						out.close();
						JOptionPane.showMessageDialog(null, "Gestore inserito nel database correttamente");
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * Salvataggio di tutti i file.
	 */
	public void salvaFile() {
		try {
			outFasciaEta = new ObjectOutputStream(new FileOutputStream(fileFasciaEta));
			outStadi = new ObjectOutputStream(new FileOutputStream(fileStadi));
			outCalendario = new ObjectOutputStream(new FileOutputStream(fileCalendario));
			outBiglietti = new ObjectOutputStream(new FileOutputStream(fileBiglietti));

			outStadi.writeObject(stadi);
			outCalendario.writeObject(calendarioPartite);
			outFasciaEta.writeObject(listaFasciaEta);
			outBiglietti.writeObject(biglietti);

			outCalendario.close();
			outStadi.close();
			outFasciaEta.close();
			outBiglietti.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Controlla se esiste il file, altrimenti lo crea.
	 */
	public void controlloPreRegistrazione() {
		try {
			if (!fileGestori.exists()) {
				ObjectOutputStream outGestore = new ObjectOutputStream(new FileOutputStream(fileGestori));
				outGestore.writeObject(gestori);
				outGestore.close();
			}
			if (!fileClienti.exists()) {
				ObjectOutputStream outClienti = new ObjectOutputStream(new FileOutputStream(fileClienti));
				outClienti.writeObject(clienti);
				outClienti.close();
			}
			inGestori = new ObjectInputStream(new FileInputStream(fileGestori));
			inClienti = new ObjectInputStream(new FileInputStream(fileClienti));
			gestori = (ArrayList<Gestore>) inGestori.readObject();
			clienti = (ArrayList<Cliente>) inClienti.readObject();
			inGestori.close();
			inClienti.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Restituisce la lista delle partite presenti nel calendario.
	 * 
	 * @return calendarioPartite
	 */
	public ArrayList<Partita> getCalendarioPartite() {
		return calendarioPartite;
	}

	/**
	 * Restituisce la lista dei biglietti.
	 * 
	 * @return biglietti
	 */
	public ArrayList<Biglietto> getBiglietti() {
		return biglietti;
	}

	/**
	 * Restituiesce la lista degli stadi.
	 * 
	 * @return stadi
	 */
	public static ArrayList<Stadio> getStadi() {
		return stadi;
	}

	/**
	 * Restituisce la lista dei gestori.
	 * 
	 * @return gestori
	 */
	public ArrayList<Gestore> getGestori() {
		return gestori;
	}

	/**
	 * Restituisce la lista dei clienti.
	 * 
	 * @return clienti
	 */
	public ArrayList<Cliente> getClienti() {
		return clienti;
	}

	/**
	 * Restituisce la lista della fascia età.
	 * 
	 * @return listaFasciEta
	 */
	public ArrayList<Double> getFasciaEta() {
		return listaFasciaEta;
	}

	/**
	 * Restituisce il miglior prezzo per stadio, per partita, per fascia età,
	 * del biglietto dopo aver calcolato gli sconti sul prezzo base.
	 * 
	 * @param partite
	 *            lista delle partite inserite
	 * @param index
	 *            indice della partita
	 * @param user
	 *            cliente che acquista il biglietto
	 * @return il minimo prezzo calcolato
	 */
	public double getMigliorPrezzo(ArrayList<Partita> partite, int index, Cliente user) {

		double percentualePartita = partite.get(index).getPercentualeSconto();
		double percentualeStadio = stadi.get(trovaStadio(partite.get(index).getStadio().getNome()))
				.getPercentualeSconto();
		double percentualeEta = listaFasciaEta.get(user.getTipologiaUtente());

		double prezzoBase = partite.get(index).getStadio().getPrezzo();
		double migliorePrezzoPerStadio = prezzoBase - ((prezzoBase / 100) * percentualeStadio);
		double migliorePrezzoPerPartita = prezzoBase - ((prezzoBase / 100) * percentualePartita);
		double migliorePrezzoPerEta = prezzoBase - ((prezzoBase / 100) * percentualeEta);

		return getMinimo(migliorePrezzoPerStadio, migliorePrezzoPerPartita, migliorePrezzoPerEta);
	}

	/**
	 * Restituisce il minimo tra tre prezzi.
	 * 
	 * @param primo
	 * @param secondo
	 * @param terzo
	 * @return minimo
	 */
	private double getMinimo(double primo, double secondo, double terzo) {
		double minimo;
		if (primo < secondo) {
			if (primo < terzo)
				minimo = primo;
			else
				minimo = terzo;
		} else {
			if (secondo < terzo)
				minimo = secondo;
			else
				minimo = terzo;
		}
		return minimo;
	}

	/**
	 * Restituisce l'indice dello stadio con lo stesso nome passato da
	 * parametro.
	 * 
	 * @param nomeStadio
	 *            nome dello stadio
	 * @return indice dello stadio nella lista degli stadi
	 */
	private int trovaStadio(String nomeStadio) {

		for (int i = 0; i < stadi.size(); i++) {
			if (stadi.get(i).getNome().equals(nomeStadio))
				return i;
		}

		return -1;
	}

	/**
	 * Restituisce l'indice del biglietto associato ad un posto ed una partita
	 * passato come parametro.
	 * 
	 * @param partita
	 * @param numeroPosto
	 * @return indice biglietto
	 */
	public int trovaBiglietto(Partita partita, int numeroPosto) {

		for (int i = 0; i < biglietti.size(); i++) {
			if (biglietti.get(i).getPartita().getNomeStadio().equals(partita.getNomeStadio())
					&& biglietti.get(i).getCodicePosto() == numeroPosto)
				return i;
		}

		return -1;
	}

	/**
	 * Restituisce l'indice del biglietto associato ad un posto, una partita e
	 * un acquirente passato come parametro.
	 * 
	 * @param partita
	 * @param numeroPosto
	 * @return indice biglietto
	 */
	public int trovaBiglietto(Cliente cliente, Partita partita, int numeroPosto) {

		for (int i = 0; i < biglietti.size(); i++) {
			if (biglietti.get(i).getCliente().getLogin().equals(cliente.getLogin())
					&& biglietti.get(i).getPartita().getNomeStadio().equals(partita.getNomeStadio())
					&& biglietti.get(i).getCodicePosto() == numeroPosto)
				return i;
		}

		return -1;
	}

	/**
	 * Cerca un utente in base al login e la password passata da parametro.
	 * 
	 * @param log
	 *            login dell'utente
	 * @param pass
	 *            password dell'utente
	 * @return Object castato al tipo dell'utente trovato
	 */
	public Object cercaUtente(String log, String pass) {
		try {
			boolean trovato = false;

			for (Cliente user : clienti) {
				if (user.getLogin().equalsIgnoreCase(log) && user.getPassword().equals(pass)) {
					trovato = true;
					return (Cliente) user;
				}
			}

			for (Gestore user : gestori) {
				if (user.getLogin().equalsIgnoreCase(log) && user.getPassword().equals(pass)) {
					trovato = true;
					return (Gestore) user;
				}
			}

			if (!trovato)
				throw new UserNotFoundException();
		} catch (UserNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "utente non trovato");
			ex.printStackTrace();
		}
		return null;
	}

}
