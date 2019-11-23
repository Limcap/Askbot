package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.parser.CompilerExceptions.*;
import ledski.askbot.parser.rules.Especialidade;
import ledski.askbot.parser.rules.ListaDeQuestoes;
import ledski.askbot.parser.rules.ListaDeTestes;
import ledski.askbot.parser.rules.Questao;
import ledski.askbot.parser.rules.Teste;

public class SyntaxTree {
	
	public Especialidade especialidade;
	public List<Questao> listaDeQuestoes;
	public List<Teste> listaDeTestes;

	public SyntaxTree( List<Token> tokenList ) throws Exception, NotAToken, UnexpectedToken, UnfinishedCode {
		
		SyntaxManager.setTokenList( tokenList );
		SyntaxManager sm = new SyntaxManager();
		
		especialidade = new Especialidade();
		listaDeQuestoes = new ListaDeQuestoes().listaDeQuestoes;
		listaDeTestes = new ListaDeTestes().listaDeTestes;
		
		sm.throwSavedExceptionAtTheEndOfStartRule();
		
	}



}
