package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.List;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.CompilerExceptions.*;

/**
 * Regra:
 * ESPECIALIDADE -> Especialidade  :  String String
 */
public class Especialidade extends GrammarRule {
	
	public String name;
	public String description;
	public List<Questao> listaDeQuestoes;
	public List<Teste> listaDeTestes;
	
	public Especialidade() throws Exception, NotAToken, UnexpectedToken, UnfinishedCode {
		SyntaxManager sm = new SyntaxManager();
		
		
		sm.getNextToken( _Especialidade );
		sm.getNextToken( _doisPontos );
		
		name = sm.getNextToken( _String ).lexema;
		description = sm.getNextToken( _String ).lexema;

		listaDeQuestoes = new ListaDeQuestoes().listaDeQuestoes;
		
		listaDeTestes = new ListaDeTestes().listaDeTestes;
		
		sm.throwSavedExceptionAtTheEndOfStartRule();
	}

}
