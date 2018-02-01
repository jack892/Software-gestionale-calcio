package classes;

import java.io.Serializable;

import java.util.GregorianCalendar;

import exceptions.PostoIndisponibileException;
import interfaces.Prevendita;

/**
 * La classe Biglietto rappresenta il biglietto di una partita associato ad un
 * cliente, conterrà il prezzo e il numero del posto ad esso associato.
 **/
public class Biglietto implements Serializable, Prevendita {
	private int codicePosto;
	private double prezzo;
	private Partita partita;
	private GregorianCalendar oggi;
	private Cliente cliente;
	private boolean acquistato;
	private boolean prenotato;
	
	/**
	 * Crea un oggetto del tipo della classe Biglietto in riferimento ad una
	 * partita.
	 * 
	 * @param partita
	 *            è la partita che viene associata a un biglietto
	 * @param prezzo
	 *            è il prezzo associato al biglietto
	 * @param codicePosto
	 *            è il riferimento al posto selezionato nello stadio
	 * @param cliente
	 *            è il riferimento al cliente che acquista il biglietto
	 */
	public Biglietto(Partita partita, double prezzo, int codicePosto, Cliente cliente) {
		oggi = new GregorianCalendar();
		acquistato = false;
		prenotato = false;
		this.partita = partita;
		this.prezzo = prezzo;
		this.codicePosto = codicePosto;
		this.cliente = cliente;
	}

	/**
	 * Metodo che restituisce l'oggetto cliente
	 * 
	 * @return cliente il riferimento all'oggetto cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo che restituisce l'oggetto Partita
	 * 
	 * @return partita il riferimento all'oggetto partita
	 */
	public Partita getPartita() {
		return partita;
	}

	/**
	 * Metodo che restituisce il valore di codice posto
	 * 
	 * @return codicePosto l'intero in riferimento al posto nello stadio assegnato
	 */
	public int getCodicePosto() {
		return codicePosto;
	}

	/**
	 * Restituisce il valore della variabile prezzo
	 * 
	 * @return prezzo un double che indica il prezzo associato al biglietto
	 */
	public double getPrezzo() {
		return prezzo;
	}

	/**
	 * Restituisce una variabile booleana che indica se il biglietto è stato
	 * acquistato
	 * 
	 * @return acquistato true se il biglietto risulta acquistato dall'utente, false
	 *         altrimenti .
	 */
	public boolean isAcquistato() {
		return acquistato;
	}

	/**
	 * Modifica il valore della variabile acquistato.
	 * 
	 * @param acquistato
	 *            il valore a cui deve essere settato il parametro acquistato
	 * @exception PostoIndisponibileException
	 *             viene sollevata nel momento in cui il posto non è
	 *             acquistabile
	 */
	public void setAcquistato(boolean acquistato) throws PostoIndisponibileException {
		if (!isAcquistato() || isPrenotato())
			this.acquistato = acquistato;
		else
			throw new PostoIndisponibileException();
	}

	/**
	 * Restituisce una variabile booleana che indica se il biglietto è stato
	 * prenotato
	 * 
	 * @return prenotato true se il biglietto risulta prenotato dall'utente,false
	 *         altrimenti .
	 */
	public boolean isPrenotato() {
		return prenotato;
	}

	/**
	 * Modifica il valore della variabile prenotato.
	 * 
	 * @param prenotato
	 *            il valore a cui deve essere settato il parametro prenotato
	 * @exception PostoIndisponibileException
	 *             viene sollevata nel momento in cui non è possibile prenotare
	 *             un posto
	 */
	public void setPrenotato(boolean prenotato) throws PostoIndisponibileException {
		if (!isPrenotato() && !isAcquistato())
			this.prenotato = prenotato;
		else
			throw new PostoIndisponibileException();
	}

	/**
	 * Restituisce un valore booleano dopo aver controllato e confrontato
	 * l'orario della generazione del biglietto con quello della partita.
	 * 
	 * @return true se la prenotazione avviene 12 ore prima dell'evento, false
	 *         altrimenti
	 * @exception PostoIndisponibileException
	 *             viene sollevata se la prenotazione è effettuata dopo le 12
	 *             ore
	 */
	public boolean ePrenotabile() throws PostoIndisponibileException {
		if (oggi.before(new GregorianCalendar(Integer.parseInt(partita.getAnno()),
				Integer.parseInt(partita.getMese()) - 1, Integer.parseInt(partita.getGiorno()),
				Integer.parseInt(partita.getOra()) - 12, Integer.parseInt(partita.getMinuti()))))
			return true;
		else {

			throw new PostoIndisponibileException();
		}
	}

	/**
	 * Restituisce un valore booleano dopo aver controllato e confrontato
	 * l'orario della generazione del biglietto con quello della partita.
	 * 
	 * @return true se l'acquisto avviene 2 ore prima dell'evento, false
	 *         altrimenti.
	 * @exception PostoIndisponibileException
	 *             viene sollevata se l'acquisto è effettuato dopo le due ore
	 */
	public boolean eAcquistabile() throws PostoIndisponibileException {
		if (oggi.before(new GregorianCalendar(Integer.parseInt(partita.getAnno()),
				Integer.parseInt(partita.getMese()) - 1, Integer.parseInt(partita.getGiorno()),
				Integer.parseInt(partita.getOra()) - 2, Integer.parseInt(partita.getMinuti()))))
			return true;
		else {
			throw new PostoIndisponibileException();
		}
	}

	/**
	 * @return Restituisce una rappresentazione testuale dello stato interno
	 *         dell’oggetto Biglietto come stringa.
	 */
	public String toString() {
		return "Biglietto [codicePosto=" + codicePosto + ", partita=" + partita + ", cliente=" + cliente + "]";
	}

}
