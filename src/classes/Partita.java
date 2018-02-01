package classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import graphics.MainFrame;
import interfaces.Scontabile;

/**
 * La classe Partita rappresenta la partita giocante in uno stadio. Implementa
 * due interfacce, Serializable per la memorizzazione in un flusso e Scontabile
 * per applicare sconti alle partite.
 */
public class Partita implements Serializable, Scontabile {
	private String squadraA;
	private String squadraB;
	private Stadio stadio;
	private GregorianCalendar data;
	private double percentualeSconto;

	/**
	 * Crea un oggetto del tipo della classe Partita.
	 * 
	 * @param squadraA
	 *            squadra giocante in casa
	 * @param squadraB
	 *            squadra giocante fuori casa
	 * @param nomeStadio
	 *            nome dello stadio
	 * @param data
	 *            data di incontro delle squadre
	 */
	public Partita(String squadraA, String squadraB, String nomeStadio, GregorianCalendar data) {
		this.squadraA = squadraA;
		this.squadraB = squadraB;
		stadio = StrutturaSportiva.cercaStadi(nomeStadio);
		this.data = data;
		percentualeSconto = 0;
	}

	/**
	 * Restituisce una stringa rappresentante il giorno del mese in cui viene
	 * giocata la partita.
	 * 
	 * @return giorno in cui si gioca la partita
	 */
	public String getGiorno() {
		return "" + data.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Restituisce una stringa rappresentante il mese in cui viene giocata la
	 * partita.
	 * 
	 * @return mese in cui si gioca la partita
	 */
	public String getMese() {
		return "" + (data.get(Calendar.MONTH) + 1);
	}

	/**
	 * Restituisce una stringa rappresentante l'anno in cui viene giocata la
	 * partita.
	 * 
	 * @return anno in cui si gioca la partita
	 */
	public String getAnno() {
		return "" + data.get(Calendar.YEAR);
	}

	/**
	 * Restituisce una stringa rappresentante il l'ora in cui viene giocata la
	 * partita.
	 * 
	 * @return ora in cui si gioca la partita
	 */
	public String getOra() {
		return "" + data.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Restituisce una stringa rappresentante i minuti in cui viene giocata la
	 * partita.
	 * 
	 * @return minuti in cui si gioca la partita
	 */
	public String getMinuti() {
		return "" + data.get(Calendar.MINUTE);
	}

	/**
	 * Restituisce la data della partita in formato stringa.
	 * 
	 * @return data in cui si gioca la partita
	 */

	public String getData() {
		return getGiorno() + "/" + getMese() + "/" + getAnno();
	}

	/**
	 * Restituisce la data della partita in formato GregorianCalendar.
	 * 
	 * @return data in cui si gioca la partita
	 */
	public GregorianCalendar getDataGregorian() {
		return data;
	}

	/**
	 * Restituisce la squadra che gioca in casa di una partita.
	 * 
	 * @return squadraA squadra che gioca in casa
	 */
	public String getSquadraA() {
		return squadraA;
	}

	/**
	 * Modifica la squadra giocante in casa di una partita con quella passata da
	 * parametro.
	 * 
	 * @param squadraA
	 *            squadra che gioca in casa
	 */
	public void setSquadraA(String squadraA) {
		this.squadraA = squadraA;
	}

	/**
	 * Restituisce la squadra giocante fuori casa di una partita.
	 * 
	 * @return squadraB squadra ospite
	 * 
	 */
	public String getSquadraB() {
		return squadraB;
	}

	/**
	 * Modifica la squadra ospite di una partita con quella passata da
	 * parametro.
	 * 
	 * @param squadraB
	 *            squadra ospite
	 */
	public void setSquadraB(String squadraB) {
		this.squadraB = squadraB;
	}

	/**
	 * Restituisce il nome dello stadio dove viene giocata la partita.
	 * 
	 * @return nome stadio
	 */
	public String getNomeStadio() {
		return stadio.getNome();
	}

	/**
	 * Metodo che restituisce l'oggetto stadio.
	 * 
	 * @return stadio
	 */
	public Stadio getStadio() {
		return stadio;
	}

	/**
	 * Modifica il valore della variabile percentuale sconto.
	 * 
	 * @param percentuale
	 *            di sconto
	 */
	public void assegnaPercentualeSconto(double percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	/**
	 * Restituisce il valore della percentuale di sconto.
	 * 
	 * @return percentuale di sconto
	 */
	public double getPercentualeSconto() {
		return percentualeSconto;
	}

	public String toString() {
		return getClass().getSimpleName() + " [ Stadio " + stadio.getNome() + " : " + squadraA + " vs. " + squadraB
				+ " data: " + getData() + " - " + getOra() + ":" + getMinuti() + " numero settimana: "
				+ (getDataGregorian().get(Calendar.WEEK_OF_YEAR)) + " ]";
	}

	/**
	 * Restituisce una stringa riguardante l'informazioni di una partita.
	 */
	public String getInfoPartita() {
		return "Stadio: " + getNomeStadio() + "	evento: " + getSquadraA() + " vs. " + getSquadraB() + "	data: "
				+ getData() + " - " + getOra() + ":" + getMinuti();
	}

}
