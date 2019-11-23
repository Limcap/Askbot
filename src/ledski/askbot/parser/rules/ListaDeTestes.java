package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.CompilerExceptions.NonExistentToken;
import ledski.askbot.parser.CompilerExceptions.UnexpectedEndOfCode;
import ledski.askbot.parser.CompilerExceptions.UnexpectedToken;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;

/**
 * Regra:
 * LISTA_DE_TESTES -> TESTE  LISTA_DE_TESTES | vazio
 * @author Leandro Ledski
 */
public class ListaDeTestes extends GrammarRule {
	
	public List<Teste> listaDeTestes;

	public ListaDeTestes( TokenizedCodeManager code ) throws UnexpectedEndOfCode, NonExistentToken {
		super( code );
		
		listaDeTestes = new ArrayList<Teste>();
		try {
			listaDeTestes.add( new Teste( code ) );
			listaDeTestes.addAll( new ListaDeTestes( code ).listaDeTestes );
		} catch( UnexpectedToken e ) {
			code.resetRulePointer();
		}
	}

}
