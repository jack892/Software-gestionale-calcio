package interfaces;

import exceptions.PostoIndisponibileException;

/**
 * Interfaccia per il controllo dello stato dei biglietti. Essa contiene la
 * firma di due metodi ePrenotabile(), eAcquistabile().
 **/
public interface Prevendita {

	/**
	 * Metodo per il controllo della prenotazione di un biglietto.
	 */
	boolean ePrenotabile() throws PostoIndisponibileException;

	/**
	 * Metodo per il controllo dell'acquisto di un biglietto.
	 */
	boolean eAcquistabile() throws PostoIndisponibileException;

}
