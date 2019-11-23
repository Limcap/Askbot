package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * LISTA_DE_COMPARACOES -> COMPARACAO1  COMPARACAO2
 * @author Leandro
 */
public class ListaDeComparacoes extends SyntaxRule {

	public String valor;
	
	public ListaDeComparacoes() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _Se );
			sm.getNextToken( _Entao );
			sm.getNextToken( _String );
			sm.getNextToken( _Senao );
			sm.getNextToken( _String );
			
			
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
