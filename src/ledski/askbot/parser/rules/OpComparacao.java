package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_COMPARACAO  ->  ==  |  >  |  >=  |  <  |  <=  |  != 
 * @author Leandro Ledski
 */
public class OpComparacao extends SyntaxRule {
	
	
	public TokenType tipo;
	
	
	public OpComparacao() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		if( sm.noSuccessYet() ) try {
			tipo = sm.getNextToken( _igualIgual, _diferente, _maior, _maiorIgual, _menor, _menorIgual ).type;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
