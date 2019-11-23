package ledski.askbot.lexer;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.lexer.Token.TokenType;
import ledski.util.Gridder;

/**
 * @author Leandro Oliveira Lino de Araujo
 * @param <Tokens>
 */
public class Automato {

	// variaveis de verbosidade
	public int verboseLevel012 = 2;
	public boolean verboseWhitespaceTokens = false;
	Gridder gr1 = new Gridder( 0, 1, 'l', 'r', 3, false, false, true );
	Gridder gr2 = new Gridder( 0, 1, 'l', 'r', 3, false, false, true );

	// variaveis de navegacao do automato
	private static final String startNodeName = "inicio";
	private Node currentNode;
	private List<Node> nodeList = new ArrayList<Node>();
	private StringBuilder lexema = new StringBuilder();
	private boolean isInErrorState = false;

	/**
	 * Construtor
	 * @throws ConflictingTransitionException 
	 */
	public Automato() throws ConflictingTransitionException {
		build();
		restart();
	}
	
	// =====================================================================================
	// 			METODOS DE NAVEGAÇÃO DO AUTÔMATO                
	// =====================================================================================
	
	
	
	/**
	 * Reinicia o autômato. Descarta todo oo progresso de navegação feito.
	 */
	private void restart() {
		currentNode = nodeList.get( 0 );
		lexema = new StringBuilder();
	}
	
	
	
	/**
	 * Processa o String passado, avançando o autômato de acordo.
	 * @param s O String a ser processado
	 * @return Uma lista dos Tokens finais e de erro encontrados
	 */
	public List<Token> process( String s ) {
		if( isInErrorState ) throw new RuntimeException( "\nAtomata is in error state" );
		List<Token> resultTokens = new ArrayList<Token>();
		Token t;
		char[] charArray = s.toCharArray();
		for( int i=0; i <= charArray.length; i++ ) {// char c : s.toCharArray() ) {
			char c = i == s.length() ? '\r' : charArray[i];
			t = getNextToken( c );
			// Se o token retornado for de um estado final, entao adiciona o token na lista de resultados, reseta o
			// automato E REPROCESSA o mesmo caractere, a partir do estado inicial. Isso é necessário porque esse
			// último char já é o primeiro do próximo token.
			while( t.type != TokenType._none && t.type != TokenType._error ) {
				// Caso retorne um token vádido, adiciona o token na lista, reseta o automato
				// e processa o mesmo charactere novamente. Não diciona tokens _whitespace.
				if( t.type != TokenType._whitespace ) resultTokens.add( t );
				restart();
				t = getNextToken( c );
			}
			if( t.type == TokenType._error ) {
				resultTokens.add( t );
				break;
			}
		}
		return resultTokens;
	}
	
	
	
	/**
	 * Avança o autômato em 1 estado utilizando o char passado como link transição e retorna o token
	 * do novo estado.
	 * @param c char do link de transição
	 * @return Token
	 */
	private Token getNextToken( char c ) {
		if( currentNode == null ) return new Token(TokenType._error, null);
		Node nextNode = currentNode.getLinkedNode( c );

		verboseNextNode( c, nextNode );

		// Cria o token apropriado
		Token token;
		if( nextNode == null ) {
			lexema.append( c );
			token = new Token( TokenType._error, lexema.toString() );
			isInErrorState = true;
		}
		else if( nextNode.marcado ) {
			// Caso o estado seja marcado, o char de transição nao é incluido no lexema do token,
			// pois ele é o caractere que marca o divisor entre esse token e o próximo. Ele é
			// inserido após a criação do token pois deve ser inserido de qualquer forma para o
			// reconhecimento correto do próximo token.
			token = new Token( nextNode.tokenType, lexema.toString() );
			lexema.append( c );
		}
		else {
			lexema.append( c );
			token = new Token( TokenType._none, lexema.toString() );
		}
		currentNode = nextNode;
		return token;
	}
	
	
	
	/**
	 * Verbosidade da transição efetuada e token encontrado
	 * @param nextNode
	 */
	private void  verboseNextNode( char c, Node nextNode ) {
		if( verboseLevel012 > 0 ) {
			String nextNodeName = nextNode == null ? "X" : "(" + nextNode.nome + ")";  
			gr2.text( "("+currentNode.nome+")" ).text( "["+Utils.legivel(c)+"]" ).text( nextNodeName );
			verbose( 2, gr2 );
//			verbose( 2, "(" + currentNode.nome + ")\t[" + legivel( c ) + "]\t" + nextNodeName );
			if( nextNode != null && nextNode.marcado ) {
				gr1.text( "TOKEN: " + nextNode.tokenType.name() )
				.textLine( "LEXEMA: " + Utils.legivel( lexema.toString() ) );
				verbose( 1, gr1 );
//				verbose( 1, "TOKEN: " + nextNode.token.name() + "\nLEXEMA: " + legivel( lexema.toString() ) );
			}
		}
	}
	
	
	
	/**
	 * Retorna o token do estado atual.
	 * Não necessário para o processo interno.
	 * @return
	 */
	public Token currentToken() {
		return new Token( currentNode == null ? TokenType._error : currentNode.tokenType, lexema.toString() );
	}
	
	
	
	// =====================================================================================
	// 			METODOS DE CRIAÇÃO DOS ESTADOS E TRANSIÇOES DO AUTÔMATO                
	// =====================================================================================
	
	
	
	/**
	 * Constroi o autômato, criando estados e transições entre os estados.
	 * @throws ConflictingTransitionException
	 */
	private void build() throws ConflictingTransitionException {
		Transition.reset();
		Node node = new Node( startNodeName );
		nodeList.add( node );
		while( Transition.nextPath() ) {
			node = nodeList.get(0);
			while( Transition.next() ) {
				Node nextNode = node.calcNodeFromCurrentTransition();
				if( nextNode == null ) {
					nextNode = node.createLinks();
					// Não insere o nextNode na lista se a transição atual for um loop, pois o next node
					// será o mesmo do atual, que já terá sido inserido antes.
					if( !Transition.isSelfLoop ) nodeList.add( nextNode );
				}
				node = nextNode;
			}
			node.marcado = true;
			node.tokenType = Transition.tokenType;
		}
	}
	
	
	
	// =====================================================================================
	// 			METODOS PARA EXIBIÇÃO DE INFORMAÇÃO DO AUT�MATO NO CONSOLE                
	// =====================================================================================
	
	
	/**
	 * Mostra no console a lista de todos os estados e suas transições
	 */
	public void mostrarEstadosNoConsole() {
//		for( Map.Entry<String, Estado> entry : this.mapaDeEstados.entrySet() ) {
//			System.out.println( entry.getValue().toString() );
//		}
		for( Node e : nodeList ) {
			System.out.println( e );
		}
	}
	
	
	/**
	 * Mostra no console todos os caminhos do autômato a partir do estado inicial
	 */
	public void mostrarCaminhosNoConsole() {
		StringBuilder listaDeRotas = new StringBuilder();
//		this.mapaDeEstados.get("inicio-var").listarRotas( listaDeRotas );
		nodeList.get(0).listarRotas( listaDeRotas );
//		this.estadoInicial.listarRotas( listaDeRotas );
		System.out.println( listaDeRotas.toString() );
	}
	
	
	
	/**
	 * 
	 * @param str
	 */
	private void verbose( int level, Gridder gr ) {
		if( this.verboseLevel012 <= 1 &&
			!this.verboseWhitespaceTokens &&
			this.currentNode != null &&
			this.currentNode.tokenType == TokenType._whitespace
			) return;
		if( this.verboseLevel012 >= level ) System.out.println( gr.publish() );
	}
	
	
	
}


