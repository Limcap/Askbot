package ledski.askbot.parser.rules;


import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * TERMO  ->  qVar  |  String  |  Numero
 * @author Leandro Ledski
 */
public class Termo extends SyntaxRule {
	
	
	public String valor = null;
	public TokenType tipo;
	
	
	public Termo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			Token t = sm.getNextToken( _qVar, _String, _Numero );
			valor = t.lexema;
			tipo = t.type;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
