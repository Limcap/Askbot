package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_LOGICO -> && | || 
 * @author Leandro
 */
public class OpLogico extends SyntaxRule {

	public TokenType operador;
	
	public OpLogico() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			operador = sm.getNextToken( _e, _ou ).type;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
