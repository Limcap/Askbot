package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxExceptions.*;

/**
 * Regra:
 * LISTA_DE_QUESTOES -> QUESTAO  LISTA_DE_QUESTOES | vazio
 * @author Leandro
 *
 */
public class ListaDeQuestoes extends SyntaxRule {
	
	public List<Questao> listaDeQuestoes;
	
	public ListaDeQuestoes() throws Exception { // UnfinishedCode, NotAToken
		SyntaxManager sm = new SyntaxManager();
		
		listaDeQuestoes = new ArrayList<Questao>();
		// Como a regra pode ser vazia, exceções de token são ignoradas e o ponteiro de tokens do
		// tekenizedCodeManager é reposicionado para o índice onde estava no início da regra.
		try {
			listaDeQuestoes.add( new Questao() );
			listaDeQuestoes.addAll( new ListaDeQuestoes().listaDeQuestoes );
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
	}
	
}
