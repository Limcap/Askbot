package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxExceptions.UnfinishedCode;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * TESTE_OPCIONAL -> TESTE  TESTE_OPCIONAL | vazio
 * @author Leandro Ledski
 */
public class TesteOpcional extends SyntaxRule {
	
	public List<Teste> listaDeTestes;

	public TesteOpcional() throws Exception { // NotAToken
		SyntaxManager sm = new SyntaxManager();
		
		listaDeTestes = new ArrayList<Teste>();
		try {
			listaDeTestes.add( new Teste() );
			listaDeTestes.addAll( new TesteOpcional().listaDeTestes );
		} catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
	}

}
