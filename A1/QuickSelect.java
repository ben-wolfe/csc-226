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

    	if (A.length == 0 || k == 0) return -1;              // Check for a valid array

    	int[] noDuplicates = RemoveDuplicates(A);  // Remove duplicates     	
    	if (noDuplicates.length < k) return -1;    // Check for valid k after removing duplicates */

    	ArrayList<Integer> arrayList = new ArrayList<>(A.length); // Convert the input to an array list
    	for (int num : noDuplicates) {
    		arrayList.add(Integer.valueOf(num));
    	}

    	int kthValue = medianOfMedians(arrayList, k);  // Find the kth order statistic */
        return kthValue;
    }   

    public static int medianOfMedians(ArrayList<Integer> A, int k) {

    	System.out.println();
    	System.out.println("************************ start iteration ************************ ");

    	System.out.println("k: " + k);

    	System.out.print("Argument list A: ");
    	printLists(A);
    	System.out.println();

    	int kthValue, pivot;
    	ArrayList<Integer> allMedians = new ArrayList<Integer>();
    	ArrayList<Integer> subList = new ArrayList<Integer>();

    	for(int index=0; index < A.size(); index++) {
	
    		subList.add(A.get(index));

    		if (subList.size() == 7) {

    			allMedians.add(findMedian(subList));
    			subList.clear();

    		} else if (index == A.size()-1 && subList.size() != 7) {

				allMedians.add(findMedian(subList));
    			subList.clear();
    		}
    	}

    	System.out.print("Medians: ");
    	printLists(allMedians);
    	System.out.println();


    	if (allMedians.size() <= 7) {
    		
    		Collections.sort(allMedians);
    		pivot = allMedians.get(allMedians.size()/2);

    	} else {

    		pivot = medianOfMedians(allMedians, allMedians.size()/2);
    	} 

    	System.out.println("Pivot: " + pivot);

    	ArrayList<Integer> low = new ArrayList<Integer>();
    	ArrayList<Integer> high = new ArrayList<Integer>();
 
    	for (Integer num : A) {
    		if (num < pivot) low.add(num);
    		else if (num > pivot) high.add(num);
    	}

    	int highLength = high.size();
    	int lowLength = low.size();

    	System.out.print("Low: ");
    	printLists(low);
    	System.out.println();

    	System.out.print("High: ");
    	printLists(high);
    	System.out.println();

    	System.out.println("Low size: " + lowLength + ", High size: " + highLength);

    	if (k <= lowLength) {
    		System.out.println("Calling <= lowLength");
			System.out.println("************************ end iteration ************************ ");
    		return medianOfMedians(low, k);
    	
    	} else if (k == lowLength+1) {
    		System.out.println("Calling == lowLength+1");
    		System.out.println("************************ end iteration ************************ ");
    		return pivot;
    	
    	} else { // k > lowLength + 1
			System.out.println("Calling > lowLength+1");
			System.out.println("************************ end iteration ************************ ");
    		return medianOfMedians(high, k-lowLength-1);
    	}                       
    }

    public static int findMedian(ArrayList<Integer> nums) {
    	Collections.sort(nums);
    	return nums.get(nums.size()/2);
    }

    public static int[] RemoveDuplicates(int[] A) {
    /* 
       This function removes duplicates by attempting to add every element to a hash set.  
       The hash set class implements the set interface backed by a hash table meaning that no 
       duplicate entries are allowed. Time complexity is O(n) to iterate over the entire array.
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

    public static void printLists(ArrayList<Integer> list) {
    	for (Integer num : list) System.out.print(num + " ");
    }
    
    public static void main(String[] args) {
    	int[] A = {1, 24, 3, 70};

        int kthElement = 1;
       //System.out.println("The kth smallest value is " + QuickSelect(A, (A.length+1)/2));
        System.out.println("The "+kthElement+"th smallest element is " + QuickSelect(A, kthElement));
    }
}