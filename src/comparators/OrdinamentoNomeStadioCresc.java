package comparators;

import java.util.Comparator;

import classes.Partita;

/**
 * Questa classe implementando l'interfaccia Comparator permette l'ordinamento
 * cronologico crescente sul nome dello stadio.
 */
public class OrdinamentoNomeStadioCresc implements Comparator<Partita> {

	/**
	 * L'implementazione dell'unico metodo dell'interfaccia Comparator
	 * restituisce -1,1 o 0 in base al nome dello stadio da confrontate. 
	 * @param partita0 la partita con la quale avviene il confronto
	 * @param partita1 la partita con la quale avviene il confronto
	 * @return -1 se il nome dello stadio della partita è lessicograficamente minore del nome dello stadio della partita1
	 * @return 1 se il nome dello stadio della partita è lessicograficamente maggiore del nome dello stadio della partita1
	 * @return 0 se il nome dello stadio della partita0 è lessicograficamente uguale del nome dello stadio della partita1
	 */
	public int compare(Partita arg0, Partita arg1) {
		String a = arg0.getStadio().getNome();
		String b = arg1.getStadio().getNome();
		return a.compareTo(b);
	}

}
