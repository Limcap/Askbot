package ledski.askbot.runenv;

import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

import ledski.askbot.Main;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.parser.rules.Questao;
import ledski.util.Gridder;

public class Interpreter {
	SyntaxTree tree;
	StringBuilder sb;
	boolean waitingForInput = false;
	int currentQuestionIndex = 0;
//	String currentInputVar = null;
//	String currentInputValue = null;
	Map<String, String> variaveisDeclaradas;
	
	public void reset() {
		variaveisDeclaradas = new HashMap<String,String>();
		waitingForInput = false;
		currentQuestionIndex = 0;
//		currentInputVar = null;
//		currentInputValue = null;
		tree = Main.tree;
	}
	
	public String iniciarConversa() {
		sb = new StringBuilder();
		
		if( currentQuestionIndex == 0 ) {
			writeLn( sb, tree.especialidade.name );
			writeLn( sb, tree.especialidade.description );
			skipLines(1);
			writeLn( sb, dizerQuestao() );
		}
		return sb.toString();
//		else if( currenTreeNode instanceof )
		
	}
	
	/**
	 * Recebe o input do usuário e responde
	 * @param input
	 * @return
	 */
	public String sendUserInput( String input ) {
		StringBuilder sb = new StringBuilder();
		if( currentQuestionIndex >= tree.questoes.size() ) return null;
		Questao q = tree.questoes.get( currentQuestionIndex );
		int respIndice;
		try {
			respIndice = Integer.parseInt( input );
			System.out.println( input + " -> " + respIndice );
		}
		catch( NumberFormatException e ) {
			respIndice = -1;
		}
		if( q.arrayDeResposta.range == null ) {
			System.out.println( "Items" );
			if( respIndice > -1 && respIndice < q.arrayDeResposta.items.size() ) {
				String resposta = q.arrayDeResposta.items.get( respIndice ).valor;
				variaveisDeclaradas.put( q.variavel, resposta );
				sb.append( "\nVocê respodeu: " + resposta + "\n\n" );
				currentQuestionIndex++;
				sb.append( dizerQuestao() );
				
			}
		}
		else {
			System.out.println( "Range" );
			if( respIndice >= q.arrayDeResposta.range.min && respIndice <= q.arrayDeResposta.range.max ) {
				variaveisDeclaradas.put( q.variavel, String.valueOf( respIndice ) );
				sb.append( "\nVocê respodeu: " + respIndice + "\n\n" );
				currentQuestionIndex++;
				sb.append( dizerQuestao() );
			}
		}
		return sb.toString();
	}
	
	private void writeLn( StringBuilder sb, String s ) {
		sb.append( removeQuotes( s ) + ( s.endsWith( "\n" ) ? "" : "\n" ) );
	}
	
	private void skipLines( int i ) {
		sb.append(  Gridder.repeat( "\n", i ) );
	}
	
	/**
	 * Retorna a fala do bot sobre a Questao indicada em currentQuestionIndex
	 * @param q
	 * @return
	 */
	private String dizerQuestao(){
		Questao q = tree.questoes.get( currentQuestionIndex );
		if( currentQuestionIndex > tree.questoes.size() ) return null;
		StringBuilder sb = new StringBuilder();
		writeLn( sb, q.textoDaQuestao );
		if( q.arrayDeResposta.range == null ) {
			writeLn( sb, "Digite o número correspondente à resposta" );
			for( int i = 0; i < q.arrayDeResposta.items.size(); i++ ) {
				String s = q.arrayDeResposta.items.get( i ).valor;
				sb.append( i + ". " + removeQuotes( s ) + "\n" );
			}
		}
		else {
			writeLn( sb, "Digite um número entre " +
				q.arrayDeResposta.range.min + " e " + q.arrayDeResposta.range.max
			);
		}
		return sb.toString();
	}
	
	private String dizerResposta( Questao q ) {
		if( variaveisDeclaradas.containsKey( q.variavel )) {
			return variaveisDeclaradas.get( q.variavel );
		}
		else {
			StringBuilder sb = new StringBuilder();
			for( int i = 0; i < q.arrayDeResposta.items.size(); i++ ) {
				String s = q.arrayDeResposta.items.get( i ).valor;
				sb.append( i + ". " + removeQuotes( s ) + "\n" );
			}
			return sb.toString();
		}
	}
	
	
	
	
	private String removeQuotes( String s ) {
		if( s.startsWith("\"") && s.endsWith("\"") )
		return s.substring( 1, s.length()-1 );
		else return s;
	}
}
