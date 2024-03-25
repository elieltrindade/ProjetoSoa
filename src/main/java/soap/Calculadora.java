package soap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.WebServiceContext;

import exception.DivisaoPorZeroException;

// Define a classe como um serviço web
@WebService
// Define o estilo do SOAP como RPC (Remote Procedure Call)
@SOAPBinding(style = Style.RPC)
public class Calculadora {
	
	@Resource
	WebServiceContext context; // Contexto do serviço web

	// Método para autenticar as chamadas ao serviço
	@SuppressWarnings("rawtypes")
	private boolean autenticar(WebServiceContext context) throws Exception {
		// Obtém o contexto da mensagem
		MessageContext mc = context.getMessageContext();
		// Obtém os cabeçalhos HTTP da mensagem
		Map httpHeaders = (Map) mc.get(MessageContext.HTTP_REQUEST_HEADERS);
		
		// Verifica se o cabeçalho "usuario" está presente
		if (!httpHeaders.containsKey("usuario"))
			throw new Exception("Informe um usuário");
		
		// Verifica se o cabeçalho "senha" está presente
		if (!httpHeaders.containsKey("senha")) 
			throw new Exception("Informe uma senha");
		
		// Obtém o usuário e a senha dos cabeçalhos
		String usuario = ((List) httpHeaders.get("usuario")).get(0).toString();
		String senha = ((List) httpHeaders.get("senha")).get(0).toString();
		
		// Verifica se o usuário e a senha são válidos
		if (usuario.equals("sisfin") && senha.equals("sisfin123")) {
			return true;
		} else { 
			throw new Exception("Usuário e senha inválidos");
		}
	}

	// Método de soma
	@WebMethod(action= "somar")
	// Define os parâmetros da operação
	public int somar(
			@WebParam(name= "numero1") int numero1,
			@WebParam(name = "numero2") int numero2) throws Exception {
		// Autentica a chamada
		autenticar(context);
		// Retorna a soma dos números
		return numero1 + numero2;
	}
	
	// Método de subtração
	@WebMethod(action= "subtrair")
	// Define os parâmetros da operação
	public int subtrair(
			@WebParam(name= "numero1") int numero1,
			@WebParam(name = "numero2") int numero2) {
		// Retorna a subtração dos números
		return numero1 - numero2;	
	}
	
	// Método de multiplicação
	@WebMethod(action= "multiplicar")
	// Define os parâmetros da operação
	public int multiplicar(
			@WebParam(name= "numero1") int numero1,
			@WebParam(name = "numero2") int numero2) {
		// Retorna a multiplicação dos números
		return numero1 * numero2;	
	}
	
	// Método de divisão
	@WebMethod(action= "dividir")
	// Define os parâmetros da operação
	public double dividir(
			@WebParam(name= "numero1") double numero1,
			@WebParam(name = "numero2") double numero2) throws DivisaoPorZeroException {
		// Verifica se o segundo número é zero para evitar divisão por zero
		if (numero2 == 0) {
			throw new DivisaoPorZeroException();
		}
		// Retorna a divisão dos números
		return numero1 / numero2;	
	}
}
