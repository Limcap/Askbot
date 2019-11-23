package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.CompilerExceptions.*;

/** 
 * Regra:
 * QUESTAO -> Questao  qVar  :  String  ARRAY_RESPOSTA
 */
public class Questao extends GrammarRule {

	public String questao;
	public String variavel;
	public byte tipoDeArray;
	public String[] items;
	public Range range;
	
	
	public Questao() throws Exception {
		SyntaxManager sm = new SyntaxManager();

		try {
			sm.getNextToken( _Questao );

			variavel = sm.getNextToken( _qVar ).lexema;
			sm.getNextToken( _doisPontos );
			questao = sm.getNextToken( _String ).lexema;
		
			ArrayResposta ar = new ArrayResposta();
			tipoDeArray = ar.tipoDeArray;
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
	}

}
