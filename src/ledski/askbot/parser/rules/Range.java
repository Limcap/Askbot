package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * RANGE  ->  Numero  -  Numero
 * @author Leandro Ledski
 *
 */
public class Range extends SyntaxRule {
	
	
	public Double min;
	public Double max;
	
	
	public Range() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			min = Double.valueOf( sm.getNextToken( _Numero ).lexema );
			sm.getNextToken( _menos );
			max = Double.valueOf( sm.getNextToken( _Numero ).lexema );
		}
		catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
