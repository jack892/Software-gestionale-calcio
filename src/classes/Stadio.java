package classes;

import java.io.Serializable;
import java.util.ArrayList;

import interfaces.Scontabile;

/**
 * La classe Stadio rappresenta lo stadio in cui viene giocata una partita.
 * Implementa due interfacce, Serializable per la memorizzazione in un flusso e
 * Scontabile per applicare sconti allo stadio.
 *
 */
public class Stadio implements Serializable, Scontabile {
	private String nome;
	private String capienza;
	private double prezzoBiglietto;
	private double percentualeSconto;

	/**
	 * Crea un oggetto del tipo della classe Stadio, assegnando un valore
	 * predefinito al prezzo del biglietto e alla percentuale di sconto.
	 * 
	 * @param nome
	 *            il nome dello stadio
	 * @param capienza
	 *            la capienza dello stadio
	 */
	public Stadio(String nome, String capienza) {
		this.nome = nome;
		this.capienza = capienza;
		prezzoBiglietto = 30;
		percentualeSconto = 0;
	}

	/**
	 * Crea un oggetto del tipo della classe Stadio, assegnando un valore predefinito alla variabile percentuale di sconto.
	 * @param name  il nome dello stadio
	 * @param capienza  la capienza dello stadio
	 * @param prezzo il prezzo dei biglietti delle partite giocanti nello stadio
	 */
	public Stadio(String name, String capienza, double prezzo) {
		this.nome = name;
		this.capienza = capienza;
		prezzoBiglietto = prezzo;
		percentualeSconto = 0;
	}

	/**
	 * Restituisce una stringa rappresentante la capienza dello stadio.
	 * @return capienza dello stadio
	 */
	public String getCapienza() {
		return capienza;
	}

	/**
	 * Modifca il valore della capienza dello stadio
	 * @param capienza dello stadio
	 */
	public void setCapienza(String capienza) {
		this.capienza = capienza;
	}

	/**
	 * Restituisce il nome dello stadio
	 * @return nome dello stadio
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Modifica il valore della variabile prezzo del biglietto.
	 * @param prezzo del biglietto
	 */
	public void setPrezzo(double prezzo) {
		prezzoBiglietto = prezzo;
	}

/**
 * Restituisce il valore della variabile prezzo.
 * @return prezzo del biglietto
 */
	public double getPrezzo() {
		return prezzoBiglietto;
	}

	/**
	 * Assegna una percentuale di sconto allo stadio.
	 * @param percentuale di sconto
	 */
	public void assegnaPercentualeSconto(double percentualeSconto) {
		this.percentualeSconto = percentualeSconto;

	}

	/**
	 * Restituisce il valore della variabile percentuale di sconto dello stadio.
	 * @return percentuale di sconto
	 */
	public double getPercentualeSconto() {
		return percentualeSconto;
	}

	public String toString() {
		return getClass().getSimpleName() + " [ Nome: " + this.nome + ", Capienza: " + this.capienza + "] ";
	}
}
