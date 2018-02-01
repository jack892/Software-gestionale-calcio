package comparators;

import java.util.Comparator;

import classes.Partita;


/**
 * Questa classe implementando l'interfaccia Comparator permette l'ordinamento
 * cronologico decrescente sul nome delle partite.
 */
public class OrdinamentoNomeSquadre implements Comparator<Partita> {


	/**
	 * L'implementazione dell'unico metodo dell'interfaccia Comparator
	 * restituisce -1,1 o 0 in base al nome delle partite confrontate. 
	 * @param partita0 la partita con la quale avviene il confronto
	 * @param partita1 la partita con la quale avviene il confronto
	 * @return -1 se il nome della partita0 è lessicograficamente minore del nome della partita1
	 * @return 1 se il nome della partita0 è lessicograficamente maggiore del nome della partita1
	 * @return 0 se il nome della partita0 è lessicograficamente uguale del nome della partita1
	 */
	public int compare(Partita partita0, Partita partita1) {
		String a = partita0.getSquadraA();
		String b = partita1.getSquadraA();
		return a.compareTo(b);
	}

}
