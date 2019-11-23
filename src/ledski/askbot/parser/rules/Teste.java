package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxExceptions.*;

/** TESTE -> Teste  tVar  :  String  CONDICAO
 */
public class Teste extends SyntaxRule {

	public String variavel;
	public String texto;
//	public Condicao condicao;
	
	
	public Teste() throws Exception { //UnexpectedToken, NotAToken, UnfinishedCode
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _Teste );
			
			variavel = sm.getNextToken( _tVar ).lexema;
			
			sm.getNextToken( _doisPontos );
			
			sm.getNextToken( _String );
//			condicao = new Condicao( code );
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
	}

}
