package ledski.askbot.parser;

public abstract class CompilerExceptions {
	
	@SuppressWarnings("serial")
	public static class UnexpectedToken extends Exception {
		public UnexpectedToken( String message ) {
			super( message );
		}
	}
	
	@SuppressWarnings("serial")
	public static class NotAToken extends Exception {
		public NotAToken( String message ) {
			super( message );
		}
	}
	
	@SuppressWarnings("serial")
	public static class UnfinishedCode extends Exception {
		public UnfinishedCode( String message )  {
			super( message );
		}
	}


}
