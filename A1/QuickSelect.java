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
        int[] A = {816,652,313,279,5,189,901,638,128,454,722,808,820,809,577,879,577,814,874,887,70,318,719,930,35,398,777,858,962,153,595,87,470,970,65,981,387,619,24,916,599,638,199,949,394,216,369,403,955,390,772,259,461,407,384,646,415,788,5,673,308,334,488,258,728,16,98,9,74,252,402,965,936,436,728,80,692,938,859,801,201,510,186,263,498,527,45,550,694,748,304,472,835,60,240,33,808,174,747,606,533,223,698,223,262,289,297,4,236,738,36,464,236,422,820,701,518,308,504,827,495,857,651,815,396,473,737,751,369,149,86,593,237,714,206,11,629,368,941,169,829,375,403,292,392,749,933,793,469,138,581,854,700,909,354,926,155,207,293,879,926,773,453,346,824,970,483,168,931,368,373,616,706,245,481,830,502,550,621,200,850,143,129,45,311,244,933,476,747,762,870,949,805,169,882,939,718,106,555,975,359,345,245,683,962,714,639,899,540,115,973,349,713,243,943,436,466,948,864,176,133,55,734,351,628,127,709,123,130,92,521,469,863,735,326,368,814,663,515,737,340,959,303,450,472,690,982,673,351,883,431,972,73,311,40,531,404,100,763,537,179,270,190,117,478,830,549,452,559,893,512,21,599,44,110,516,606,171,215,941,585,536,877,533,848,766,621,294,956,979,220,36,261,685,91,617,620,680,346,85,251,831,439,929,520,738,345,7,774,979,319,110,458,761,657,95,410,886,124,335,298,874,583,588,256,332,339,510,631,909,99,307,177,223,260,276,840,579,284,320,392,593,527,725,51,815,953,427,966,982,898,318,776,443,667,272,321,634,601,741,749,603,37,580,531,910,430,271,484,765,694,556,854,784,932,595,13,277,399,443,587,304,101,51,87,544,79,7,639,674,400,462,604,82,724,272,613,975,470,3,408,375,31,865,550,445,129,535,191,823,659,346,275,83,933,302,26,185,575,395,457,122,958,296,675,264,816,648,79,832,522,221,497,426,239,759,289,135,607,355,547,304,286,416,516,368,902,617,550,740,378,601,894,956,302,372,398,213,825,611,758,355,732,327,359,869,145,876,853,494,864,194,139,674,866,356,51,552,234,933,700,538,806,262,125,810,789,335,520,126,800,144,405,265,532,440,970,163,733,854,578,490,595,904,356,665,935,919,671,946,343,502,667,8,836,897,670,390,5,556,560,769,192,111,565,89,674,154,897,409,306,910,708,341,154,148,753,473,373,920,668,717,897,742,564,739,605,185,117,905,168,122,548,162,366,812,222,530,21,428,153,280,571,416,913,654,537,709,43,364,245,270,175,244,941,44,220,510,570,96,962,346,451,342,49,570,9,607,759,154,350,838,818,275,610,787,959,572,230,578,11,25,150,208,454,400,243,682,368,794,346,745,269,572,662,494,686,639,189,85,396,742,208,61,107,295,922,600,184,120,700,92,104,904,910,571,518,893,135,481,859,931,637,9,625,674,766,800,390,873,340,584,146,167,621,273,308,538,810,266,181,766,321,925,397,362,201,43,389,774,382,520,755,310,227,216,708,149,577,813,308,59,382,211,369,732,519,773,129,840,915,12,594,360,194,118,457,918,945,128,359,849,719,542,952,976,233,366,32,144,174,909,508,571,463,576,313,109,706,431,499,956,971,204,622,960,864,355,160,493,748,813,884,933,608,130,701,245,813,742,248,222,265,248,586,204,707,104,267,22,197,968,771,588,708,231,295,70,72,89,645,39,767,785,906,444,324,587,686,192,724,420,422,740,136,929,301,916,301,585,454,172,863,134,925,882,162,291,42,879,842,350,373,370,501,743,598,44,108,242,117,964,633,822,661,713,653,288,549,797,361,70,828,511,200,263,870,770,501,718,920,278,6,32,460,212,370,905,486,506,844,298,838,479,77,393,572,882,379,869,84,191,302,777,973,300,364,394,263,46,516,420,145,752,14,546,766,33,686,971,617,688,828,47,271,518,677,934,397,841,353,248,122,278,175,783,580,325,859,910,25,503,763,708,643,50,208,366,570,253,425,941,254,670,92,113,477,780,849,111,407,63,128,800,659,479,250,879,796,239,71,812,343,536,865,821,935,618,609,146,682,56,157,12,344,311,782,336,477,322,674,96,171,288,74,101,632,539,859,132,791,13,305,223,337,46,818,247,116,156,941,248,438,888,962,140,619,337,165,637,87,905,349,789,285,354,372,292,683,164,907,4,679,391,963,527,465,377,277,490,101,304,953};


        int kthElement = 20;
       //System.out.println("The kth smallest value is " + QuickSelect(A, (A.length+1)/2));
        System.out.println("The "+kthElement+"th smallest element is " + QuickSelect(A, kthElement));
    }
}