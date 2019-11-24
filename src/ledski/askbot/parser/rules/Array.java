package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.parser.SyntaxRule;
import ledski.askbot.parser.SyntaxManager;


/**
 * Regra:
 * ARRAY_RESPOSTA -> [  RANGE  ]  |  [  ITEMS  ]
 * ARRAY_RESPOSTA -> [  NUMERO - NUMERO  ]  |  [  LISTA_DE_ITEM  ] |  [  ]
 * @author Leandro Ledski
 */
public class Array extends SyntaxRule {

	public byte tipoDeArray;
	public Double min;
	public Double max;
	public boolean isDecimal;
	public List<Primitivo> listaDeItems;
	
	public Array() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		// array vazio - é reconhecido, mas nao pode.
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _colchete1 );
			sm.getNextToken( _colchete2 );
			sm.saveException( new EmptyArray( sm.peekPointer() ) );
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		// conteudo de array de range
		try {
			sm.getNextToken( _colchete1 );
			min = Double.valueOf( sm.getNextToken( _Numero ).lexema );
			sm.getNextToken( _menos );
			max = Double.valueOf( sm.getNextToken( _Numero ).lexema );
			sm.getNextToken( _colchete2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// sem rethrow aqui pq é uma regra com OU, senao não executa a parte abaixo
		}
		
		
		// conteudo de array de items
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _colchete1 );
			listaDeItems = new ArrayList<Primitivo>();
			listaDeItems.add( new Primitivo() );
			listaDeItems.addAll( new PrimitivoOpcional().listaDePrimitivos );
			sm.getNextToken( _colchete2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// mesma coisa
		}
		
		
		if( sm.noSuccessYet() )
			sm.rethrowSavedExceptionFromCatchBlock();
	}

}
