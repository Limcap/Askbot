package ledski.askbot.parser.rules;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;

/**
 * Regra:
 * LISTA_DE_TESTES -> TESTE  LISTA_DE_TESTES | vazio
 */
public class ListaDeTestes extends GrammarRule {

	public ListaDeTestes( TokenizedCodeManager code ) {
		super( code );
		// TODO Auto-generated constructor stub
	}

}
