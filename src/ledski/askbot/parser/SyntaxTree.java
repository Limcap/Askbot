package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.lexer.Token;
import ledski.askbot.parser.CompilerExceptions.*;
import ledski.askbot.parser.rules.Especialidade;

public class SyntaxTree {
	
	public Especialidade inicio;

	public SyntaxTree( List<Token> tokenList ) throws NonExistentToken, UnexpectedToken, UnexpectedEndOfCode {
		
		TokenizedCodeManager.setTokenList( tokenList );
		
		inicio = new Especialidade( new TokenizedCodeManager( 0 ) );
		
	}



}
