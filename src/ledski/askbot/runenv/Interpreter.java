package ledski.askbot.runenv;

import java.util.HashMap;
import java.util.Map;

import ledski.askbot.lexer.Token.TokenType;
import ledski.askbot.parser.SyntaxTree;
import ledski.askbot.parser.rules.Condicao;
import ledski.askbot.parser.rules.CondicionalBasica;
import ledski.askbot.parser.rules.Questao;
import ledski.askbot.parser.rules.Teste;
import ledski.util.Gridder;

/**
 * Essa classe é o interpretador da arvore sintática. Ela é o ambiente de execução do código. Ela funciona
 * recebendo um input do usuario na forma String, processa esse input e retorna uma resposta ao usuário, e o ciclo
 * se repete até que todas as questões e todos os testes tenham sido enviados ao usuário. O método que deve
 * ser chamado para isso é o {@link #next(String)}. Os outros métodos públicos {@link #start()} e {@link #reset()},
 * auxiliam na inicialização e reinicialização do interpretador.
 * @author Leandro
 * @version 0.1
 */
public class Interpreter {
	private static SyntaxTree tree;
	private static int currentQuestaoIndex = -1;
	private static boolean esperandoInputValido = false;
	private static Map<String, Resposta> mapaDeVariaveis;
	
	private static final byte INICIO = 0;
	private static final byte QUESTAO = 1;
	private static final byte TESTE = 2;
	
	
	
	
	/**
	 * Determina qual a árvore sintática o interpretador deve usar.
	 * @param tree2
	 */
	public static void setSyntaxTree( SyntaxTree tree2 ) {
		tree = tree2;
	}





	/**
	 * Reinicia o interpretador para recomeçar o sistema.
	 */
	public static void reset() {
		mapaDeVariaveis = new HashMap<String, Resposta>();
		currentQuestaoIndex = -1;
		esperandoInputValido = false;
	}





	/**
	 * Inicia o interpretador, enviando o primeiro output.
	 * @return O String do output
	 */
	public static String start() {
		reset();
		return next( null );
	}





	/**
	 * Informa qual o estágio da execução no momento.
	 * O métodos {@link #estagioInicio()} e {@link #estagioQuestao(String)} são os métodos que fazem mdança de estágio.
	 * E os estágios só devem mudar ao receber um input do usuário, e o input for váliido.
	 * @return int no formato de uma constante
	 */
	private static int currentEstagio() {
		if( currentQuestaoIndex < 0 ) return INICIO;
		else if( currentQuestaoIndex < tree.questoes.size() ) return QUESTAO;
		else return TESTE;
	}





	/**
	 * Recebe o input do usuário e responde.
	 * @param input O input do usuário
	 * @return O String de output
	 */
	public static String next( String input ) {
		String output = "";
		if( currentEstagio() == INICIO )
			output = estagioInicio().toString();
		// na segunda iteração do estágio INICIO, o estágio já é alterado para QUESTAO, então já entra nesse
		// segundo if na mesma iteração.
		if( currentEstagio() == QUESTAO )
			output = estagioQuestao( input ).toString();
		// Ao receber um input válido quando já está na última questão, o estágio é alterado para TESTE e então
		// entra no if abaixo na mesma iteração.
		if( currentEstagio() == TESTE )
			output = estagioTeste();
		return output;
	}
	
	
	
	
	
	/**
	 * Procedimentos do estágio inicial.
	 * @return Output como um StringBuilder
	 */
	private static StringBuilder estagioInicio() {
		StringBuilder output = new StringBuilder();
		writeLn( output, tree.especialidade.name );
		writeLn( output, tree.especialidade.description );
		skipLines( output, 1);
		writeLn( output, "Quando estiver pronto para começar, pressione Enter.\n\n" );
		// Esse if faz com que assim que iniciar, o estagio início não passse direto para o estágio Questao.
		// Vai ficar esperando por um input do usuário, e aí muda de estágio.
		if( esperandoInputValido == true ) {
			esperandoInputValido = false;
			currentQuestaoIndex = 0; // sai do estagio INICIO para o estagio QUESTAO
		}
		else {
			esperandoInputValido = true;
		}
		return output;
	}
	
	
	
	
	
	/**
	 * Processamento do estágio QUESTAO. Recebe o input do usuário, processa-o, e retorna
	 * o outpur como um StringBuilder.
	 * @param input o input do usuário.
	 * @return
	 */
	private static StringBuilder estagioQuestao( String input ) {
		StringBuilder output = new StringBuilder();
		
		// Começa como true, pois se não houver enviado nenhum questão ainda, qualquer input deve ser
		// aceito para enviar a próxima questão.
		boolean inputFoiValidado = true;


		// Se já enviou uma questao e precisa receber um input validá-lo, entra nessa bloco para processar
		// o input e retorna se ele é valido ou nao.
		if( esperandoInputValido )
			inputFoiValidado = processarInput( input, output );

		// Se estava esperando um input para a questão feita anteriormente e recebeu um input valido, então
		// configura as variáveis para ir pra proxima questão
		if( esperandoInputValido && inputFoiValidado ) {
			esperandoInputValido = false;
			currentQuestaoIndex++;
		}
		
		// Vai entrar nesse caso se tiver acabado de entrar no estágio Questao ou se tiver emitido uma questão na
		// iteração anterior.
		if( !esperandoInputValido ) {
			// Envia a próxima questão, se houver
			if( currentQuestaoIndex < tree.questoes.size() ) {
				output.append( elaborarQuestao() );
				esperandoInputValido = true;
			}
		}

		return output;
	}
	
	
	
	
	
	/**
	 * Retorna a fala do bot sobre a Questao indicada em {@link #currentQuestaoIndex}
	 * @param q
	 * @return
	 */
	private static StringBuilder elaborarQuestao(){
		if( currentQuestaoIndex >= tree.questoes.size() ) return null;
		Questao q = tree.questoes.get( currentQuestaoIndex );
		StringBuilder sb = new StringBuilder();
		writeLn( sb, q.textoDaQuestao );
		if( q.arrayDeResposta.range == null ) {
			writeLn( sb, "Digite o número correspondente à resposta" );
			for( int i = 0; i < q.arrayDeResposta.items.size(); i++ ) {
				String s = q.arrayDeResposta.items.get( i ).valor;
				sb.append( i + ". " + removeQuotes( s ) + "\n" );
			}
		}
		else {
			writeLn( sb, "Digite um número entre " +
				q.arrayDeResposta.range.min + " e " + q.arrayDeResposta.range.max
			);
		}
		return sb;
	}
	
	
	
	
	
	/**
	 * Processa o input do usuário, se for um input válido, retorna true, senão, falso.
	 * @param input O input do usuário
	 * @return boolean
	 */
	private static boolean processarInput( String input, StringBuilder output ) {
		// Se ainda nou houver enviado nenhum output, qualquer input é válido, pois sinaliza que é
		// pra começar a enviar as questões.
//		if( currentQuestaoIndex == -1 ) return true;
		
		// Caso já tenha enviado uma questão, o input será analisado como resposta à essa questão.
		Resposta r = identificarResposta( input );
		if( r != null ) {
			armazernarResposta( r );
			output.append( elaborarResposta( r ) );
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * Transforma a intancia da classe Resposta passada em um String.
	 * @param r Instância da classe Resposta
	 * @return String
	 */
	private static String elaborarResposta( Resposta r ) {
		armazernarResposta( r );
		String valorString;
		if( r.numInteger != null ) valorString = String.valueOf( r.numInteger ); 
		else if( r.numDouble != null ) valorString = String.valueOf( r.numDouble );
		else valorString = r.string;
		return "\n\nVocê respodeu: " + valorString + "\n\n";
	}
	
	
	
	
	
	/**
	 * Armazena a resposta de uma questão em sua variável correspondente, no {@link #mapaDeVariaveis}
	 * @param v
	 */
	private static void armazernarResposta( Resposta v ) {
		mapaDeVariaveis.put( v.questao.variavel, v );
	}
	
	
	
	
	
	/**
	 * Compara o input do usuario com o array de resposta para retornar o valor da resposta,
	 * no caso do input ser só um indice para um elemento do array de resposta.
	 * @param input O input do usuário
	 * @return Um objeto Resposta
	 */
	private static Resposta identificarResposta( String input ) {
		// Sempre identifica a resposta da Questao anterior ao indice atual, pois sempre que se manda uma Questao 
		Questao q = tree.questoes.get( currentQuestaoIndex );
		Resposta r = new Resposta();
		r.questao = q;
		try {
			// ARRAY DE RANGE DE DOUBLE
			if( isArrayRespostaRange(q) && input.contains( "." ) ) {
				r.numDouble = Double.valueOf( input );
				if( r.numDouble >= q.arrayDeResposta.range.min && r.numDouble <= q.arrayDeResposta.range.max ) {
					r.string = input;
					r.tipo = TokenType._Numero;
					return r;
				}
			}
			// ARRAY DE RANGE DE INTEIRO
			else if( isArrayRespostaRange(q) ) {
				r.numInteger = Integer.parseInt( input );
				if( r.numInteger >= q.arrayDeResposta.range.min && r.numInteger <= q.arrayDeResposta.range.max ) {
					r.string = input;
					r.tipo = TokenType._Numero;
					return r;
				}
			}
			// ARRAY DE RESPOSTA DE STRING
			else {
				int index = Integer.parseInt( input );
				if( index > -1 && index < q.arrayDeResposta.items.size() ) {
					r.string = q.arrayDeResposta.items.get( index ).valor;
					r.tipo = TokenType._String;
					return r;
				}
			}
		}
		catch (NumberFormatException e) {}
		return null;
	}
	
	
	
	
	
	/**
	 * É o método que interpreta os Testes. Está funcionando mas é só a primeira versão. Precisa de ser dividido
	 * em múltiplos métodos para melhorar o entendimento.
	 * @return
	 */
	private static String estagioTeste() {
		Teste t = tree.testes.get( 0 );
		String resultado = null;
		for( CondicionalBasica b : t.condicionais ) {
			boolean condicaoCompleta = false;
			for( Condicao c : b.condicoes ) {
				boolean condicaoUnica = false;
				Resposta termo1;
				Resposta termo2;
				// se os termos forem cariaveis, recupera seus valores do mapa de variaveis
				if( c.termo1.tipo == TokenType._qVar ) {
					System.out.println( "=========  " + c.termo1.valor );
					termo1 = mapaDeVariaveis.get( c.termo1.valor );
				}
				else termo1 = new Resposta(c.termo1.tipo, c.termo1.valor);
				if( c.termo2.tipo == TokenType._qVar ) {
					System.out.println( "=========  " + c.termo2.valor );
					termo2 = mapaDeVariaveis.get( c.termo2.valor );
				}
				else termo2 = new Resposta(c.termo2.tipo, c.termo2.valor);
				// Faz a comparacao dos termos
				if((
					termo1.tipo == TokenType._String &&
					termo2.tipo == TokenType._String &&
					c.opComparacao.tipo == TokenType._igualIgual
				)||(
					termo1.tipo == TokenType._Numero &&
					termo2.tipo == TokenType._Numero
				)) {
					if( c.opComparacao.tipo == TokenType._igualIgual ) {
						condicaoUnica = termo1.string.equals( termo2.string );
					}
					else if( c.opComparacao.tipo == TokenType._diferente ) {
						condicaoUnica = !termo1.string.equals( termo2.string );
					}
					else {
						int num1 = Integer.valueOf( termo1.string );
						int num2 = Integer.valueOf( termo2.string );
						switch( c.opComparacao.tipo ) {
							case _maior: condicaoUnica = num1 > num2; break;
							case _maiorIgual: condicaoUnica = num1 >= num2; break;
							case _menor: condicaoUnica = num1 < num2; break;
							case _menorIgual: condicaoUnica = num1 <= num2; break;
						default:
							break;
						}
					}
				}
				if( c.opLogico != null ) {
					if( c.opLogico.operador == TokenType._e ) {
						condicaoCompleta = condicaoCompleta && condicaoUnica;
					}
					else if( c.opLogico.operador == TokenType._ou ) {
						if( condicaoCompleta ) break;
						else condicaoCompleta = condicaoCompleta || condicaoUnica;
					}
				}
				else {
					condicaoCompleta = condicaoUnica;
				}
			}
			if( condicaoCompleta ) {
				resultado = b.implicacao.valor;
				break;
			}
		}
		if( resultado == null ) resultado = replaceVariables( t.resultadoPadrao.valor );
		return "\n\n" + removeQuotes( t.textoInicial ) + "\n" + removeQuotes( resultado );
	}
	
	
	
	
	
	// =================================================================================================================
	// METODOS E CLASSES AUXILIARES
	//==================================================================================================================
	
	private static String replaceVariables( String s ) {
		String newS = s;
		for( String key : mapaDeVariaveis.keySet() ) {
			String valorNovo = removeQuotes( mapaDeVariaveis.get( key ).string );
			newS = newS.replace( key, valorNovo );
		}
		return newS;
	}
	
	
	
	
	
	private static boolean isArrayRespostaRange( Questao q ) {
		return q.arrayDeResposta.items == null;
	}
	
	
	
	
	
	private static void writeLn( StringBuilder sb, String s ) {
		sb.append( removeQuotes( s ) + ( s.endsWith( "\n" ) ? "" : "\n" ) );
	}
	
	
	
	
	
	private static void skipLines( StringBuilder sb, int i ) {
		sb.append(  Gridder.repeat( "\n", i ) );
	}
	
	
	
	
	
	private static String removeQuotes( String s ) {
		if( s.startsWith("\"") && s.endsWith("\"") )
		return s.substring( 1, s.length()-1 );
		else return s;
	}
	
	
	
	private static class Resposta {
		public Resposta() {}
		public Resposta( TokenType tipo, String valor ) {
			this.string = valor;
			this.tipo = tipo;
		}
		Integer numInteger;
		Double numDouble;
		String string;
		TokenType tipo;
		Questao questao;
	}
}
