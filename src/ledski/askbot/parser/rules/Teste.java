package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;
import ledski.askbot.parser.CompilerExceptions.*;

/** TESTE -> Teste  tVar  :  String  CONDICAO
 */
public class Teste extends GrammarRule {

	public String variavel;
	public String texto;
//	public Condicao condicao;
	
	
	public Teste( TokenizedCodeManager code ) throws UnexpectedToken, NonExistentToken, UnexpectedEndOfCode {
		super( code );
		
		code.getNextToken( _Teste );
		variavel = code.getNextToken( _tVar ).lexema;
		code.getNextToken( _doisPontos );
//		condicao = new Condicao( code );
		
	}

}
