package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * TESTE_OPCIONAL  ->  TESTE  TESTE_OPCIONAL  |  vazio
 * @author Leandro Ledski
 */
public class TesteOpcional extends SyntaxRule {
	
	
	public List<Teste> testes;
	
	
	public TesteOpcional() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		testes = new ArrayList<Teste>();
		try {
			testes.add( new Teste() );
			testes.addAll( new TesteOpcional().testes );
		} catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}
	
	
}
