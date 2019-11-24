package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * QUESTAO_OPCIONAL -> QUESTAO  QUESTAO_OPCIONAL | vazio
 * @author Leandro
 *
 */
public class QuestaoOpcional extends SyntaxRule {
	
	
	public List<Questao> listaDeQuestoes;
	
	
	public QuestaoOpcional() throws Exception { // UnfinishedCode, NotAToken
		SyntaxManager sm = new SyntaxManager();
		
		
		listaDeQuestoes = new ArrayList<Questao>();
		// Como a regra pode ser vazia, exceções de token são ignoradas e o ponteiro de tokens do
		// tekenizedCodeManager é reposicionado para o índice onde estava no início da regra.
		try {
			listaDeQuestoes.add( new Questao() );
			listaDeQuestoes.addAll( new QuestaoOpcional().listaDeQuestoes );
		}
		catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}
	
	
}
