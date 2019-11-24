package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;

/**
 * Regra:
 * PRIMITIVO_ADICIONAL  ->  ,  PRIMITIVO  PRIMITIVO_ADICIONAL  |  nada
 * @author Leandro Ledski
 *
 */
public class PrimitivoOpcional extends SyntaxRule {
	
	
	public List<Primitivo> listaDePrimitivos = new ArrayList<Primitivo>(4);;
	
	
	public PrimitivoOpcional() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _virgula );
			listaDePrimitivos = new ArrayList<Primitivo>();
			listaDePrimitivos.add( new Primitivo() );
			listaDePrimitivos.addAll( new PrimitivoOpcional().listaDePrimitivos );
		}
		catch( UnexpectedToken | UnfinishedCode e ) {
			sm.resetRulePointer();
		}
		
		
	}
	
	
}
