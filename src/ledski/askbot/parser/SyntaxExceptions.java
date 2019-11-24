package ledski.askbot.parser;

import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;

public abstract class SyntaxExceptions {
	
	@SuppressWarnings("serial")
	public static class UnexpectedToken extends Exception {
		public UnexpectedToken( Token t, int pointer, TokenType[] types ) {
			super( "\nFound: " + t.toString() + "\non index " + pointer + "\nExpected: " + tokenTypesToString( types ) );
		}
		private static String tokenTypesToString( TokenType[] types ) {
			StringBuilder sb = new StringBuilder();
			for( int i=0; i<types.length; i++ )
				sb.append( types[i].name() + (i<types.length-1 ? ", " : "") );
			return sb.toString();
		}
	}
	
	@SuppressWarnings("serial")
	public static class NotAToken extends Exception {
		public NotAToken( Token t, int pointer ) {
			super( "\n" + t.toString() + "\nINDEX: " + pointer );
		}
	}
	
	@SuppressWarnings("serial")
	public static class UnfinishedCode extends Exception {
		public UnfinishedCode() {
			super( "\nUnexpected end of code" );
		}
	}
	
	@SuppressWarnings("serial")
	public static class EmptyArray extends Exception {
		public EmptyArray( int pointer ) {
			super( "\nEmpty array is now allowed\nINDEX: " + pointer );
		}
	}

}
