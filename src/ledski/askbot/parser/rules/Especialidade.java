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
	
	public Especialidade( SyntaxManager rm ) throws Exception, NotAToken, UnexpectedToken, UnfinishedCode {
		super( rm );
		SyntaxManager sm = SyntaxManager.optionalRule( true );
		
		
		rm.getNextToken( _Especialidade );
		rm.getNextToken( _doisPontos );
		
		name = rm.getNextToken( _String ).lexema;
		description = rm.getNextToken( _String ).lexema;

		listaDeQuestoes = new ListaDeQuestoes( rm ).listaDeQuestoes;
		
		listaDeTestes = new ListaDeTestes( rm ).listaDeTestes;
		
		sm.throwSavedException();
	}

}
