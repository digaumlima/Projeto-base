package br.org.pasa.projeto.exception;


public class AcessoNegadoException extends Exception {
	private static final long serialVersionUID = -2661582132539157321L;

	public AcessoNegadoException() {
		super();
	}

	public AcessoNegadoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AcessoNegadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public AcessoNegadoException(String message) {
		super(message);
	}

	public AcessoNegadoException(Throwable cause) {
		super(cause);
	}

}
