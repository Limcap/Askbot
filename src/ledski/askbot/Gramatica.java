package ledski.askbot;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.automato.Token;

public class Gramatica {

	private List<Token> lista = null;
	private String errorMsg = null;
	private int errorIndice = 0;
	private List<Token.Tipo> errorTokenEsperado;
	
	public Gramatica( List<Token> listaDeTokens ) {
		this.lista = listaDeTokens;
		this.errorTokenEsperado = new ArrayList<Token.Tipo>();
	}
	
	public void verificar() {
		INICIO();
	}
	
	/**
	 * INICIO -> program  LISTA_DE_VARS  begin  LISTA_DE_COMANDOS  end
	 */
	private Resultado INICIO() {
		System.out.println("\n======================\nGRAMATICA\n======================");
		Resultado r = new Resultado( 0, 0, false );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._program ) );
		if( r.ok ) r.and( LISTA_DE_VARS( r ) );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._begin ) );
		if( r.ok ) r.and( LISTA_DE_COMANDOS( r ) );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._end ) );
		return r.fim();
	}

	/**
	 * LISTA_DE_VARS -> variavel ; LISTA_DE_VARS | vazio
	 */
	private Resultado LISTA_DE_VARS( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._variavel ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._pontoEVirgula ));
		if( r.ok ) r.and( LISTA_DE_VARS( r ));
		return r.fim();
	}
	
	/**
	 * LISTA_DE_COMANDOS -> COMANDO ; LISTA_DE_COMANDOS | vazio
	 */
	private Resultado LISTA_DE_COMANDOS( Resultado p ){
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( COMANDO( r ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._pontoEVirgula ));
		if( r.ok ) r.and( LISTA_DE_COMANDOS( r ));
		return r.fim();
	}
	
	/**
	 * COMANDO -> ATRIBUICAO | LEITURA | ESCRITA
	 */
	private Resultado COMANDO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( ATRIBUICAO( r ));
		r.or();
		if( r.ok ) r.and( LEITURA( r ));
		r.or();
		if( r.ok ) r.and( ESCRITA( r ));
		return r.fim();
	}
	
	/**
	 * ATRIBUICAO -> variavel OP_ATRIBUICAO XP_ARITMETICA_1 | variavel = XP_LOGICA_1
	 */
	private Resultado ATRIBUICAO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and(  isToken( r, ledski.askbot.Tipo._variavel ));
		r.set();
		if( r.ok ) r.and( OP_ATRIBUICAO( r ));
		if( r.ok ) r.and( XP_ARITMETICA_1( r ));
		r.or();
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._igual ));
		if( r.ok ) r.and( XP_LOGICA_1( r ));
		return r.fim();
	}
	
	/**
	 * LEITURA -> variavel = read ( TEXTO )
	 */
	private Resultado LEITURA( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._variavel ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._igual ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._read ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses1 ));
		if( r.ok ) r.and( TEXTO( r ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses2 ));
		return r.fim();
	}
	
	/**
	 * ESCRITA -> write ( TEXTO )
	 */
	private Resultado ESCRITA( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._write ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses1 ));
		if( r.ok ) r.and( TEXTO( r ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses2 ));
		return r.fim();
	}
	
	/**
	 * XP_ARITMATICA_1 -> TERMO_1 XP_ARITMETICA_2
	 */
	private Resultado XP_ARITMETICA_1( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( TERMO_1( r ));
		if( r.ok ) r.and( XP_ARITMETICA_2( r ));
		return r.fim();
	}
	
	/**
	 * XP_ARAITMETICA_2 -> OP_SOMA TERMO_1 XP_ARITMETICA_2 | vazio
	 */
	private Resultado XP_ARITMETICA_2( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( OP_SOMA( r ));
		if( r.ok ) r.and( TERMO_1( r ));
		if( r.ok ) r.and( XP_ARITMETICA_2( r ));
		return r.fim();
	}

	/**
	 * XP_LOGICA_1 -> TERMO_LOGICO_1 XP_LOGICA_2
	 */
	private Resultado XP_LOGICA_1( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( TERMO_LOGICO_1( r ));
		if( r.ok ) r.and( XP_LOGICA_2( r ));
		return r.fim();
	}
	
	/**
	 * XP_LOGICA_2 -> OP_OU XP_LOGICA2 | vazio
	 */
	private Resultado XP_LOGICA_2( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( OP_OU( r )); 
		if( r.ok ) r.and( XP_LOGICA_2( r ));
		return r.fim();
	}

	/**
	 * TERMO_1 -> FATOR TERMO_2
	 */
	private Resultado TERMO_1( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( FATOR( r ));
		if( r.ok ) r.and( TERMO_2( r ));
		return r.fim();
	}
	
	/**
	 * TERMO_2 -> OP_MULT FATOR TERMO_2 | vazio
	 */
	private Resultado TERMO_2( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( OP_MULT( r ));
		if( r.ok ) r.and( FATOR( r ));
		if( r.ok ) r.and( TERMO_2( r ));
		return r.fim();
	}

	/**
	 * TERMO_LOGICO_1 -> FATOR_LOGICO TERMO_LOGICO_2
	 */
	private Resultado TERMO_LOGICO_1( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( FATOR_LOGICO( r ));
		if( r.ok ) r.and( TERMO_LOGICO_2( r ));
		return r.fim();
	}

	/**
	 * TERMO_LOGICO_2 -> OPERADOR_E FATOR_LOGICO_1 TERMO_LOGICO_2 | vazio
	 */
	private Resultado TERMO_LOGICO_2( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraOpcional( p );
		if( r.ok ) r.and( OP_E( r ));
		if( r.ok ) r.and( FATOR_LOGICO( r ));
		if( r.ok ) r.and( TERMO_LOGICO_2( r ));
		return r.fim();
	}

	/**
	 * FATOR -> (XP_ARITMETICA_1) | variavel | numero
	 */
	private Resultado FATOR( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses1 ));
		if( r.ok ) r.and( XP_ARITMETICA_1( r ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses2 ));
		r.or();
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._variavel, ledski.askbot.Tipo._inteiro, ledski.askbot.Tipo._decimal ));
		return r.fim();
	}

	/**
	 * FATOR_LOGICO -> LITERAL_LOGICO | _variavel | (XP_LOGICA_1) | ! LITERAL_LOGICO | ! variavel | ! (XP_LOGICA_1) | RELACAO
	 */
	private Resultado FATOR_LOGICO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( RELACAO( r ));
		r.or();
		if( r.ok ) r.either( isToken( r, ledski.askbot.Tipo._nao ));
		r.set();
		if( r.ok ) r.and( LITERAL_LOGICO( r ));
		r.or();
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._variavel ));
		r.or();
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses1 ));
		if( r.ok ) r.and( XP_LOGICA_1( r ));
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._parenteses2 ));
		return r.fim();
	}

	/**
	 * OP_ATRIBUICAO -> = | += | -=
	 */
	private Resultado OP_ATRIBUICAO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._igual, ledski.askbot.Tipo._maisIgual, ledski.askbot.Tipo._menosIgual ));
		return r.fim();
	}

	/**
	 * OP_SOMA -> + | -
	 */
	private Resultado OP_SOMA( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._mais, ledski.askbot.Tipo._menos ));
		return r.fim();
	}

	/**
	 * OP_MULT -> * | / | %
	 */
	private Resultado OP_MULT( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._vezes, ledski.askbot.Tipo._divisao, ledski.askbot.Tipo._resto ));
		return r.fim();
	}

	/**
	 * OP_OU -> ||
	 */
	private Resultado OP_OU( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._ou ));
		return r.fim();
	}
	
	/**
	 * OP_E -> &&
	 */
	private Resultado OP_E( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._e ));
		return r.fim();
	}
	
	/**
	 * OP_RELACIONAL -> > | < | <= | >= | == | !=
	 */
	private Resultado OP_RELACIONAL( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._maior, ledski.askbot.Tipo._menor, ledski.askbot.Tipo._maiorIgual, ledski.askbot.Tipo._menorIgual, ledski.askbot.Tipo._igualIgual, ledski.askbot.Tipo._diferente ));
		return r.fim();
	}

	/**
	 * LITERAL_LOGICO -> 0 | 1
	 */
	private Resultado LITERAL_LOGICO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._logico0ou1 ));
		return r.fim();
	}
	
	/**
	 * RELACAO -> XP_ARITMETICA_1 OP_RELACIONAL XP_ARITMETICA_1
	 */
	private Resultado RELACAO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( XP_ARITMETICA_1( r ));
		if( r.ok ) r.and( OP_RELACIONAL( r ));
		if( r.ok ) r.and( XP_ARITMETICA_1( r ));
		return r.fim();
	}

	/**
	 * TEXTO -> string | numero | variavel
	 */
	private Resultado TEXTO( Resultado p ) {
		verboseNomeRegra( p.stackLevel );
		Resultado r = regraNormal( p );
		if( r.ok ) r.and( isToken( r, ledski.askbot.Tipo._string, ledski.askbot.Tipo._variavel, ledski.askbot.Tipo._inteiro, ledski.askbot.Tipo._decimal ));
		return r.fim();
	}


//	===========================================================================
//	===========================================================================


	/**
	 * Tenta reconhecer o token no �ndice indicado, comparando seu tipo com a lista de tipos passados.
	 * Caso n�o seja reconhecido chama o m�todo de registro de erro.
	 * Caso seja reconhecido, chama o m�todo de apagar o registro de erro anterior, por�m ele s� ser�
	 * apagado se o �ndice do token reconhecido for maior que o �ndice do erro. 
	 * @param p O objeto Resultado da regra anterior
	 * @param esperado Token.Tipo: Os tipos de token procurados.
	 * @return Resultado
	 */
	private Resultado isToken( Resultado p, Token.Tipo... esperado ) {
		Token token = this.lista.get( p.idx );
		Resultado r = regraToken( p );
		// Se o token recuperado for o esperado
		// Avan�a o �ndice e mostra o token recuperado
		if( token.tipoIn( esperado )) {
			verbose( r.stackLevel, token.toString() );
			limparErro( r );
			r.idx += 1;
			r.ok = true;
		}
		else {
			registrarErro( r, token, esperado );
		}
		return r.fim();
	}
	
	/**
	 * Chamado somente a partir do m�todo isToken quando ele n�o reconhece o token encontrado.
	 * Cada vez que uma regra � chamada, eventualmente ela testar� por um token, e se o token n�o for reconhecido
	 * este m�todo ser� chamado e registrar� um erro. Por�m nem toda as vezes isso ser� um erro de verdade,
	 * pois no caso da regra COMANDO por exemplo a subregra ATRIBUICAO pode acusar um erro, mas em seguida
	 * ser� testada a regra LEITURA e se essa reconhecer o token no �ndice onde a regra ATRIBUICAO acusou erro,
	 * isso quer dizer que n�o era um erro e o registro de erro ser� apagado. Toda chamada para registro de
	 * erro vai comparar o �ndice do novo erro e o indice do erro j� registrado. Caso o novo seja menor, o erro
	 * � ignorado, pois erros s� podem acontecer no ultimo token reconhecido. Caso seja maior, o erro anterior �
	 * substituido pelo novo, e caso o �ndice seja igual, o erro anterior � complementado.
	 */
	private void registrarErro( Resultado r, Token t, Token.Tipo[] esperado ) {
		if( this.errorMsg == null || r.idx >= this.errorIndice) {
			if( r.idx > this.errorIndice ) this.errorTokenEsperado.clear();
			for( Token.Tipo e : esperado ) this.errorTokenEsperado.add( e );
			StringBuilder sb = new StringBuilder();
			for( Token.Tipo e : this.errorTokenEsperado ) sb.append( e + " " );
			String msg = "\nErro no �ndice " + r.idx + ", lexema: '" + t.lexema + "'" +
						 "\nToken encontrado: " + t.tipo +
						 "\nToken esperado: " + sb.toString();
			this.errorMsg = msg;
			this.errorIndice = r.idx;
		}
		if( !r.ignorarErro ) {
			System.out.println( this.errorMsg );
			throw new Error();
		}
	}
	
	private void limparErro( Resultado r ) {
		if( r.idx > this.errorIndice ) {
			this.errorMsg = null;
			this.errorIndice = 0;
			this.errorTokenEsperado.clear();
		}
	}
	
	private Resultado regraToken( Resultado anterior ) {
		Resultado r = new Resultado( anterior.idx, anterior.stackLevel, false );
		r.ignorarErro = anterior.ignorarErro;
		r.ok = false;
		return r;
	}
	private Resultado regraNormal( Resultado anterior ) {
		Resultado r = new Resultado( anterior.idx, anterior.stackLevel+1, false );
		r.ignorarErro = anterior.ignorarErro;
		return r;
	}
	private Resultado regraOpcional( Resultado anterior ) {
		Resultado r = new Resultado( anterior.idx, anterior.stackLevel+1, true );
		r.ignorarErro = true;
		return r;
	}
	
	public static void verbose( int stackLevel, String txt ) {
		StringBuilder sb = new StringBuilder();
		for( int i=0; i<stackLevel*3; i++ )
			sb.append( i%3 == 0 ? '|' : ' ');
		System.out.println( sb.toString() + txt );
	}
	
	public void verboseNomeRegra( int n ) {
		String nome = Thread.currentThread().getStackTrace()[2].getMethodName();
		verbose( n, nome );
//		boolean doNext = false;
//		for (StackTraceElement s : e) {	
//			if (doNext) mostrar( n, s.getMethodName() );
//			doNext = s.getMethodName().equals("getStackTrace");
//		}
	}


//	===========================================================================
//	===========================================================================


	/**
	 * Subclasse Resultado.
	 * Um objeto dessa classe � retornado pelo m�todo buscarToken.
	 * � usada para registrar todos os resultados e par�metros da busca.
	 * @author Leandro
	 *
	 */
	private class Resultado {
		int idxInicial;
		int idx;
		int stackLevel;
		boolean opcional;
		boolean ok;
		boolean sucesso = false;
		boolean ignorarErro;
		public Resultado( int index, int stackLevel, boolean opcional ) {
			this.idxInicial = index;
			this.idx = index;
			this.opcional = opcional;
			this.stackLevel = stackLevel;
			this.ok = true;
			this.ignorarErro = opcional;
		}
		public void set() {
			if( this.ok ) this.idxInicial = this.idx;
		}
		public void and( Resultado r ) {
			this.ok = this.ok && r.ok;
			this.idx = r.ok ? r.idx : this.idx;
		}
		public void or() {
			this.sucesso = this.sucesso || this.ok;
			this.ok = !this.sucesso;
			this.idx = this.sucesso ? this.idx : this.idxInicial;
		}
		public void either( Resultado r ) {
			this.idx = r.ok ? r.idx : this.idx;
		}
		public Resultado fim() {
			this.sucesso = this.sucesso || this.ok;
			this.ok = this.opcional ? true : this.sucesso;
			return this;
		}
	}


}
