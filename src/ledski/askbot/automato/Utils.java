package ledski.askbot.automato;

/**
 * 
 * @author Leandro
 * @version 0.1.0
 */
public class Utils {
	
	
	
	/**
	 * Substitui caracteres especiais uma String, como tab, espa�o e quebra de linha. aspas e barra
	 * para que sejam exibidos como seus correspondentes escapados: \t \s \n \" \\ 
	 * @param s String ou Character
	 * @return String
	 */
	public static String legivel(String s) {
		char[] ca = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		for( char c : ca ) {
			sb.append( legivel( c ) );
		}
		return sb.toString();
	}
	public static String legivel(Character c) {
		switch (c) {
			case '\n':
				return "\\n";
			case '\t':
				return "\\t";
			case '\r':
				return "\\r";
			case '\f':
				return "\\f";
			case '\b':
				return "\\b";
			case '\'':
				return "\\\'";
			//case '\"':
				//return "\\\"";
			case '\\':
				return "\\\\";
			case ' ':
				return "\\s";
			default:
				return c.toString();
		}
	}
	
	
	
}
