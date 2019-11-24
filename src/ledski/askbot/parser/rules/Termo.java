package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * TERMO -> qVar | String | Numero
 * @author Leandro
 */
public class Termo extends SyntaxRule {

	public String valor = null;
	
	public Termo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			
			valor = sm.getNextToken( _qVar, _String, _Numero ).lexema;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
		
	}

}
