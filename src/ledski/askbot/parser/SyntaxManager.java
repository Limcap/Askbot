package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxExceptions.NotAToken;
import ledski.askbot.parser.SyntaxExceptions.UnfinishedCode;
import ledski.askbot.parser.SyntaxExceptions.UnexpectedToken;

/**
 * Esta classe cria um objeto para gerenciar a manipulação dos tokens pelas regras da gramática.
 * Uma regra deve ser criada com uma instancia desse objeto como parâmetro, para que ela possa recuperar
 * os proximos tokens sem se precupar em lançar exceções. O construtor das regras cria um cópia desse objeto
 * que é passado como parâmetro porque cada regra deve ter sua própria instância dele para que os pointers não
 * se misturem.
 * @author Leandro
 *
 */
public class SyntaxManager {
	
	private static List<Token> tokenList;
	private static int pointer2 = 0;
	public static Exception savedException = null;
	public static int savedExceptionIndexOfToken;

	public static void setTokenList( List<Token> t ) {
		tokenList = t;
		pointer2 = 0;
	}
	
	
	
	public SyntaxManager() {
		this.startIndex = pointer2;
	}
	
	
	private final int startIndex;
	
	
	
	public Token getNextToken( TokenType type ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		Token t;
		try {
			if( pointer2 > tokenList.size()-1 ) {
				throw new SyntaxExceptions.UnfinishedCode();
			}
			t = tokenList.get( pointer2++ );
			if( t.type == TokenType._error ) {
				throw new SyntaxExceptions.NotAToken( t, pointer2 );
			}
			else if( t.type != type ) {
				throw new SyntaxExceptions.UnexpectedToken( t, pointer2  );
			}
		}
		catch( UnexpectedToken | NotAToken | UnfinishedCode e ) {
			saveException( e );
			throw e;
		}
		System.out.println( pointer2 + " " + t.type + " " + t.lexema );
		return t;
	}
	
	
	
	public int peekPointer() {
		return pointer2;
	}
	
	
	
	/**
	 * Quando ocorre uma exceção UnexpectedToken em uma regra opcional, a excessão é ignorada,
	 * porém deve ser salva para uso posterior, que será:
	 * 1- Se o parser encontrar um erro obrigatório com indice menor ao do erro obrigatório.
	 * 2- Se o parser chegar ao final das regras sem encontrar uma exceção obrigatória. 
	 * @param e
	 */
	public void saveException( Exception e ) {
		if( SyntaxManager.savedExceptionIndexOfToken < pointer2 ) {
			SyntaxManager.savedExceptionIndexOfToken = pointer2;
			SyntaxManager.savedException = e;
		}
	}
	
	
	
	public void rethrowSavedExceptionFromCatchBlock() throws Exception {
		throw savedException;
	}
	
	
	
	public void throwSavedExceptionAtTheEndOfStartRule() throws Exception {
		if( pointer2 <= savedExceptionIndexOfToken )
			throw savedException;
	}
	
	
	
	public boolean noSuccessYet() {
		return pointer2 == startIndex;
	}
	
	
	
	public void resetRulePointer() {
		resetRulePointer(0);
	}
	
	
	
	public void resetRulePointer( int offset ) {
		pointer2 = this.startIndex + offset;
	}
	
	
	
}
