package ledski.askbot.parser.rules;

import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/**
 * Regra:
 * CONDICAO  ->  TERMO  OP_COMPARACAO  TERMO
 * @author Leandro Ledski
 */
public class Condicao extends SyntaxRule {
	
	
	public OpLogico opLogico = null;
	public Termo termo1;
	public OpComparacao opComparacao;
	public Termo termo2;
	
	
	public Condicao() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			termo1 = new Termo();
			opComparacao = new OpComparacao();
			termo2 = new Termo();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
