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
   	/* 
   	   This function returns the kth order statistic of a sequence
   	   of random numbers by using a median-of-median pivot to cluster
   	   the elements.  It first iterates once over the array removing
   	   all duplicates.
   	*/

    	if (A.length == 0 || k == 0) return -1;                   // Check for a valid array

    	int[] noDuplicates = RemoveDuplicates(A);                 // Remove duplicates     	
    	if (noDuplicates.length < k) return -1;                   // Check for valid k after removing duplicates */

    	ArrayList<Integer> arrayList = new ArrayList<>(A.length); // Convert the input to an array list
    	for (int num : noDuplicates) {
    		arrayList.add(Integer.valueOf(num));
    	}

    	int kthValue = medianOfMedians(arrayList, k);             // Find the kth order statistic */
        return kthValue;
    }   


	public static int medianOfMedians(ArrayList<Integer> A, int k) {
	/*
	   This is the recurisve implementatino of quick select which first
	   recursively finds the median of all medians then uses this median
	   to group the elements.  Time complexity is O(n) as expected.
	*/
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

    	if (k <= lowLength) return medianOfMedians(low, k);
    	else if (k == lowLength+1) return pivot;
    	else return medianOfMedians(high, k-lowLength-1);                     
    } 
  

    public static int findMedian(ArrayList<Integer> nums) {
    /*
       Find the median of a group of seven numbers be sorting
       and then grabbing the middle element.  Time complexity is
       O(1).
    */
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


    public static void main(String[] args) {
    	int A[] = {1,3,5,7,9,2,4,6,8,10};
        for (int kthElement=0; kthElement < A.length; kthElement++){
        	System.out.println("The "+kthElement+"th smallest element is " + QuickSelect(A, kthElement));
        }
    }
    
}