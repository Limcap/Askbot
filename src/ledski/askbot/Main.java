package ledski.askbot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.lexer.Automato;
import ledski.askbot.lexer.ConflictingTransitionException;
import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.util.Gridder;
import ledski.askbot.runenv.MainGUI;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {	
			@Override
			public void run() {
				JFrame mainGUI = new MainGUI();
				mainGUI.setVisible( true );
			}
		});
	}
	
	
	
	/**
	 * Executa o lexer em uma lista de Strings, um String ou um Character e retorna uma lista de tokens.
	 * @return List<Token>
	 * @throws ConflictingTransitionException 
	 * @throws IOException 
	 */
	public static List<Token> lexer() throws ConflictingTransitionException, IOException {
		Automato a2 = new Automato();
		a2.verboseLevel012 = 1;
//		a2.mostrarCaminhosNoConsole();
//		a2.mostrarEstadosNoConsole();
		
		
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
		return tokenList;
	}
	
	
	
	/**
	 * Esecuta o parser. Recebe uma lista de tokens e os verifica sobre as regras da gramática,
	 * criando uma árvore sintática.
	 * @return SyntaxTree
	 * @throws Exception
	 */
	public static SyntaxTree parser( List<Token> tokenList ) throws Exception {
		System.out.println( "\n\nTOKENS ENCONTRADOS:" );
		Gridder gr = new Gridder();
		tokenList.forEach( t -> gr.append( t.toGridder() ) );
		System.out.println( gr.publish() );
		
		return new SyntaxTree( tokenList );
	}
	
	
}
