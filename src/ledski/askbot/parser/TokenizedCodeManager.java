package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.CompilerExceptions.NonExistentToken;
import ledski.askbot.parser.CompilerExceptions.UnexpectedEndOfCode;
import ledski.askbot.parser.CompilerExceptions.UnexpectedToken;

/**
 * Esta classe cria um objeto para gerenciar a manipulação dos tokens pelas regras da gramática.
 * Uma regra deve ser criada com uma instancia desse objeto como parâmetro, para que ela possa recuperar
 * os proximos tokens sem se precupar em lançar exceções. O construtor das regras cria um cópia desse objeto
 * que é passado como parâmetro porque cada regra deve ter sua própria instância dele para que os pointers não
 * se misturem.
 * @author Leandro
 *
 */
public class TokenizedCodeManager {
	
	private static List<Token> tokenList;

	public static void setTokenList( List<Token> t ) {
		tokenList = t;
	}
	
	
	
	public TokenizedCodeManager( int pointer ) {
//		this.tokenList = tokenList;
		this.pointer = pointer;
		this.startIndex = pointer;
	}
	
	
	
	private final int startIndex;
	private int pointer;
	
	
	
	public Token getNextToken( TokenType type ) throws UnexpectedToken, NonExistentToken, UnexpectedEndOfCode {
		if( pointer > tokenList.size()-1 ) {
			throw new CompilerExceptions.UnexpectedEndOfCode("");
		}
		Token t = tokenList.get( pointer++ );
		if( t.type == TokenType._error ) {
			String msg = "\n" + t.toString() + "\nINDEX: " + pointer;
			throw new CompilerExceptions.NonExistentToken( msg );
		}
		else if( t.type != type ) {
			String msg = "\n" + t.toString() + "\nINDEX: " + pointer;
			throw new CompilerExceptions.UnexpectedToken( msg );
		}
		return t;
	}
	
	
	
	public TokenizedCodeManager advanceStartIndex() {
		return new TokenizedCodeManager( pointer );
	}
	
	
	
	public void resetRulePointer() {
		this.pointer = startIndex;
	}
	
	
	
}
