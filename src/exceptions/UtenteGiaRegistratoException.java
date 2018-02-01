package exceptions;

public class UtenteGiaRegistratoException extends Exception {
	public UtenteGiaRegistratoException() {
		super("Utente già presente");
	}

	public UtenteGiaRegistratoException(String msg) {
		super(msg);
	}
}
