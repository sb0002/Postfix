package Postfix;
//class to handle Kleene closure, Union, and Concatenate
public class Utility {
	public NFA union(NFA a, NFA b, int s) {
		s = s - a.start - b.start;							//should ensure c starts before either a or b, but is probably just causing problems
		NFA c = new NFA(s, 'E');							//make a new blank NFA
		c.edges.clear();									//clear out its states to avoid any confusion
		c.edges.add(new Transition(s, a.start, 'E'));		//add epsilon jump from c to beginning of a
		c.edges.add(new Transition(s, b.start, 'E'));		//add epsilon jump from c to beginning of b
		s++;
		c.edges.add(new Transition(b.end, s, 'E'));			//add closing transitions to new final state
		c.edges.add(new Transition(a.end, s, 'E'));
		c.edges.addAll(b.edges);							//copy over the transitions
		c.edges.addAll(a.edges);
		return c;
	}
	
	public NFA concat(NFA a, NFA b, int s) {
		NFA c = new NFA(s, 'E');							//new blank NFA
		c.edges.clear();									//clear transitions
		c.edges.addAll(a.edges);							//copy over transitions from the other two NFAs
		c.edges.addAll(b.edges);
		c.edges.add(new Transition(a.end, b.start, 'E'));	//add epsilon jump from end of a to start of b.
		return c;
	}
	
	public NFA kleene(NFA x, int s) {
		NFA y = new NFA(s, x.symbol);								//really just a copy of x
		y.edges.clear();											//clear out transitions
		y.edges.addAll(x.edges);									//copy over x's transitions
		y.edges.add(new Transition(x.start - 1, x.start, 'E'));		//new E-jump to beginning of x from new state before x
		y.edges.add(new Transition(x.end, x.end + 1, 'E'));			//new E-jump from end of x to new state after x
		y.edges.add(new Transition(x.end, x.start, 'E'));			//new E-jump from previous end of x to previous beginning
		return y;
	}
}
