package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType._String;
import static ledski.askbot.lexer.Token.TokenType._Teste;
import static ledski.askbot.lexer.Token.TokenType._doisPontos;
import static ledski.askbot.lexer.Token.TokenType._tVar;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/** TESTE -> Teste  tVar  :  String  LOGICA_DO_TESTE
 */
public class Teste extends SyntaxRule {

	public String variavel;
	public String texto;
	public CondicionalFull condicional;
//	public Condicao condicao;
	
	
	public Teste() throws Exception { //UnexpectedToken, NotAToken, UnfinishedCode
		SyntaxManager sm = new SyntaxManager();
		
		try {
			sm.getNextToken( _Teste );
			variavel = sm.getNextToken( _tVar ).lexema;
			sm.getNextToken( _doisPontos );
			texto = sm.getNextToken( _String ).lexema;
			condicional = new CondicionalFull();
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
	}

}
