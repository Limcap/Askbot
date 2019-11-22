package ledski.askbot.automato;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leandro Oliveira Lino de Araujo
 * @version 0.1.0
 */
public class Node {

	public Token.TokenType tokenType = Token.TokenType._none;
	
	public String nome;
	public boolean marcado = false;
	public Node invertedLink = null;
	public Map<Character, Node> directLinkMap;
	public Map<String, Node> transitionStringMap;
	
	public Node( String nome ) {
		this.nome = nome;
		this.directLinkMap = new HashMap<Character, Node>();
		this.transitionStringMap = new HashMap<String, Node>();
	}
	
	
	
	/**
	 * Retorna o novo estado se existir uma transição com o caractere passado
	 * @param c Character
	 * @return Estado
	 */
	public Node getLinkedNode(Character c) {
		Node e = directLinkMap.get( c );
		return e == null ? invertedLink : e;
	}
	
	
	
	/**
	 * Caminha por todas as rotas do autômato e escreve cada rota do inicio ao fim em uma
	 * linha do StringBuilder passado. É necessario utilizar System.out.print no StringBuilder
	 * após chamar este método para visualizar as rotas
	 * @param listaDeRotas StringBuilder onde serão escritas as rotas
	 */
	public void listarRotas( StringBuilder listaDeRotas ) {
		listarRotas( "", listaDeRotas );
	}
	private void listarRotas(String rotaSoFar, StringBuilder listaDeRotas) {
		Link[] compiledLinkList = getCompiledLinkList();
		if( compiledLinkList.length > 0 ) {
			// Se o estado possui transicoes:
			for( Link link : compiledLinkList ) {
				// se a transição for para o mesmo estado, adiciona uma rota com LOOP à lista de rotas e finaliza a
				// recursao.
				if( link.node == this ) {
					String rota = rotaSoFar +
						" (" + this.nome + ") " + Utils.legivel( link.transitionChar ) + " (LOOP)\n";
					listaDeRotas.append( rota );
				}
				// se for para outro estado, continua a montar a rota chamando mostrarRotas no proximo estado.
				else {
					String rotaSoFarUpdated = rotaSoFar +
						" (" + this.nome + ") " + Utils.legivel( link.transitionChar );
					link.node.listarRotas( rotaSoFarUpdated, listaDeRotas );	
				}
			}
		}
		// Se o estado não possui transições, adiciona rota à lista e finaliza a recursão.
		else {
			String rota = rotaSoFar + " (" + this.nome + ")\n";
			listaDeRotas.append( rota );
		}
	}
	
	
	
	/**
	 * Cria um array com todos os links dessa transição. Como um estado tem 2 tipos de links que são especificados
	 * por membros distintos da classe ( os diretos e o invertido ) esse método cria um compilado desses links em um
	 * só array para facilitar o iteração sobre eles. 
	 * @return
	 */
	private Link[] getCompiledLinkList() {
		boolean hasInvertedLink = invertedLink != null;
		int numberOfLinks = transitionStringMap.size() + (hasInvertedLink ? 1 : 0);
		Link[] full = new Link[numberOfLinks];
		int i = 0;
		for( Map.Entry<String, Node> entry : transitionStringMap.entrySet() ) {
			full[i] = new Link( entry.getKey(), entry.getValue() );
			i++;
		}
		if( hasInvertedLink ) full[i] = new Link( "[]", invertedLink );
		return full;
	}
//	@SuppressWarnings("unchecked")
//	private Pair<String,Estado>[] getFullLinkList() {
//		boolean hasInvertedLink = invertedLink != null;
//		int numberOfLinks = transitionStringMap.size() + (hasInvertedLink ? 1 : 0);
//		Link<String,Estado>[] full = (Link[])Array.newInstance( Link.class, numberOfLinks );
//		int i = 0;
//		for( Map.Entry<String, Estado> entry : transitionStringMap.entrySet() ) {
//			full[i] = new Link<String,Estado>(entry.getKey(),entry.getValue());
//			i++;
//		}
//		if( hasInvertedLink ) full[++i] = new Link<String,Estado>("[]",invertedLink);
//		return full;
//	}
	
	
	
	/**
	 * Retorna uma String que lista o nome do estado e seus links
	 */
	public String toString() {
		StringBuilder g = new StringBuilder();
		g.append( "(" + this.nome + ") :   ");
		for( Map.Entry<Character, Node> entry : directLinkMap.entrySet() ) {
			String caractere = Utils.legivel( entry.getKey() );
			g.append( caractere + " (" + entry.getValue().nome + ")   " );
		}
		if( invertedLink != null ) {
			g.append( " [] (" + invertedLink.nome + ")   " );
		}
//		if( marcado ) g.append( "fim" );
		return g.toString();
	}
	
	
	
	public Node calcNodeFromCurrentTransition_old2() throws ConflictingTransitionException {
		// se a transição não possuir nenhum char, retorna o estado do link invertido, pois uma transicao sem chars
		// é uma transição invertida sem exceções.
		if( Transition.chars.length == 0 ) return invertedLink;
		// se possuir 1 char, retorna o estado indicado por desse char
		else if( Transition.chars.length == 1 ) return directLinkMap.get( Transition.chars[0] );
		// se tiver 2 ou mais transições
		else {
			assertConflictingTransition();
 			return Transition.isInverted ? invertedLink : directLinkMap.get( Transition.chars[0] );
		}
	}
	
	
	
	public Node calcNodeFromCurrentTransition_old1() throws ConflictingTransitionException {
		// destinationNode é o valor que o char deve tem ou deve ter no charNodeMap. Se a transicao for invertida então
		// todos os chars devem ter valor null no charNodeMap. Se não for invertida então o destinationNode será
		// algum estado existente se aquele char já existir no mapa ou null se não existir.
//		System.out.println( Transition.word + " -- " + String.join( " " , Transition.path ) );
		if( Transition.chars.length == 0 ) return invertedLink;
		Node destinationNode = directLinkMap.get( Transition.chars[0] );
		// verifica se os chars da transicao ja existem no charNodeMap. No caso da transicao nao ser invertida, então
		// se algum char ja existir, é necessario que todos os outros também já existam e indiquem o memso estado. Caso
		// contrário emite um erro pois a transicão analisada conflita com alguma transição já configurada antes.
		// te e indicam o MESMO estado. Se nenhum char já
		// existir entao todas as verificacões serao null o que indica que essa transicao ainda nao existe. Se só
		// alguns chars dessa transicao já existir no mapa, 
		for( int i = 0; i < Transition.chars.length; i++ ) {
			char c = Transition.chars[i];
			Node tempNode = directLinkMap.get( c );
			if( Transition.isInverted && tempNode != null )
				throw new ConflictingTransitionException("Tentativa de anular um char de transicao existente");
			else
				if( !Transition.isInverted && tempNode != destinationNode )
					throw new ConflictingTransitionException("Ambiguidade de transicoes");
		}
		return Transition.isInverted ? invertedLink : destinationNode;
	}
	
	
	
	/**
	 * Método de construção de autômato.
	 * <br>
	 * Verifica se este estado já possui uma transição equivalente à que está sendo processada.
	 * Emite um erro se a transição for incompatível com alguma já existe, ou retorna o estado encontrado (que pode
	 * ser nulo ou não).
	 * @return um Estado ou null.
	 * @throws ConflictingTransitionException
	 */
	public Node calcNodeFromCurrentTransition() throws ConflictingTransitionException {
		// Verifica se a transição atual é conflitante com outra já inserida. Obs: só será conflitante se possuir
		// 2 ou mais chars.
		assertConflictingTransition();
		// Se passou o assert acima, a transicao é compativel com as que já existem o que significa que ou já existem
		// todos os chars dessa transicao no diretLinkMap ou são todos nulos, dessa forma podemos pegar o estado do
		// char index 0 ou qualquer outro index, pois são o mesmo estado.
		return Transition.isInverted ? invertedLink : directLinkMap.get( Transition.chars[0] );
	}
	
	
	
	/**
	 * Verifica se a transição atual conflita com alguma outra já configurada. A consulta ao directLinkMap sobre os
	 * chars da transição atual devem retornar sempre o mesmo estado ou sempre nulo para que a ela seja compatível.
	 * <br>
	 * <br>Exemplo: Se existem os chars 'a' e 'b' mas não 'c' no mapa de links diretos entao a transição [ac] é:
	 * <ul>
	 * <li>incompatível se o valor de 'a' não for null, pois visto  que 'c' não exite no mapa, sua consulta retorna
	 *     null, enquanto que a consulta de 'a' não retorna null.
	 * </li>
	 * <li>compatível se o valor de 'a' for null pois tanto a consulta de 'a' quanto de 'c' retornarão null. Um char
	 *     mapeia para null somente se ele tiver vindo de uma transição invertida com exceções, por exemplo -[a]
	 * </li>
	 * </ul>
	 * @throws ConflictingTransitionException
	 */
	private void assertConflictingTransition() throws ConflictingTransitionException {
		// Se a transição atual tiver 0 ou 1 char somente, ela não vai conflitar com nenhuma outra.
		if( Transition.chars.length < 2 ) return;
		Node e0 = directLinkMap.get( Transition.chars[0] );
		// Procura os chars da transição atual no mapa de links direto e verifica se todos indicam o mesmo estado.
		// - Se todos indicam null é porque não existe nenhuma transição anterior com os chars da atual.
		// - Se todos indicarem um estado é porque existe uma transição compatível com a atual.
		// - Se alguns indicarem um estado e outros null ou outro estado é porque já existem outras transições
		//   incompatíveis.
		for( int i = 1; i < Transition.chars.length; i++ )
			if( e0 != directLinkMap.get( Transition.chars[i] ) )
				throw new ConflictingTransitionException("Ambiguidade de transicoes");
		// e se a transicao for invertida, esses estados devem ser nulos, para que seja compatível
		if( Transition.isInverted && e0 != null)
			throw new ConflictingTransitionException("Ambiguidade de transicoes");
	}
	
	
	
	/**
	 * Conecta o estado ao proximo estado usando os caracteres da transição atual.
	 * Chamado pela classe Automato ao construir o automato. Só é chamado se o teste para verificar se já existe
	 * um estado para a transição atual retornar null, ou seja, não existir estado.
	 * @return
	 */
	public Node createLinks() {
		// constroi o nome do estado de destino
		String name = Transition.tokenType.name() + "-" + Transition.nextNodeDepth + ( Transition.isLast() ? "-fim" : "" );
		// decide qual é o estado de destino, se é um novo estado ou se é o próprio estado
		Node destinationNode = Transition.isSelfLoop ? this : new Node( name );
		// decide qual é o estado que será inserido no mapa de links ( que é o mapa de char -> node)
		Node nodeForLinkMap = Transition.isInverted ? null : destinationNode;
		// decide qual é o estado do link invertido deste estado. Somente se o ele ainda não tiver sido definido,
		// senão caso já tenha sido criada uma transicao invertida em loop ( []* ) e esta transicao agora seja normal,
		// vai apagar o link invertido.
		if( invertedLink == null )
			invertedLink = Transition.isInverted ? destinationNode : null ;
		// adiciona todos os links diretos ao mapa de links
		for( int i = 0; i < Transition.chars.length; i++ ) {
			directLinkMap.put( Transition.chars[i], nodeForLinkMap );
			transitionStringMap.put( Transition.transitionStr, nodeForLinkMap ); // para verbosidade
		}
		return destinationNode;
	}
	
}
