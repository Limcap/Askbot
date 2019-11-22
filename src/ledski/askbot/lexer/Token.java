package ledski.askbot.lexer;

import ledski.util.Gridder;

/**
 * 
 * @author Leandro
 * @version 0.2.0
 */
public class Token {
	
	
	public Token( TokenType type, String lexema ) {
		this.type = type;
		this.lexema = lexema;
	}
	
	
	
	public final TokenType type;
	public final String lexema;
	
	
	
	public Gridder toGridder() {
		Gridder gr = new Gridder();
		return gr.text( "{ TOKEN:", type.name(), "- LEXEMA:", Utils.legivel( lexema ), "}" );
	}
	
	public String toString() {
		return String.join( " ", "{ TOKEN:", type.name(), "\t LEXEMA:", Utils.legivel( lexema ), "}" );
	}
	
	
	
	public enum TokenType {
	_Especialidade, _Questao, _Teste, _String, _Inteiro, _Decimal,
	_Se, _Entao, _OuSe, _Senao,
	_qVar, _tVar,
	_whitespace,
	_igual,
	_mais, _menos, _vezes, _barra, _potencia, _mod, _e, _ou,
	_maior, _maiorIgual, _menor, _menorIgual, _igualIgual,
	_parenteses1, _parenteses2, _chave1, _chave2, _doisPontos,
	_error, _none, 
	}
}