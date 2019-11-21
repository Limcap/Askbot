package ledski.askbot.automato;

/**
 * 
 * @author Leandro
 * @version 0.1.0
 */
public enum Token {
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