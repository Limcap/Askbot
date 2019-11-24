package ledski.askbot.parser;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.parser.rules.Especialidade;
import ledski.askbot.parser.rules.QuestaoOpcional;
import ledski.askbot.parser.rules.TesteOpcional;
import ledski.askbot.parser.rules.Questao;
import ledski.askbot.parser.rules.Teste;

public class SyntaxTree {
	
	public Especialidade especialidade;
	public List<Questao> questoes = new ArrayList<Questao>();
	public List<Teste> testes = new ArrayList<Teste>();

	public SyntaxTree( List<Token> tokenList ) throws Exception {
		
		SyntaxManager.setTokenList( tokenList );
		SyntaxManager sm = new SyntaxManager();
		
		especialidade = new Especialidade();
		questoes.add( new Questao() ); // pelo menos 1 questao é obrigatoria
		questoes.addAll( new QuestaoOpcional().listaDeQuestoes );
		testes.add( new Teste() ); // pelo menos 1 teste é obrigatório
		testes = new TesteOpcional().testes;
		
		sm.throwSavedExceptionAtTheEndOfStartRule();
		
	}



}
