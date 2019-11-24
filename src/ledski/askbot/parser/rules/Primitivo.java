package ledski.askbot.parser.rules;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.lexer.Token;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * PRIMITIVO  ->  Numero  |  String
 * @author Leandro Ledski
 *
 */
public class Primitivo extends SyntaxRule {
	
	
	public String valor;
	public TokenType tipo;
	
	
	public Primitivo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			Token t = sm.getNextToken( _String, _Numero );
			valor = t.lexema;
			tipo = t.type;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
