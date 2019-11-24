package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * OP_COMPARACAO -> ==  |  >  |  >=  |  <  |  <=  |  != 
 * @author Leandro
 */
public class OpComparacao extends SyntaxRule {

	public TokenType type;
	
	public OpComparacao() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		if( sm.noSuccessYet() ) try {
			type = sm.getNextToken( _igualIgual, _diferente, _maior, _maiorIgual, _menor, _menorIgual ).type;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
//		try {
//			sm.getNextToken( _igualIgual );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//		}
//		
//		
//		try {
//			sm.getNextToken( _maior );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//		}
//		
//		
//		try {
//			sm.getNextToken( _maiorIgual );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//		}
//		
//		
//		try {
//			sm.getNextToken( _menor );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//		}
//		
//		
//		try {
//			sm.getNextToken( _menorIgual );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//		}
//		
//		
//		if( sm.noSuccessYet() ) try {
//			sm.getNextToken( _diferente );
//		}
//		catch ( UnexpectedToken e ) {
//			sm.resetRulePointer();
//			sm.rethrowSavedExceptionFromCatchBlock();
//		}
		
		
	}

}
