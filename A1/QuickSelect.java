/*
	CSC 226 - Algorithms and Data Structures II
	Assignment 1 - Medians of Medians Quick Select

	Submitted By: Ben Wolfe | V00205547
	Submitted On: 5.10.2017
*/

import java.util.*;
import java.io.*;

public class QuickSelect {
       
    public static int QuickSelect(int[] A, int k){
     	
    	int kthOrder = 0;

    	int[] noDuplicates = removeDuplicates(A);
    	for (int num : noDuplicates) {
    		System.out.print(num + " ");
    	}
    	
        return kthOrder;
    }   

    public static int[] removeDuplicates(int[] A) {
    /* This function takes an array and returns a duplicate-free version.  The worst case running time
       is O(n) as a result of traversing over all elements of the array to create the mapping and also 
       to convert back to an array */

       	// Create an Integer list to be able to apply hash set
		List<Integer> integerList = new ArrayList<Integer>();
		for (int index = 0; index < A.length; index++) {
			integerList.add(A[index]);
		}

		// Make a set to remove duplicates
    	Set<Integer> arraySet = new HashSet<Integer>(integerList);
    	
    	// Convert back to int[]
    	int[] noDuplicates = new int[arraySet.size()];
    	int index = 0;
    	for ( Integer i : arraySet) {
    		noDuplicates[index++] = i;
    	}

    	return noDuplicates;
    } 
    
    public static void main(String[] args) {
        int[] A = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59};

        System.out.println("The median is " + QuickSelect(A, (A.length+1)/2));
    }
}