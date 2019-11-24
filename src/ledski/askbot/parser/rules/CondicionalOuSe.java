package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;
import ledski.askbot.parser.SyntaxExceptions.UnfinishedCode;
import ledski.askbot.parser.util.PairIfThen;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * CONDICIONAL_OUSE -> OuSe CONDICIONAL_BASICO CONDICIONAL_OUSE | vazio
 * @author Leandro
 */
public class CondicionalOuSe extends SyntaxRule {

	public List<PairIfThen> lista = new ArrayList<PairIfThen>();

	public CondicionalOuSe() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		/**
		 * Sobre o comando   sm.assertNextToken( _OuSe, _Senao )   abaixo
		 * O Token _OuSe é opcional (pois está numa regra opcional, esta aqui), mas _Senao é obrigatório,
		 * pois está na regra mãe Condicao, que é obrigatória. Entao se der um erro do tipo UnexpectedToken,
		 * a mensagem vai mostrar os dois TokenTypes no "Expected:". Sem esse assert, se der erro,
		 * vai mostrar só o _OuSe, pois o indice da Exceção salva vai ser o mesmo tanto para o
		 * getNextToken(_OuSe) dessa regra e o getNextToken(_Senao) da regra mae Condicao.
		 * Como o indice vai ser o mesmo, a Exceção que ocorre depois, que é a do _Senao, não substitui
		 * a que já está salva. Ver o método saveException da classe SyntaxManager.
		 * @see SyntaxManager#saveException 
		 */
		try {
			sm.assertNextToken( _OuSe, _Senao );
			
			sm.getNextToken( _OuSe );
			lista.add(  new CondicionalBasico().pairIfThen );
			lista.addAll( new CondicionalOuSe().lista );
		}
		catch ( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}

}
