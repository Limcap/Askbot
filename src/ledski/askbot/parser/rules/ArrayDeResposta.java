package ledski.askbot.parser.rules;


import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;


/**
 * Regra:
 * ARRAY  ->  [  RANGE  ]  |  [  PRIMITIVO  PRIMITIVO_OPCIONAL  ]  |  [  ]
 * @author Leandro Ledski
 */
public class ArrayDeResposta extends SyntaxRule {
	
	
	public List<Primitivo> items;
	public Range range;
	
	
	public ArrayDeResposta() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		// array vazio - é reconhecido, mas nao pode.
		try {
			sm.getNextToken( _colchete1 );
			sm.getNextToken( _colchete2 );
			sm.saveException( new EmptyArray( sm.peekPointer() ) );
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		// conteudo de array de range
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _colchete1 );
			range = new Range();
			sm.getNextToken( _colchete2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// sem rethrow aqui pq é uma regra com OU, senao não executa a parte abaixo
		}
		
		
		// conteudo de array de items
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _colchete1 );
			items = new ArrayList<Primitivo>();
			items.add( new Primitivo() );
			items.addAll( new PrimitivoOpcional().listaDePrimitivos );
			sm.getNextToken( _colchete2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// mesma coisa
		}
		
		
		if( sm.noSuccessYet() )
			sm.rethrowFromCatchBlockOfEnforcedRules();
	}
	
	
}
