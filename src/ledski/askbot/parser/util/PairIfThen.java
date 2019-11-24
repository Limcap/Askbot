package ledski.askbot.parser.util;

import java.util.ArrayList;
import java.util.List;

import ledski.askbot.parser.rules.Condicao;
import ledski.askbot.parser.rules.Primitivo;

public class PairIfThen {
//	public final Termo termo1;
//	public final OpComparacao op;
//	public final Termo termo2;
	public List<Condicao> condicoes = new ArrayList<Condicao>();
	public Primitivo implicacao;
	
	public PairIfThen() {
	}
	
	public PairIfThen( List<Condicao> condicoes, Primitivo implicacao ) {
		this.condicoes = condicoes;
		this.implicacao = implicacao;
	}
	
}
