package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * PRIMITIVO_ADICIONAL -> , PRIMITIVO  PRIMITIVO_ADICIONAL | vazio
 * @author Leandro
 *
 */
public class PrimitivoAdicional extends SyntaxRule {

	public List<Primitivo> itemsAdicionais;
	
	public PrimitivoAdicional() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		itemsAdicionais = new ArrayList<Primitivo>(4);
		
		
		try {
			sm.getNextToken( _virgula );
			itemsAdicionais = new ArrayList<Primitivo>();
			itemsAdicionais.add( new Primitivo() );
			itemsAdicionais.addAll( new PrimitivoAdicional().itemsAdicionais );
		} catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}

}
