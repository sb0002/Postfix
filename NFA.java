package Postfix;

import java.util.*;

public class NFA {
	Vector<Transition> edges = new Vector<Transition>();			//each NFA will contain a Vector array of transitions
	int start;														//# of state at the NFA's beginning
	int end;														//# of NFA's accept state
	char symbol;													//language the NFA recognizes
	
	NFA(int s, char x){												//when creating an NFA, it takes the number of existing states and the character it's to be built for
		this.start = s + 1;
		this.end = s + 2;
		this.symbol = x;
		this.edges.add(new Transition(start, end, symbol));			//a new NFA has one transition, for the single symbol, from its start to its end.
	}
}
