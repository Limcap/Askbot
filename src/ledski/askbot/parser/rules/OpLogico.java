package ledski.askbot.parser.rules;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_LOGICO  ->  e | ou 
 * @author Leandro Ledski
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
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
