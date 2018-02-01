package interfaces;

/**
 * Interfaccia per il controllo dello stato dei biglietti. Essa contiene la
 * firma di due metodi assegnaPercentualeSconto(), getPercentualeSconto().
 **/
public interface Scontabile {
	/**
	 * Metodo per l'assegnazione dello sconto.
	 */
	void assegnaPercentualeSconto(double percentualeSconto);

	/**
	 * Metodo per la restituzione dello sconto.
	 */
	double getPercentualeSconto();

}
