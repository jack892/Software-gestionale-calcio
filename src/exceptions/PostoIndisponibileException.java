package exceptions;

import javax.swing.JOptionPane;

public class PostoIndisponibileException extends Exception {
	public PostoIndisponibileException() {
	}

	public PostoIndisponibileException(String msg) {
		super(msg);
	}

}
