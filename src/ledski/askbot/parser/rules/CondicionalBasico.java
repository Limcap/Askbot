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

	public PairIfThen pairIfThen;
	
	public CondicionalBasico() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			PairIfThen ifthen = new PairIfThen();
			
			ifthen.condicoes.add( new Condicao() );
			ifthen.condicoes.addAll( new CondicaoOpcional().list );
			sm.getNextToken( _Entao );
			ifthen.implicacao = new Primitivo();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
		
	}

}
