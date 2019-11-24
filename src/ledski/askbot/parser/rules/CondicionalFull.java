package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/**
 * Regra:
 * CONDICIONAL_FULL  ->  Se  CONDICIONAL_BASICO  CONDICIONAIS_OPCIONAIS  Senao  PRIMITIVO
 * @author Leandro Ledski
 */
public class CondicionalFull extends SyntaxRule {
	
	
	public List<CondicionalBasica> condicionaisBasicas = new ArrayList<CondicionalBasica>();
	public Primitivo resultadoPadrao;
	
	
	public CondicionalFull() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {	
			// Se...Entao
			sm.getNextToken( _Se );
			condicionaisBasicas.add( new CondicionalBasica() );
			// OuSe...Entao
			condicionaisBasicas.addAll( new CondicionalOuSe().condicionaisBasicas );
			// Senao
			sm.getNextToken( _Senao );
			resultadoPadrao = new Primitivo();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
