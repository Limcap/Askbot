package ledski.askbot.parser;

import ledski.askbot.lexer.Token;

public abstract class CompilerExceptions {
	
	@SuppressWarnings("serial")
	public static class UnexpectedToken extends Exception {
		public UnexpectedToken( Token t, int pointer ) {
			super( "\n" + t.toString() + "\nINDEX: " + pointer );
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
