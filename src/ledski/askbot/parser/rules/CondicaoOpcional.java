package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/**
 * Regra:
 * CONDICAO_OPCIONAL  ->  OP_LOGICO  CONDICAO  CONDICAO_OPCIONAL  |  nada
 * @author Leandro Ledski
 */
public class CondicaoOpcional extends SyntaxRule {
	
	
	public List<Condicao> list = new ArrayList<Condicao>();
	
	
	public CondicaoOpcional() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		/**
		 * Sobre o comando   sm.assertNextToken( _e, _ou, _Entao )   abaixo:
		 * É a mesma coisa como descrito na classe CondicaoOpcional.
		 * @see CondicaoOpcional
		 */
		try {
			sm.assertNextToken( _e, _ou, _Entao );
			// O operador logico vem primero, depois a comparacao. Depois de recuperar os dois,
			// o operador lógico é atribuido ao membro da classe Comparacao, e só entao essa comparacao é adicionada
			// à lista
			OpLogico opLogico = new OpLogico();
			Condicao condicao = new Condicao();
			condicao.opLogico = opLogico;
			list.add( condicao );
			list.addAll( new CondicaoOpcional().list );
		}
		catch ( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}
	
	
}
