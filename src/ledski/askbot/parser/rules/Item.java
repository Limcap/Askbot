package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.NotAToken;
import ledski.askbot.parser.SyntaxExceptions.UnfinishedCode;
import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

public class Item extends SyntaxRule {

	public String item;
	
	public Item() throws UnexpectedToken, NotAToken, UnfinishedCode {
		SyntaxManager sm = new SyntaxManager();
		
		item = sm.getNextToken( _String ).lexema;
	}

}
