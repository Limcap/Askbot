package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.CompilerExceptions.NotAToken;
import ledski.askbot.parser.CompilerExceptions.UnfinishedCode;
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
	private static int pointer2 = 0;

	public static void setTokenList( List<Token> t ) {
		tokenList = t;
	}
	
	
	
	public TokenizedCodeManager( int startIndex ) {
		this.startIndex = startIndex;
	}
	
	
	
	private final int startIndex;
	
	
	
	public Token getNextToken( TokenType type ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		if( pointer2 > tokenList.size()-1 ) {
			throw new CompilerExceptions.UnfinishedCode("");
		}
		Token t = tokenList.get( pointer2++ );
		if( t.type == TokenType._error ) {
			String msg = "\n" + t.toString() + "\nINDEX: " + pointer2;
			throw new CompilerExceptions.NotAToken( msg );
		}
		else if( t.type != type ) {
			String msg = "\n" + t.toString() + "\nINDEX: " + pointer2;
			throw new CompilerExceptions.UnexpectedToken( msg );
		}
		System.out.println( pointer2 + " " + t.type + " " + t.lexema );
		return t;
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
