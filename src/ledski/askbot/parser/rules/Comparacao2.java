package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * COMPARACAO2 -> OP_LOGICO  COMPARACAO1 | vazio
 * @author Leandro
 */
public class Comparacao2 extends SyntaxRule {

	public String valor;
	
	public Comparacao2() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _Se );
			sm.getNextToken( _Entao );
			sm.getNextToken( _String );
			sm.getNextToken( _Senao );
			sm.getNextToken( _String );
			
			
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
	}

}
