package classes;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * La classe Cliente rappresenta il cliente di una struttura sportiva, ha il
 * nome, cognome, login, password ed età.Implementa
 * Serializable per la memorizzazione in un flusso.
 */
public class Cliente extends Gestore implements Serializable {
	private int eta;
	private int tipologiaUtente;

	/**
	 * Crea un oggetto del tipo della classe cliente di una struttura sportiva
	 * ereditando le credenziali del gestore, e aggiungendo a queste l'età.
	 * 
	 * @param nome
	 *            il nome del cliente
	 * @param cognome
	 *            il cognome del cliente
	 * @param log
	 *            il login del cliente
	 * @param pass
	 *            la password del cliente
	 * @param eta
	 *            l'età del cliente
	 */
	public Cliente(String nome, String cognome, String log, String pass, int eta) {
		super(nome, cognome, log, pass);
		if (eta <= 0) {
			this.eta = Integer.parseInt("-");
		} else
			this.eta = eta;
		assegnaTipologia(eta);
	}

	/**
	 * Restituisce un intero rappresentante l'età del cliente.
	 * 
	 * @return età età del cliente
	 */
	public int getEta() {
		return eta;
	}

	/**
	 * Assegna al cliente una categoria 0,1 o 2 in base alla sua età.
	 * 
	 * @param eta
	 *            età del cliente
	 */
	private void assegnaTipologia(int eta) {
		if (eta < 18) {
			tipologiaUtente = 0;
		} else if (eta >= 18 && eta <= 60) {
			tipologiaUtente = 1;
		} else if (eta > 60)
			tipologiaUtente = 2;
	}

	/**
	 * Restituisce la categoria del cliente assegnatagli.
	 * 
	 * @return tipologia dell'utente
	 */
	public int getTipologiaUtente() {
		return tipologiaUtente;
	}

	public String toString() {
		return super.toString() + " [eta: " + this.eta + " ]";
	}

}
