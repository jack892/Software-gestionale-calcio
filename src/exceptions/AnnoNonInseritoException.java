package exceptions;

public class AnnoNonInseritoException extends Exception {
	public AnnoNonInseritoException() {
		super("Inserire l'anno");
	}

	public AnnoNonInseritoException(String msg) {
		super(msg);
	}
}
