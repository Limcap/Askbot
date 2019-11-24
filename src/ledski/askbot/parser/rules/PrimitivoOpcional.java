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
public class PrimitivoOpcional extends SyntaxRule {

	public List<Primitivo> listaDePrimitivos;
	
	public PrimitivoOpcional() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		listaDePrimitivos = new ArrayList<Primitivo>(4);
		
		
		try {
			sm.getNextToken( _virgula );
			listaDePrimitivos = new ArrayList<Primitivo>();
			listaDePrimitivos.add( new Primitivo() );
			listaDePrimitivos.addAll( new PrimitivoOpcional().listaDePrimitivos );
		} catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}

}
