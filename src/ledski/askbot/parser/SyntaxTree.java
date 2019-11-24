package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.parser.rules.Especialidade;
import ledski.askbot.parser.rules.QuestaoOpcional;
import ledski.askbot.parser.rules.TesteOpcional;
import ledski.askbot.parser.rules.Questao;
import ledski.askbot.parser.rules.Teste;

public class SyntaxTree {
	
	public Especialidade especialidade;
	public List<Questao> listaDeQuestoes;
	public List<Teste> listaDeTestes;

	public SyntaxTree( List<Token> tokenList ) throws Exception {
		
		SyntaxManager.setTokenList( tokenList );
		SyntaxManager sm = new SyntaxManager();
		
		especialidade = new Especialidade();
		listaDeQuestoes = new QuestaoOpcional().listaDeQuestoes;
		listaDeTestes = new TesteOpcional().listaDeTestes;
		
		sm.throwSavedExceptionAtTheEndOfStartRule();
		
	}



}
