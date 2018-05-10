package spring.h2.poc.exception;

public class PocException extends Exception {

	private static final long serialVersionUID = 1L;

	public PocException() {
		super();
	}

	public PocException(String mensagem) {
		super(mensagem);
	}

}
