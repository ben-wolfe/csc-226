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
        int[] A = {436,454,306,183,132,431,297,619,279,458,324,122,505,549,83,186,131,71,91,216,190,26,386,109,
        142,117,65,372,346,330,441,231,2,409,221,368,298,52,30,200,503,412,359,32,263,528,279,11,26,
        103,570,513,580,572,117,70,383,109,58,297,384,550,178,236,133,227,223,311,337,480,559,156,58,
        335,514,598,209,382,255,285,539,140,198,605,562,414,192,477,571,604,545,93,253,30,148,461,625,
        568,590,538,352,436,603,560,294,439,17,16,206,333,515,560,582,46,29,249,210,324,277,305,600,226,
        406,299,133,329,302,342,358,307,204,388,40,19,147,95,347,447,62,304,358,223,578,631,411,613,265,
        212,597,585,112,452,16,428,221,636,518,295,415,541,490,136,445,120,373,328,140,476,62,603,262,
        594,173,571,440,248,580,466,45,342,320,371,159,347,344,20,542,257,476,335,599,617,567,344,315,
        605,220,392,289,199,491,539,105,160,565,72,210,319,500,343,345,9,99,433,529,349,580,63,93,50,
        172,24,177,265,252,263,39,325,570,186,482,121,340,192,494,426,502,215,235,62,167,51,566,519,
        201,342,452,486,510,581,89,213,211,401,429,573,492,351,465,386,419,293,344,346,352,522,524,455,
        383,483,75,174,197,214,179,118,75,356,581,438,425,485,46,564,11,270,497,431,525,62,546,323,270,536,
        479,448,419,438,207,543,89,36,518,16,457,610,552,104,588,434,643,417,213,355,365,7,155,438,439,76,
        204,326,345,607,542,434,509,121,75,15,386,557,478,365,577,154,271,13,154,258,8,626,573,44,74,645,
        301,38,541,633,12,449,96,511,197,512,447,570,114,570,153,573,145,556,231,140,126,213,473,463,314,
        180,379,549,234,62,579,151,90,246,528,484,66,602,376,645,183,88,218,604,139,478,35,646,502,62,40,
        132,99,157,530,16,475,365,512,260,32,99,458,289,443,479,107,334,317,647,146,68,307,79,560,137,182,
        505,619,518,408,228,180,303,552,108,112,332,104,60,1,206,112,573,249,537,330,178,453,240,381,168,58,
        42,352,93,259,121,143,411,269,51,310,138,238,492,173,349,277,144,486,75,280,612,454,640,425,351,567,
        480,314,127,442,208,178,199,419,586,98,621,226,175,395,515,646,550,17,431,152,530,20,302,184,423,417,
        601,186,287,156,29,527,271,215,76,615,96,509,241,507,497,396,417,331,238,101,366,93,462,8,75,411,340,
        213,569,122,312,373,14,415,419,316,253,619,161,571,458,611,440,627,25,117,623,296,114,509,524,463,244,
        434,458,429,578,195,38,326,177,397,116,311,303,424,590,643,595,519,431,440,259,32,280,66,353,163,60,
        423,603,171,543,500,597,144,218,478,387,108,498,594,540,206,497,186,307,609,576,462,571,560,420,599,
        448,289,447,395,210,344,350,157,457,267,240,145,76,446,173,60,431,60,518,191,25,555,263,612,232,125,
        246,74,335,153,300,119,337,625,422,238,535,114,71,202,391,370,599,123,224};


        int kthElement = 6;
       //System.out.println("The kth smallest value is " + QuickSelect(A, (A.length+1)/2));
        System.out.println("The "+kthElement+"th smallest element is " + QuickSelect(A, kthElement));
    }
}