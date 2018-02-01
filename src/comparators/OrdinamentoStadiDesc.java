package comparators;

import java.util.Comparator;

import classes.Partita;


/**
 * Questa classe implementando l'interfaccia Comparator permette l'ordinamento
 * decrescente sulla capienza degli stadi.
 */
public class OrdinamentoStadiDesc implements Comparator<Partita> {

	/**
	 * L'implementazione dell'unico metodo dell'interfaccia Comparator
	 * restituisce -1,1 o 0 in base alla date delle partite confrontate. 
	 * @param partita0 la partita con la quale avviene il confronto
	 * @param partita1 la partita con la quale avviene il confronto
	 * @return 1 se la capienza dello stadio di partita0 è minore della capienza dello stadio della partita1
	 * @return -1 se la capienza dello stadio di partita0 è maggiore della capienza dello stadio della partita1
	 * @return 0 se la capienza dello stadio di partita0 è uguale della capienza dello stadio della partita1
	 */
	public int compare(Partita arg0, Partita arg1) {
		int a = Integer.parseInt(arg0.getStadio().getCapienza());
		int b = Integer.parseInt(arg1.getStadio().getCapienza());
		if (a < b)
			return 1;
		else if (a > b)
			return -1;
		return 0;
	}

}
