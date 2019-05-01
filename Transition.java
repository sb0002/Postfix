package Postfix;

public class Transition {					//nothing too special. just an edge between two states
	int to;									//# of the state the transition arrives at
	int from;								//# of the state the transition leaves
	char edge;								//symbol which caused the transition
	
	Transition(int a, int b, char c) {
		this.to = b;
		this.from = a;
		this.edge = c;
	}
}
