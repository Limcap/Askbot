package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/**
 * Regra:
 * CONDICIONAL_BASICO  ->  CONDICAO  Entao  PRIMITIVO
 * @author Leandro Ledski
 */
public class CondicionalBasica extends SyntaxRule {
	
	
	public List<Condicao> condicoes = new ArrayList<Condicao>();
	public Primitivo implicacao;
	
	
	public CondicionalBasica() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			condicoes.add( new Condicao() );
			condicoes.addAll( new CondicaoOpcional().list );
			sm.getNextToken( _Entao );
			implicacao = new Primitivo();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
