package ledski.askbot.parser;

public abstract class GrammarRule {
	
//	public short endIndex = 0;
//	public short startIndex = 0;
//	boolean isSuccess = false;
	
//	public GrammarRule( List<Token> tokenList, int startIndex ) {}
	public GrammarRule( TokenizedCodeManager code ) {
//		code = code.advanceStartIndex();
		System.out.println( "\n" + Thread.currentThread().getStackTrace()[2].getClassName().substring( 21 ) );
	}


}
