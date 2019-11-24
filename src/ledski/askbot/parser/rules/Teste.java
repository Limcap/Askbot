package ledski.askbot.parser.rules;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.SyntaxExceptions.*;
import static ledski.askbot.lexer.Token.TokenType.*;
import ledski.askbot.parser.SyntaxManager;
import ledski.askbot.parser.SyntaxRule;

/** 
 * Regra:
 * TESTE  ->  Teste  tVar  :  String  CONDICIONAL_FULL
 * @author Leandro Ledski
 */
public class Teste extends SyntaxRule {
	
	
	public String variavel;
	public String textoInicial;
	public List<CondicionalBasica> condicionais = new ArrayList<CondicionalBasica>();
	public Primitivo resultadoPadrao;
	
	
	public Teste() throws Exception {
		SyntaxManager sm = new SyntaxManager();
		
		
		try {
			sm.getNextToken( _Teste ); // Regra Teste
			variavel = sm.getNextToken( _tVar ).lexema; // Regra tVar
			sm.getNextToken( _doisPontos ); // Regra :
			textoInicial = sm.getNextToken( _String ).lexema; // Regra String 
			CondicionalFull cf = new CondicionalFull(); // Regra CONDICIONAL_FULL
			condicionais.addAll( cf.condicionaisBasicas );
			resultadoPadrao = cf.resultadoPadrao;
		}
		catch( UnexpectedToken e ) {
			sm.resetRulePointer();
			sm.rethrowFromCatchBlockOfEnforcedRules();
		}
		
		
	}
	
	
}
