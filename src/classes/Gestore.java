package classes;

import java.io.Serializable;

/**
 * La classe gestore rappresenta il curatore di una struttura sportiva, ha un
 * nome, cognome, login e password.Essa implementa Serializable per
 * memorizzazione in un flusso.
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 *
 */
public class Gestore implements Serializable {
	private String nome;
	private String cognome;
	private String login;
	private String password;

	/**
	 * Costruisce un Gestore di una struttura sportiva.
	 * 
	 * @param nome
	 *            il nome del gestore
	 * @param cognome
	 *            il cognome del gestore
	 * @param login
	 *            il login del gestore
	 * @param password
	 *            la password del gestore
	 */
	public Gestore(String nome, String cognome, String login, String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.login = login;
		this.password = password;
	}

	/**
	 * Restituisce una stringa rappresentante il nome del gestore.
	 * 
	 * @return nome del gestore
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce una stringa rappresentante il cognome del gestore.
	 * 
	 * @return cognome del gestore
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Restituisce una stringa rappresentante il login del gestore.
	 * 
	 * @return login del gestore
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Restituisce una stringa rappresentante la password del gestore
	 * 
	 * @return password del gestore
	 */
	public String getPassword() {
		return password;
	}

	public String toString() {
		return getClass().getSimpleName() + " [Nome: " + this.nome + ", Cognome: " + this.cognome + ", Login: "
				+ this.login + ", Pass: " + this.password + " ]";
	}

}
