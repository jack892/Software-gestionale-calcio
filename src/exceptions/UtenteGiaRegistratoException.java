package exceptions;

public class UtenteGiaRegistratoException extends Exception {
	public UtenteGiaRegistratoException() {
		super("Utente gi� presente");
	}

	public UtenteGiaRegistratoException(String msg) {
		super(msg);
	}
}
