package ledski.askbot.automato;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leandro Oliveira Lino de Araujo
 * @version 0.1.0
 */
public abstract class Transition {
	
	
	
	// Dicionario
	public static final Map<String, String> dict = Stream.of(new Object[][] { 
		//Verbete		String de transi�ao
		{"whs",					" \t\n\r"},
		{"num",					"0123456789"},
		{"fs1",					"=-+"},
		{"alphanum",			"aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVxXwWyYzZ_0987654321"},
	}).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));
	
	
	
	// Transicoes - Simbolos reservados
	public static final Map<Token, String> pathMap = Stream.of(new Object[][] { 
		{Token.Sistema,		"S i s t e m a [whs]"},
		{Token.Questao,		"Q u e s t a o [whs]"},
		{Token.Teste,			"T e s t e [whs]"},
		{Token.String,			"\" []* \" []"},
		{Token.Inteiro,		"[num] [num]* []"},
		{Token.Decimal,		"[num] [num]* . [num] [num]* []"},
		{Token.Se,				"S e [whs]"},
		{Token.Entao,			"E n t a o [whs]"},
		{Token.OuSe,			"O u S e [whs]"},
		{Token.Senao,			"S e n a o [whs]"},
		{Token.whitespace,		"[whs] [whs]* []"},
		{Token.parenteses1,	"( []"},
		{Token.parenteses2,	") []"},
		{Token.chave1,			"{ []"},
		{Token.chave2,			"} []"},
		{Token.igual,			"= []"},
		{Token.igualIgual,		"= = []"},
		{Token.maior,			"> []"},
		{Token.maiorIgual,		"> = []"},
		{Token.menor,			"< []"},
		{Token.menorIgual,		"< = []"},
		{Token.mais,			"+ []"},
		{Token.menos,			"- []"},
		{Token.vezes,			"* []"},
		{Token.potencia,		"^ []"},
		{Token.barra,			"/ []"},
		{Token.mod,			"% []"},
		{Token.e,				"& []"},
		{Token.ou,				"| []"},
		{Token.doisPontos,		": []"},
		{Token.qVar,			"q [alphanum]* []"},
		{Token.tVar,			"t [alphanum]* []"},
	}).collect(Collectors.toMap(data -> (Token) data[0], data -> (String) data[1]));
	
	
	
	private static Iterator<Entry<Token,String>> iterator = null;
	static private String[] path = null;
	static public int pathIndex = -1;
	static public int nextNodeDepth = 0;
	static private boolean wordIsEntry = false;
	
	static public Token token = null;
	static public boolean hasBrackets = false;
	static public String transitionStr;// para verbosidade
	static public String word = null;
	static public char[] chars = null;
	static public boolean isInverted = false;
	static public boolean isSelfLoop = false;
	
	
	
	/**
	 * Informa se a transiça atual é a última do 'path' atual.
	 * @return
	 */
	public static boolean isLast() {
		return pathIndex == path.length-1;
	}
	
	
	
	/**
	 * Reseta o estado da classe.
	 */
	public static void reset() {
		iterator = pathMap.entrySet().iterator();
		path = null;
		token = null;
		resetTransitionString();
	}
	
	
	
	public static void resetTransitionString() {
		transitionStr = null;
		wordIsEntry = false;
		hasBrackets = false;
		word = null;
		chars = null;
		isInverted = false;
		isSelfLoop = false;
		pathIndex = -1; // tem q ser menos 1 pq o metodo next incrementa no início.
		nextNodeDepth = 0;
	}
	
	
	
	/**
	 * Avança para o próximo 'path'.
	 * @return true se houver próximo<br>false se não houver.
	 */
	public static boolean nextPath() {
		if( iterator == null ) iterator = pathMap.entrySet().iterator();
		if( iterator.hasNext() ) {
			Entry<Token,String> entry = iterator.next();
			path = entry.getValue().split( " " );
			token = entry.getKey();
			resetTransitionString();
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
	 * Avança para o próxima string de transição no 'path' atual.
	 * @return true se houver próxima<br>false se não houver.
	 */
	public static boolean next() {
		if( path.length > pathIndex+1 ) {
			pathIndex++;
			transitionStr = path[pathIndex];// para verbosidade
			parse( path[pathIndex] );
			// nextNodeDepth só avança se a transição nao for um loop, pois este numero é usado para definir
			// o nome do estado.
			if( !isSelfLoop ) nextNodeDepth++;
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
	 * Verifica as propriedades do string de transição atual e atualiza todas as variáveis da classe que representam
	 * essas propriedades. Chamado pelo método next().
	 * @param transitionStr
	 */
	private static void parse( String transitionStr ) {
		Matcher m = Pattern
//		.compile( "^(-)?(\\[)?(.+?)(\\])?(\\*)?$" )
//		.compile( "^((-)?\\[)?(.+?)(\\](\\*)?)?$" )
		.compile( "^((-)?\\[(.*?)\\](\\*)?|(.*))$" )
		.matcher( transitionStr );
		m.find();
		hasBrackets = m.group(5) == null;
		word = hasBrackets ? m.group(3) : m.group(5);
		wordIsEntry = hasBrackets && dict.get( word ) != null;
		chars = ( wordIsEntry ? dict.get( word ) : word ).toCharArray();
		isInverted = m.group(2) != null || ( hasBrackets && chars.length == 0 );
		isSelfLoop = m.group(4) != null;
	}

}


