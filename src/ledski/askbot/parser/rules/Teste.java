package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.CompilerExceptions.*;

/** TESTE -> Teste  tVar  :  String  CONDICAO
 */
public class Teste extends GrammarRule {

	public String variavel;
	public String texto;
//	public Condicao condicao;
	
	
	public Teste( SyntaxManager code ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		super( code );
		
		try {
			code.getNextToken( _Teste );
			
			variavel = code.getNextToken( _tVar ).lexema;
			
			code.getNextToken( _doisPontos );
			
			code.getNextToken( _String );
//			condicao = new Condicao( code );
		}
		catch( UnexpectedToken e ) {
			code.resetRulePointer();
			throw e;
		}
		
	}

}
