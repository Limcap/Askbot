package ledski.askbot.runenv;

import ledski.askbot.Main;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.parser.rules.Questao;
import ledski.util.Gridder;

public class Interpreter {
	SyntaxTree tree;
	boolean waitingForInput = false;
	SyntaxRule currenTreeNode = null;
	StringBuilder sb;
	
	public void reset() {
		waitingForInput = false;
		currenTreeNode = null;
		tree = Main.tree;
	}
	
	public String getNextBotMessage() {
		sb = new StringBuilder();
		
		if( currenTreeNode == null ) {
			writeLn( sb, tree.especialidade.name );
			writeLn( sb, tree.especialidade.description );
			skipLines(1);
			writeLn( sb, dizerQuestao( tree.questoes.get( 0 ) ) );
		}
		return sb.toString();
//		else if( currenTreeNode instanceof )
		
	}
	
	private void writeLn( StringBuilder sb, String s ) {
		sb.append( removeQuotes( s ) + ( s.endsWith( "\n" ) ? "" : "\n" ) );
	}
	
	private void skipLines( int i ) {
		sb.append(  Gridder.repeat( "\n", i ) );
	}
	
	private String dizerQuestao( Questao q ) {
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
	
	private String removeQuotes( String s ) {
		if( s.startsWith("\"") && s.endsWith("\"") )
		return s.substring( 1, s.length()-1 );
		else return s;
	}
}
