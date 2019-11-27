package ledski.askbot.parser;

import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import ledski.askbot.lexer.Token;
import ledski.askbot.lexer.Token.TokenType;

/**
 * Esta classe cria um objeto para gerenciar a manipulação dos tokens pelas regras da gramática.
 * Ela é responsável por pegar o proximo token e gerenciar as exceções relacionadas a esse processo.
 * A primeira coisa que uma regra deve fazer é criar um objeto SyntaxManager para que ele possa recuperar 
 * os tokens que a regra necessita. Quando um Objeto emite uma exceção, quer dizer que não conseguiu recuperar
 * o token que a regra requisitou, e então a regra deve tratar essa exceção.
 * @author Leandro Ledski
 *
 */
public class SyntaxManager {
	
	private static List<Token> tokenList;
	
	/** É o index definitivo da lista de tokens, ou seja, é o index até o qual os tokens já foram validados
	 *  pelas regras */
	private static int pointerLista = 0;
	
	/** Lugar onde exceções são salvas para análise posterior. */
	public static Exception savedException = null;
	
	/** Index do token onde a exceção salva ocorreu. */
	public static int savedExceptionIndexOfToken;

	/** Método que deve ser chamado antes de iniciar a análise das regras. para identificar a lista de tokens */
	public static void setTokenList( List<Token> t ) {
		tokenList = t;
		pointerLista = 0;
		savedException = null;
		savedExceptionIndexOfToken = 0;
	}
	
	
	
	public SyntaxManager() {
		this.pointerRegra = pointerLista;
	}
	
	
	
	/**
	 * Identifica o index do token onde a regra deve começar a analisar a lista. É um membro de instância, por isso
	 * é particular a cada regra. Quando a regra cria uma instancia dessa classe, esse index é definido no valor
	 * do {@link SyntaxManager#pointerLista}. A medida que a regra vai validando os proximos tokens o pointer1 que é
	 * static vai sendo incrementado, porém se a regra falha, o pointer1 é resetado para o valor de pointer2.
	 */
	private final int pointerRegra;
	
	
	
	/**
	 * Método que recupera os proximos tokens. Usado nas classes de regra.
	 * @param types Os tipos de token procurados.
	 * @return O token encontrado
	 * @throws UnexpectedToken
	 * @throws NotAToken
	 * @throws UnfinishedCode
	 */
	public Token getNextToken( TokenType ...types ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		Token t;
		try {
			if( pointerLista > tokenList.size()-1 )
				throw new SyntaxExceptions.UnfinishedCode( tokenList.get(tokenList.size()-1) );
			t = tokenList.get( pointerLista++ );
			if( t.type == TokenType._error )
				throw new SyntaxExceptions.NotAToken( t, pointerLista );
			boolean found = false;
			for( TokenType ty : types )
				if( ty == t.type )
					found = true;
			if( !found )
				throw new SyntaxExceptions.UnexpectedToken( t, pointerLista, types );
		}
		catch( UnexpectedToken | NotAToken | UnfinishedCode e ) {
			saveException( e );
			throw e;
		}
		System.out.println( pointerLista + " " + t.type + " " + t.lexema );
		return t;
	}
	
	
	
	/**
	 * Emite exceção se o próximo token nao for dos tipos informados.
	 * Avança o pointer para que a exceção seja lançada corretamente.
	 * @param types
	 */
	public void assertNextToken( TokenType ...types ) throws UnexpectedToken, NotAToken, UnfinishedCode {
		if( !isNextToken( types ) ) getNextToken( types );
	}
	
	
	
	/**
	 * Checa se o próximo token é de algum dos tipos informados.
	 * Não avança o pointer, não causa erro.
	 * @param types
	 * @return
	 */
	public boolean isNextToken( TokenType ... types ) {
		if( pointerLista >= tokenList.size() )
			return false;
		Token t = tokenList.get( pointerLista );
		for( TokenType ty : types )
			if( ty == t.type )
				return true;
		return false;
	}
	
	
	
	/**
	 * Retorna o indice em que o pointer se encontra.
	 * @return
	 */
	public int peekPointer() {
		return pointerLista;
	}
	
	
	
	/**
	 * Quando ocorre uma exceção UnexpectedToken em uma regra opcional, a excessão é ignorada,
	 * porém deve ser salva para uso posterior, que será:
	 * 1- Se o parser encontrar um erro obrigatório com indice menor ao do erro obrigatório.
	 * 2- Se o parser chegar ao final das regras sem encontrar uma exceção obrigatória. 
	 * @param e
	 */
	public void saveException( Exception e ) {
		if( SyntaxManager.savedExceptionIndexOfToken < pointerLista ) {
			SyntaxManager.savedExceptionIndexOfToken = pointerLista;
			SyntaxManager.savedException = e;
		}
	}
	
	
	
	/**
	 * Usado dentro do bloco catch das regras que NÃO sao opcionais. As regras que não são opcionais devem emitir
	 * as exceções caso aconteçam, porém, em vez de reemitir a exceção que ocorreu nela, emite a exceção salva.
	 * @throws Exception
	 */
	public void rethrowFromCatchBlockOfEnforcedRules() throws Exception {
		throw savedException;
	}
	
	
	
	/**
	 * Usado no final da regra inicial, para emitir qualquer exceção salva que precise ser emitida.
	 * @throws Exception
	 */
	public void throwSavedExceptionAtTheEndOfStartRule() throws Exception {
		if( pointerLista < savedExceptionIndexOfToken )
			throw savedException;
	}
	
	
	
	/**
	 * Verifica que a regra ainda não teve sucesso em reconhecer nenhum token, comparando o pointer1 e pointer2.
	 * @return boolean
	 */
	public boolean noSuccessYet() {
		return pointerLista == pointerRegra;
	}
	
	
	
	/**
	 * Reseta o pointerLista para a posição de pointerRegra.
	 */
	public void resetRulePointer() {
		resetRulePointer(0);
	}
	
	
	
	public void resetRulePointer( int offset ) {
		pointerLista = this.pointerRegra + offset;
	}
	
	
	
}
