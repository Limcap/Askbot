package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

public class ItemsAdicionais extends SyntaxRule {

	public List<Item> itemsAdicionais;
	
	public ItemsAdicionais() throws UnfinishedCode, NotAToken {
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _virgula );
			itemsAdicionais = new ArrayList<Item>();
			itemsAdicionais.add( new Item() );
			sm.getNextToken( _virgula );
			itemsAdicionais.addAll( new ItemsAdicionais().itemsAdicionais );
		} catch( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
	}

}
