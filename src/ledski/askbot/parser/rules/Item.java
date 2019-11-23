package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

public class Item extends SyntaxRule {

	public String item;
	
	public Item() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		try {
			item = sm.getNextToken( _String ).lexema;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
	}

}
