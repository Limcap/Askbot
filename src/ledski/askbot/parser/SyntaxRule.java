package ledski.askbot.parser;

/**
 * É a interface que deve ser usada para criação das regras.
 * @author Leandro Ledski
 *
 */
public abstract class SyntaxRule {
	
//	public short endIndex = 0;
//	public short startIndex = 0;
//	boolean isSuccess = false;
	
//	public GrammarRule( List<Token> tokenList, int startIndex ) {}
	public SyntaxRule() {
		System.out.println( "\n" + Thread.currentThread().getStackTrace()[2].getClassName().substring( 21 ) );
//		System.out.println( "stacklevel " + Thread.currentThread().getStackTrace().length );
	}


}
