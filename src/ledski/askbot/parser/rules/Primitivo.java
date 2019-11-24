package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * PRIMITIVO -> Numero | String
 * @author Leandro
 *
 */
public class Primitivo extends SyntaxRule {

	public String valor;
	public boolean isNumero;
	
	public Primitivo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			isNumero = sm.isNextToken( _Numero ); 
			valor = sm.getNextToken( _String, _Numero ).lexema;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
