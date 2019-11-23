package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.CompilerExceptions.NotAToken;
import ledski.askbot.parser.CompilerExceptions.UnfinishedCode;
import ledski.askbot.parser.CompilerExceptions.UnexpectedToken;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;

public class Item extends GrammarRule {

	public String item;
	
	public Item() throws UnexpectedToken, NotAToken, UnfinishedCode {
		SyntaxManager sm = new SyntaxManager();
		
		item = sm.getNextToken( _String ).lexema;
	}

}
