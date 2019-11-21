package ledski.askbot;

import java.util.List;

import ledski.askbot.automato.Automato;
import ledski.askbot.automato.ConflictingTransitionException;
import ledski.askbot.automato.Token;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {


	public static void main(String[] args) throws ConflictingTransitionException {
		Automato a2 = new Automato();
		a2.verboseLevel012 = 2;
		a2.mostrarCaminhosNoConsole();
		a2.mostrarEstadosNoConsole();

		String input = "Questaoe qDfui: \"ola\"";
		List<Token> tokens = a2.process( input );
		System.out.println( "\n\nTOKEN" );
		for( Token t : tokens ) { System.out.print( t + " " ); }

//		Gramatica gr = new Gramatica( tokenList );
//		gr.verificar();
	}
}
