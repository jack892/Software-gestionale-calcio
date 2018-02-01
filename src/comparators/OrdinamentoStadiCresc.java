package comparators;

import java.util.Comparator;

import classes.Partita;

/**
 * Questa classe implementando l'interfaccia Comparator permette l'ordinamento
 * crescente sulla capienza degli stadi.
 */
public class OrdinamentoStadiCresc implements Comparator<Partita> {

	/**
	 * L'implementazione dell'unico metodo dell'interfaccia Comparator
	 * restituisce -1,1 o 0 in base alla date delle partite confrontate. 
	 * @param partita0 la partita con la quale avviene il confronto
	 * @param partita1 la partita con la quale avviene il confronto
	 * @return -1 se la capienza dello stadio di partita0 è minore della capienza dello stadio della partita1
	 * @return 1 se la capienza dello stadio di partita0 è maggiore della capienza dello stadio della partita1
	 * @return 0 se la capienza dello stadio di partita0 è uguale della capienza dello stadio della partita1
	 */
	public int compare(Partita partita0, Partita partita1) {
		int a = Integer.parseInt(partita0.getStadio().getCapienza());
		int b = Integer.parseInt(partita1.getStadio().getCapienza());
		if (a < b)
			return -1;
		else if (a > b)
			return 1;
		return 0;
	}

}
