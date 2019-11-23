package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.CompilerExceptions.*;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * ESPECIALIDADE -> Especialidade  :  String String
 */
public class Especialidade extends GrammarRule {
	
	public String name;
	public String description;

	
	public Especialidade() throws Exception, NotAToken, UnexpectedToken, UnfinishedCode {
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _Especialidade );
			sm.getNextToken( _doisPontos );			
			name = sm.getNextToken( _String ).lexema;
			description = sm.getNextToken( _String ).lexema;
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		


	}

}
