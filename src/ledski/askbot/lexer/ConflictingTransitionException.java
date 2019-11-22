package ledski.askbot.lexer;

/**
 * @author Leandro
 * @version 0.1.0
 */
public class ConflictingTransitionException extends Exception {

	private static final long serialVersionUID = 1;

	public ConflictingTransitionException( String msg ) {
		super( msg );
	}

}
