package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * LISTA_DE_TESTES -> TESTE  LISTA_DE_TESTES | vazio
 * @author Leandro Ledski
 */
public class ListaDeTestes extends SyntaxRule {
	
	public List<Teste> listaDeTestes;

	public ListaDeTestes() throws Exception { // NotAToken, UnfinishedCode
		SyntaxManager sm = new SyntaxManager();
		
		listaDeTestes = new ArrayList<Teste>();
		try {
			listaDeTestes.add( new Teste() );
			listaDeTestes.addAll( new ListaDeTestes().listaDeTestes );
		} catch( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
	}

}
