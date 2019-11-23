package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

public class Primitivo extends SyntaxRule {

	public String string;
	public Double numero;
	
	public Primitivo() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			string = sm.getNextToken( _String ).lexema;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		if( sm.noSuccessYet() ) try {
			numero =Double.valueOf( sm.getNextToken( _Numero ).lexema );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
