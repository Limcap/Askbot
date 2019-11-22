package ledski.askbot.parser;

public abstract class CompilerExceptions {
	
	@SuppressWarnings("serial")
	public static class UnexpectedToken extends Exception {
		public UnexpectedToken( String message ) {
			super( message );
		}
	}
	
	@SuppressWarnings("serial")
	public static class NonExistentToken extends Exception {
		public NonExistentToken( String message ) {
			super( message );
		}
	}
	
	@SuppressWarnings("serial")
	public static class UnexpectedEndOfCode extends Exception {
		public UnexpectedEndOfCode( String message )  {
			super( message );
		}
	}


}
