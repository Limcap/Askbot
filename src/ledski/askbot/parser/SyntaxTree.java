package ledski.askbot.parser;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.parser.rules.Especialidade;
import ledski.askbot.parser.rules.QuestaoOpcional;
import ledski.askbot.parser.rules.TesteOpcional;
import ledski.askbot.parser.rules.Questao;
import ledski.askbot.parser.rules.Teste;

/**
 * SyntaxTree faz o papel da regra inicial da gramática. É uma regra especial pois recebe como parâmetro a lista de
 * tokens gerada pelo Lexer e não faz tratamento de exceção como as demais fazem, ao invés disso, ela emite as exceções
 * que recebe, pois caso alguma regra subsequente encontre uma exceção de token, ela faz uma de duas coisas: ou emite
 * de volta até a primeira regra, ou salva a regra no SyntaxManager em caso de regras não-obrigatórias, para análise
 * posterior. Essa análise posterior ocorre no ultimo comando dessa classe,
 * que é  o SyntaxManager.throwSavedExceptionAteTheEndOfStartRule
 * @author Leandro Ledski
 *
 */
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
