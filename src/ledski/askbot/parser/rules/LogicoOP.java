package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_LOGICO -> && | || 
 * @author Leandro
 */
public class LogicoOP extends SyntaxRule {

	public String valor;
	
	public LogicoOP() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _e );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _ou );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
