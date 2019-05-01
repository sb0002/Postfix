package Postfix;

import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		String line;
	    char c;
	    int stateCtr;														//counter for how many states the NFA should have
	    int u = 1;

	    Vector<NFA> stack = new Vector<NFA>();
	    NFA temp1;
	    NFA temp2;
	    
	    Utility util = new Utility();										//utility object to do NFA combining operations

	    String database="/home/bob/Documents/CS317/Postfix/src/db.txt";		//replace location of desired db file
	    File dataFile = new File(database);
	    Scanner sc;
	    try {																//try-catch block to attempt to set up file reading
	      sc = new Scanner(dataFile);
	    } catch(Exception e) {
	      System.out.println("Input error: " + e);
	      return;
	    }

	    while (sc.hasNextLine()) {											//loop until EOF
	      System.out.println("NFA " + u + "........");						//if db has multiple lines, need to indicate which NFA is being output
	      u++;																//another line, another NFA
	      stack.clear();													//clear the stack for use with a fresh NFA
	      stateCtr = 0;														//new NFA has no states yet
	      line = sc.nextLine();												//get the next line in db file
	      for (int i = 0; i < line.length(); i++) {							//look at each char in db to see what should happen
	        c = line.charAt(i);
	        if(c == '&') {													//time to concatenate
	        	if(stack.size() <= 1) {										//db probably has something like a&
	        		System.out.println("error: bad regex"); 
	        		return;
	        	}
	        	temp2 = stack.lastElement();								//these lines pop twice and save the values
	        	stack.remove(stack.size()-1);
        		temp1 = stack.lastElement();
        		stack.remove(stack.size()-1);
        		stack.add(util.concat(temp1, temp2, stateCtr));				//then a new concatenated NFA is pushed to stack
	        }
	        else if(c == '|') {
	        	if(stack.size() <= 1) {
	        		System.out.println("error: bad regex"); 
	        		return;
	        	}
	        	temp2 = stack.lastElement();								//same as before. pop twice, save twice
	        	stack.remove(stack.size()-1);
        		temp1 = stack.lastElement();
        		stack.remove(stack.size()-1);
        		stack.add(util.union(temp1, temp2, stateCtr));				//push a union'd NFA to the stack
        		stateCtr += 2;												//union operation adds 2 states to an NFA
	        }
	        else if (c == '*') {											//time for some Kleene'ing
	        	temp1 = stack.lastElement();								//pop & save
	        	stack.remove(stack.size()-1);
	        	stack.add(util.kleene(temp1, stateCtr));					//push a Kleene closed NFA to the stack
	        	stateCtr += 2;												//Kleene closure adds 2 states to an NFA
	        }
	        else {
	        	stack.add(new NFA(stateCtr, c));							//nothing special, just a basic 2-state NFA that accepts a single character
	        	stateCtr += 2;												//2 states were just added
	        }
	      }
		    for(int i = 0; i <= stack.size() - 1; i++ ) {
	        	for(int j = 0; j <= stack.get(i).edges.size() - 1; j++) {
	        		System.out.print("(q" + stack.get(i).edges.get(j).from + ", ");		//print stuff like (q1, 
	        		System.out.print(stack.get(i).edges.get(j).edge);					//print transition char
	        		System.out.println(") -> q" + stack.get(i).edges.get(j).to);		//print stuff like a) -> q2
	        	}
	        }
	    }
	    sc.close();
	}
}