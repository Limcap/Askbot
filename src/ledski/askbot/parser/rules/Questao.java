package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;
import ledski.askbot.parser.CompilerExceptions.*;

/** 
 * Regra:
 * QUESTAO -> Questao  qVar  :  String  ARRAY
 */
public class Questao extends GrammarRule {

	public String questao;
	public String variavel;
	public String tipoDeArray;
	public String[] array;
	
	
	public Questao( TokenizedCodeManager code ) throws UnexpectedToken, NonExistentToken, UnexpectedEndOfCode {
		super( code );
		
		code.getNextToken( _Questao );
		
		variavel = code.getNextToken( _qVar ).lexema;
		code.getNextToken( _doisPontos );
		questao = code.getNextToken( _String ).lexema;
		
	}

}
