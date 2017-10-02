/*
	CSC 226 - Algorithms and Data Structures II
	Assignment 1 - Medians of Medians Quick Select

	Submitted By: Ben Wolfe | V00205547
	Submitted On: 5.10.2017
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class QuickSelect {
       
    public static int QuickSelect(int[] A, int k){

    	if (A.length == 0) return -1;  // Check for a valid array

    	int[] noDuplicates = RemoveDuplicates(A);  // Remove duplicates    	
    	if (noDuplicates.length < k) return -1;

    	ArrayList<Integer> arrayList = new ArrayList<>(A.length);
    	for (int num : A) {
    		arrayList.add(Integer.valueOf(num));
    	}

    	int kthValue = medianOfMedians(arrayList, k);  // Find the kth order statistic */
        return kthValue;
    }   

    public static int medianOfMedians(ArrayList<Integer> A, int k) {

    	for (Integer num : A) {
    		System.out.print(num + ", ");
    	}
    	System.out.println();

    	int kthValue;
    	int pivot;

    	/* Problem 1 is getting out of bounds erros here.  Notice how size = 3 and 3 % 7 = 4; 3-4 = -1 so the 
    	   for loop never executes */
    	ArrayList<Integer> allMedians = new ArrayList<Integer>();
    	System.out.println(A.size());
    	for(int index=0; index < (A.size() - A.size() % 7); index = index+7) {
			ArrayList<Integer> subList = new ArrayList<Integer>(A.subList(index, index+7));

			System.out.print("Sublist: ");
	    	for (Integer num : subList) {
	    		System.out.print(num + ", ");
	    	}
	    	System.out.println();

    		allMedians.add(findMedian(subList));    		
    	}

    	if (allMedians.size() <= 7) {
    		Collections.sort(allMedians);
    		pivot = allMedians.get(allMedians.size()/2);

    	} else {
    		pivot = medianOfMedians(allMedians, allMedians.size()/2);
    	}


    	ArrayList<Integer> low = new ArrayList<Integer>();
    	ArrayList<Integer> high = new ArrayList<Integer>();
    	
    	for (Integer num : A) {
    		if (num < pivot) low.add(num);
    		else if (num > pivot) high.add(num);
    	}

    	int lowLength = low.size();
    	if (k < lowLength) {
    		return medianOfMedians(low, k);
    	} else if (k == lowLength+1) {
    		return pivot;
    	} else {
    		return medianOfMedians(high, k-lowLength-1);
    	}	
    }

    public static int findMedian(ArrayList<Integer> nums) {
    	Collections.sort(nums);
    	return nums.get(nums.size()/2);
    }

    public static int[] RemoveDuplicates(int[] A) {
    /* This function removes duplicates by attempting to add every element to a hash set.  The hash set class implements 
       the set interface backed by a hash table meaning that no duplicate entries are allowed.  Time complexity is O(n) to 
       iterate over the entire array.
       Reference: https://docs.oracle.com/javase/7/docs/api/java/util/HashSet.html
	*/
    	HashSet<Integer> hashSet = new HashSet<Integer>(); 
    	for (int num : A) {
    		hashSet.add(num);
    	} 
		
		Iterator<Integer> itr=hashSet.iterator();
		int[] noDuplicates = new int[hashSet.size()];
		int index = 0;  
 		while(itr.hasNext()){  
 			noDuplicates[index++] = itr.next();
  		} 

  		return noDuplicates;
    }
    
    public static void main(String[] args) {
        int[] A = {1, 2, 3, 4, 5, 6, 7};
       //System.out.println("The kth smallest value is " + QuickSelect(A, (A.length+1)/2));
        System.out.println("The kth smallest value is " + QuickSelect(A, 2));

    }
}