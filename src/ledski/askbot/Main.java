package ledski.askbot;

import java.util.List;

import ledski.askbot.automato.Automato;
import ledski.askbot.automato.ConflictingTransitionException;
import ledski.askbot.automato.Token;
import ledski.util.Gridder;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {


	public static void main(String[] args) throws ConflictingTransitionException {
		Automato a2 = new Automato();
		a2.verboseLevel012 = 2;
		a2.mostrarCaminhosNoConsole();
		a2.mostrarEstadosNoConsole();

		String input = "Questao qDfui: \"ola\"";
		List<Token> tokens = a2.process( input );
		System.out.println( "\n\nTOKENS ENCONTRADOS:" );
		
//		for( Token t : tokens ) { System.out.println( t ); } 
		Gridder gr = new Gridder();	
		tokens.forEach( t -> gr.append( t.toGridder() ) );
		System.out.println( gr.publish() );

//		Gramatica gr = new Gramatica( tokenList );
//		gr.verificar();
	}
}
