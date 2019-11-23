package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * TERMO -> gVar | ITEM
 * @author Leandro
 */
public class Termo extends SyntaxRule {

	public String valor;
	
	public Termo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _qVar );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _Numero );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
