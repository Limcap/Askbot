package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * ESPECIALIDADE  ->  Especialidade  :  String  String
 * @author Leandro Ledski
 */
public class Especialidade extends SyntaxRule {
	
	
	public String name;
	public String description;
	
	
	public Especialidade() throws Exception, NotAToken, UnexpectedToken, UnfinishedCode {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _Assunto );
			sm.getNextToken( _doisPontos );			
			name = sm.getNextToken( _String ).lexema;
			description = sm.getNextToken( _String ).lexema;
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		

	}
	
	
}
