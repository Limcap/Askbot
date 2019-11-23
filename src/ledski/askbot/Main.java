package ledski.askbot;

import java.util.List;

import ledski.askbot.parser.CompilerExceptions.NotAToken;
import ledski.askbot.parser.CompilerExceptions.UnfinishedCode;
import ledski.askbot.parser.CompilerExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.lexer.Automato;
import ledski.askbot.lexer.ConflictingTransitionException;
import ledski.askbot.lexer.Token;
import ledski.util.Gridder;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {
	
	
	public static void main(String[] args) throws ConflictingTransitionException, NotAToken, UnexpectedToken, UnfinishedCode, Exception {
		Automato a2 = new Automato();
		a2.verboseLevel012 = 2;
		a2.mostrarCaminhosNoConsole();
		a2.mostrarEstadosNoConsole();
		
		String input = "Especialidade: \"Teste\" \"Só para testar\" Questao q1: \"ola\" Questao q2: \"Segunda Questao\" {10-20} Teste t1: \"Teste1\" Teste t2: \"Mais um teste\"";
		List<Token> tokenList = a2.process( input );
		
		System.out.println( "\n\nTOKENS ENCONTRADOS:" );
		Gridder gr = new Gridder();	
		tokenList.forEach( t -> gr.append( t.toGridder() ) );
		System.out.println( gr.publish() );
		
		// GRAMATICA
		SyntaxTree tree = new SyntaxTree( tokenList );
	}
	
	
}
