package comparators;

import java.util.Comparator;
import java.util.GregorianCalendar;

import classes.Partita;

/**
 * Questa classe implementando l'interfaccia Comparator permette l'ordinamento
 * cronologico crescente su un insieme di partite.
 */
public class OrdinamentoDataCresc implements Comparator<Partita> {

	/**
	 * L'implementazione dell'unico metodo dell'interfaccia Comparator
	 * restituisce -1,1 o 0 in base alla date delle partite confrontate. 
	 * @param partita0 la partita con la quale avviene il confronto
	 * @param partita1 la partita con la quale avviene il confronto
	 * @return -1 se la data della partita0 viene prima della data di partita1
	 * @return 1 se la data della partita0 viene dopo la data di partita1
	 * @return 0 se la data della partita0 combacia con data di partita1
	 */
	public int compare(Partita partita0, Partita partita1) {
		GregorianCalendar a = partita0.getDataGregorian();
		GregorianCalendar b = partita1.getDataGregorian();
		if (a.before(b))
			return -1;
		else if (a.after(b))
			return 1;
		return 0;
	}

}
