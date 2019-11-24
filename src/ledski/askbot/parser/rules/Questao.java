package ledski.askbot.parser.rules;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/** 
 * Regra:
 * QUESTAO  ->  Questao  qVar  :  String  ARRAY_RESPOSTA
 * @author Leandro Ledski
 */
public class Questao extends SyntaxRule {
	
	
	public String variavel;
	public String textoDaQuestao;
	public ArrayDeResposta arrayDeResposta;
	
	
	public Questao() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _Questao );
			variavel = sm.getNextToken( _qVar ).lexema;
			sm.getNextToken( _doisPontos );
			textoDaQuestao = sm.getNextToken( _String ).lexema;
			arrayDeResposta = new ArrayDeResposta();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
