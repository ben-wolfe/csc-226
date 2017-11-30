/* 
	Submited by:
		Ben Wolfe | V00205547

	Course:
		CSC 226 - Algorithms and Data Structures II

	Deliverable:
		Assignment 4 - KMP String Search

	Description:
		This assignments generates the deterministic finite automon for a
		given pattern.  It then searches a provided text file for this pattern
		using the DFA.  The implementation is based off the example in our 
		textbook: Algorithms by Robert Sedgewick and Kevin Wayne.
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class  KMP {

    private static String pattern;
    private static int[][] dfa;      
    private final int alphabet; 

    public KMP(String pattern){  

        this.alphabet = 256;                 // This is the maximum character code for ASCII character set
                                             // It's more advantageous to set up the program using this general
                                             // configuration since it does not interfer with the asymptotic
                                             // running time and makes the program more versatile since we can
                                             // just use the numeric value of each character at each step.

        this.pattern = pattern;
        int patternLength = pattern.length();

        // Initialize the state transition array
        dfa = new int[alphabet][patternLength]; 

        // Initialize the first column (state 0) of the array
        dfa[pattern.charAt(0)][0] = 1; 

        // Iterate through each character of the pattern
        for (int refState=0, currState=1; currState < patternLength; currState++) {

        	// For each character, populate the alphabet column using the reference state
            for (int pattChar = 0; pattChar < alphabet; pattChar++)
            	dfa[pattChar][currState] = dfa[pattChar][refState];
            
            // Update state to correspond with a matched character
            dfa[pattern.charAt(currState)][currState] = currState+1;

            // Update the reference state for the next iteration
            // This acts as considering a substring from [1,...,n]
            refState = dfa[pattern.charAt(currState)][refState];
        }
    }
    
    public static int search(String txt) {  
		
        int patternLength = pattern.length();
        int textLength = txt.length();

        // For each character in the text, bounce around the state machine
        // If the state reaches the final state, then return match
        int i, j;
        for (i = 0, j = 0; i < textLength && j < patternLength; i++) {
            j = dfa[txt.charAt(i)][j];
        }

        return (j == patternLength) ? i-patternLength : textLength;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s;
		if (args.length > 0){
		    try{
				s = new Scanner(new File(args[0]));
		    } catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
		    }
		    
		    System.out.println("Opened file "+args[0] + ".");
		    
		    String text = "";
		    while(s.hasNext()){
				text += s.next() + " ";
		    }
		    
		    for(int i = 1; i < args.length; i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i] + " was not found.");
				else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
		    }
		} 
		else {
	    	System.out.println("usage: java KMP <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
    }
}