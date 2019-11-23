package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * CONDICAO -> Se  LISTA_DE_COMPARACOES  Entao  String  Senao  String
 * @author Leandro
 */
public class Condicao extends SyntaxRule {

	public String valor;
	
	public Condicao() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _Se );
			new ListaDeComparacoes();
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
