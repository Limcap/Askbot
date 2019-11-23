package ledski.askbot;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.NotAToken;
import ledski.askbot.parser.SyntaxExceptions.UnfinishedCode;
import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.lexer.Automato;
import ledski.askbot.lexer.ConflictingTransitionException;
import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.util.Gridder;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {
	
	
	public static void main(String[] args) throws ConflictingTransitionException, NotAToken, UnexpectedToken, UnfinishedCode, Exception {
		Automato a2 = new Automato();
		a2.verboseLevel012 = 1;
//		a2.mostrarCaminhosNoConsole();
//		a2.mostrarEstadosNoConsole();
		
		String input = "Especialidade: \"Teste\" \"Só para testar\" Questao q1: \"ola\" {} Questao q2: \"Segunda Questao\" {10-20} Teste t1: \"Teste1\" Teste t2: \"Mais um teste\"";
		List<String> lines = Files.readAllLines( Paths.get("codigo.ask"), StandardCharsets.UTF_8 );
		List<Token> tokenList = new ArrayList<Token>();
		for( String line : lines ) {
			List<Token> temp = a2.process( line );
			// se o ultimo token retornado for _erro, para a execucao
			tokenList.addAll( temp );
			if( temp.size() > 0 && temp.get( temp.size()-1 ).type == TokenType._error ) break;
		}
		
		for( Token t : tokenList ) {
			System.out.println( t );
		}
		
		System.out.println( "\n\nTOKENS ENCONTRADOS:" );
		Gridder gr = new Gridder();
		tokenList.forEach( t -> gr.append( t.toGridder() ) );
		System.out.println( gr.publish() );
		
		// GRAMATICA
		SyntaxTree tree = new SyntaxTree( tokenList );
	}
	
	
}
