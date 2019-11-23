package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_COMPARACAO -> ==  |  >  |  >=  |  <  |  <=  |  != 
 * @author Leandro
 */
public class ComparacaoOP extends SyntaxRule {

	public String valor;
	
	public ComparacaoOP() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _igualIgual );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		try {
			sm.getNextToken( _maior );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		try {
			sm.getNextToken( _maiorIgual );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		try {
			sm.getNextToken( _menor );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		try {
			sm.getNextToken( _menorIgual );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _diferente );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
