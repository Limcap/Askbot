package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;
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
	
	
	public Questao( TokenizedCodeManager code ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		super( code );

		try {
			code.getNextToken( _Questao );

			variavel = code.getNextToken( _qVar ).lexema;
			code.getNextToken( _doisPontos );
			questao = code.getNextToken( _String ).lexema;
		
			ArrayResposta ar = new ArrayResposta( code );
			tipoDeArray = ar.tipoDeArray;
		}
		catch ( UnexpectedToken e ) {
			System.out.println( "Questão não encontrada" );
			code.resetRulePointer();
			throw e;
		}
	}

}
