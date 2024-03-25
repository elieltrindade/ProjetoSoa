package exception;

import javax.xml.ws.WebFault;

//Define a exceção como uma falha web
@WebFault(name = "DivisaoPorZero")
public class DivisaoPorZeroException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DivisaoPorZeroException() {
		// Chama o construtor da classe Exception com uma mensagem padrão
		super("Divisão por zero não é permitida!");
	}
	
}
