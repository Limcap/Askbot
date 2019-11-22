package ledski.askbot.automato;

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
	Sistema, Questao, Teste, String, Inteiro, Decimal,
	Se, Entao, OuSe, Senao,
	qVar, tVar,
	whitespace,
	igual,
	mais, menos, vezes, barra, potencia, mod, e, ou,
	maior, maiorIgual, menor, menorIgual, igualIgual,
	parenteses1, parenteses2, chave1, chave2, doisPontos,
	_error, _none, 
	}
}