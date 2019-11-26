package ledski.askbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ledski.askbot.lexer.Automato;
import ledski.askbot.lexer.ConflictingTransitionException;
import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.runenv.MainGUI;
import ledski.util.Gridder;

/**
 * @author Leandro Oliveira Lino de Araujo
 */
public class Main {
	
	private static Automato automato;
	private static String erroNoAutomato = null;
	public static SyntaxTree tree;
	

	
	
	public static void main(String[] args) {
		
		// CRIA O AUTOMATO
		try {
			automato = new Automato();
		} catch( ConflictingTransitionException e ) {
			erroNoAutomato = e.getMessage();
			e.printStackTrace();
		}
		
		// CRIA A GUI
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
	public static List<Token> lexer( String codigo ) throws ConflictingTransitionException, IOException {
//		Automato a2 = new Automato();
//		a2.verboseLevel012 = 1;
//		a2.mostrarCaminhosNoConsole();
//		a2.mostrarEstadosNoConsole();
		
		automato.restart();
//		List<String> lines = Files.readAllLines( Paths.get("codigo.ask"), StandardCharsets.UTF_8 );
		List<String> lines = Arrays.asList( codigo.split( "\n" ) );
		List<Token> tokenList = new ArrayList<Token>();
		
		for( String line : lines ) {
			List<Token> temp = automato.process( line );
			// se o ultimo token retornado for _erro, para a execucao
			tokenList.addAll( temp );
			if( temp.size() > 0 && temp.get( temp.size()-1 ).type == TokenType._error ) break;
		}
		
//		for( Token t : tokenList ) {
//			System.out.println( t );
//		}
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
	
	
	
	
	
	
	/**
	 * Compila o código e cria uma SyntaxTree que é armazenada como membro static da Main.
	 * Caso dê erro, a SyntaxTree é colocada como null.
	 * Retorna null se o codigo compiou com sucesso, senão retorna a mensagem de erro.
	 * @param lines Lista de Strings representando o código
	 * @return String da mensagem de erro
	 */
	public static String compilar( String codigo ) {
		if( erroNoAutomato != null )
			return "Existe um problema com o compilador. Não é possível executar o código.\n" + erroNoAutomato;

		
		automato.restart();
		List<Token> tokenList = new ArrayList<Token>();
		
		// LEXER
		try {
			tokenList = lexer( codigo );
		} catch( ConflictingTransitionException | IOException e1 ) {
			e1.printStackTrace();
			//return e1.getMessage();
		}

		// PARSER
		try {
			tree = parser( tokenList );
		} catch( Exception e ) {
			tree = null;
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}
	
	
//	public static List<String> getTokens( String codigo ) {
//		
//	}
	
	
	
	
	
}
