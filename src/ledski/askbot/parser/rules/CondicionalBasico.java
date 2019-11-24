package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType._Entao;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.util.PairIfThen;

/**
 * Regra:
 * CONDICIONAL_BASICO -> CONDICAO  Entao  PRIMITIVO
 * @author Leandro
 */
public class CondicionalBasico extends SyntaxRule {

	public PairIfThen pairIfThen = new PairIfThen();;
	
	public CondicionalBasico() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			pairIfThen.condicoes.add( new Condicao() );
			pairIfThen.condicoes.addAll( new CondicaoOpcional().list );
			sm.getNextToken( _Entao );
			pairIfThen.implicacao = new Primitivo();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
