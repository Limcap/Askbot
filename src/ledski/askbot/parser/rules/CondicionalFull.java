package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.util.PairIfThen;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * CONDICIONAL_FULL -> Se  CONDICIONAL_BASICO  CONDICAO_OPCIONAL  Senao  PRIMITIVO
 * @author Leandro
 */
public class CondicionalFull extends SyntaxRule {

	public List<PairIfThen> lista = new ArrayList<PairIfThen>();
	
	public CondicionalFull() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {	
			// Se...Entao
			sm.getNextToken( _Se );
			lista.add( new CondicionalBasico().pairIfThen );
			// OuSe...Entao
			lista.addAll( new CondicionalOuSe().lista );
			// Senao
			sm.getNextToken( _Senao );
			lista.add( new PairIfThen( null, new Primitivo() ) );
//			
//			
//			// Se...Entao
//			sm.getNextToken( _Se );
//			Condicao comparacao = new Condicao();
//			sm.getNextToken( _Entao );
//			Primitivo consequencia = new Primitivo();
//			lista.add(  new PairIfThen( comparacao, consequencia ) );
//			
//			// OuSe...Entao
//			lista.addAll( new CondicionalOuSe().lista );
//			
//			// Senao
//			sm.getNextToken( _Senao );
//			sm.getNextToken( _String, _Numero );
			
			
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		
	}

}
