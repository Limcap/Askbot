package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.CompilerExceptions.*;

/**
 * Regra:
 * LISTA_DE_QUESTOES -> QUESTAO  LISTA_DE_QUESTOES | vazio
 * @author Leandro
 *
 */
public class ListaDeQuestoes extends GrammarRule {
	
	public List<Questao> listaDeQuestoes;
	
	public ListaDeQuestoes( SyntaxManager code ) throws UnfinishedCode, NotAToken {
		super( code );
		listaDeQuestoes = new ArrayList<Questao>();
	
		// Como a regra pode ser vazia, exceções de token são ignoradas e o ponteiro de tokens do
		// tekenizedCodeManager é reposicionado para o índice onde estava no início da regra.
		try {
			listaDeQuestoes.add( new Questao( code ) );
			listaDeQuestoes.addAll( new ListaDeQuestoes( code ).listaDeQuestoes );
		}
		catch( UnexpectedToken e ) {
			code.resetRulePointer();
		}

	}

}