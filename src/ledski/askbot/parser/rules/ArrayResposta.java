package ledski.askbot.parser.rules;

import static ledski.askbot.lexer.Token.TokenType.*;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.CompilerExceptions.*;
import ledski.askbot.parser.GrammarRule;
import ledski.askbot.parser.SyntaxManager;


/**
 * Regra:
 * ARRAY_RESPOSTA -> [  RANGE  ]  |  [  ITEMS  ]
 * ARRAY_RESPOSTA -> [  NUMERO - NUMERO  ]  |  [  LISTA_DE_ITEM  ] |  [  ]
 * @author Leandro Ledski
 */
public class ArrayResposta extends GrammarRule {

	public byte tipoDeArray;
	public Double min;
	public Double max;
	public boolean isDecimal;
	public List<Item> listaDeItems;
	
	public ArrayResposta() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		// array vazio - é reconhecido, mas nao pode.
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _chave1 );
			sm.getNextToken( _chave2 );
			sm.saveException( new EmptyArray( sm.peekPointer() ) );
			sm.rethrowSavedExceptionFromCatchBlock();
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
		}
		
		
		// conteudo de array de range
		try {
			sm.getNextToken( _chave1 );
			min = Double.valueOf( sm.getNextToken( _Inteiro ).lexema );
			sm.getNextToken( _menos );
			max = Double.valueOf( sm.getNextToken( _Inteiro ).lexema );
			sm.getNextToken( _chave2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// sem rethrow aqui pq é uma regra com OU, senao não executa a parte abaixo
		}
		
		
		// conteudo de array de items
		if( sm.noSuccessYet() ) try {
			sm.getNextToken( _chave1 );
			listaDeItems = new ArrayList<Item>();
			listaDeItems.add( new Item() );
			listaDeItems.addAll( new ItemsAdicionais().itemsAdicionais );
			sm.getNextToken( _chave2 );
		}
		catch ( UnexpectedToken e ) {
			sm.resetRulePointer();
			// mesma coisa
		}
		
		
		if( sm.noSuccessYet() )
			sm.rethrowSavedExceptionFromCatchBlock();
	}

}
