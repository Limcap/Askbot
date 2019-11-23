package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.List;

import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.TokenizedCodeManager;
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
	
	public Especialidade( TokenizedCodeManager code ) throws NotAToken, UnexpectedToken, UnfinishedCode {
		super( code );
		
		code.getNextToken( _Especialidade );
		code.getNextToken( _doisPontos );
		
		name = code.getNextToken( _String ).lexema;
		description = code.getNextToken( _String ).lexema;

		listaDeQuestoes = new ListaDeQuestoes( code ).listaDeQuestoes;
		
		listaDeTestes = new ListaDeTestes( code ).listaDeTestes;
	}

}
