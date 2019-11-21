package ledski.askbot.automato;

/**
 * 
 * @author Leandro
 * @version 0.1.0
 */
public class Link {

	public final String transitionChar;
	public final Node node;

	public Link(String key, Node value) {
		this.transitionChar = key;
		this.node = value;
	}

}
