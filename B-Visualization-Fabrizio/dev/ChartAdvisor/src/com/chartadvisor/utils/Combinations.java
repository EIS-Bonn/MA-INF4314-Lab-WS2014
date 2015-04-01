package com.chartadvisor.utils;

import java.util.*;

import com.chartadvisor.model.Property;


public class Combinations {

	
	public static void main(String[] args){
		Object[] elements = new Object[] {'A','B','C','D'};

		combination(elements,2);
	}
	
	
	public static ArrayList<ArrayList<Property>> combination(Object[]  elements, int K){
		ArrayList<ArrayList<Property>> result = new ArrayList<ArrayList<Property>>();
		// get the length of the array
		// e.g. for {'A','B','C','D'} => N = 4 
		int N = elements.length;
		
		if(K > N){
			System.out.println("Invalid input, K="+K+" > N="+N);
			return null;
		}
		// calculate the possible combinations
		// e.g. c(4,2)
		c(N,K);
		
		// get the combination by index 
		// e.g. 01 --> AB , 23 --> CD
		int combination[] = new int[K];
		
		// position of current index
		//  if (r = 1)				r*
		//	index ==>		0	|	1	|	2
		//	element ==>		A	|	B	|	C
		int r = 0;		
		int index = 0;
		
		while(r >= 0){
			// possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
			// possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"
			
			// for r = 0 ==> index < (4+ (0 - 2)) = 2
			if(index <= (N + (r - K))){
					combination[r] = index;
					
				// if we are at the last position print and increase the index
				if(r == K-1){

					//do something with the combination e.g. add to list or print
					result.add(toListProperty(combination, elements));
					index++;				
				}
				else{
					// select index for next position
					index = combination[r]+1;
					r++;										
				}
			}
			else{
				r--;
				if(r > 0)
					index = combination[r]+1;
				else
					index = combination[0]+1;	
			}			
		}
		//printList(result);
		return result;
	}
	

	
	public static int c(int n, int r){
		int nf=fact(n);
		int rf=fact(r);
		int nrf=fact(n-r);
		int npr=nf/nrf;
		int ncr=npr/rf; 
		
		//System.out.println("C("+n+","+r+") = "+ ncr);

		return ncr;
	}
	
	public static int fact(int n)
	{
		if(n == 0)
			return 1;
		else
			return n * fact(n-1);
	}
	

	public static void print(int[] combination, Object[] elements){

		String output = "";
		for(int z = 0 ; z < combination.length;z++){
			output += elements[combination[z]];
		}
		System.out.println(output);
	}
	public static ArrayList<String> toList(int[] combination, Object[] elements){
		ArrayList<String> result = new ArrayList<String>();
		for(int z = 0 ; z < combination.length;z++){
			
			result.add(""+elements[combination[z]]);
		}
		//printList(result);
		return result;
	}
	
	public static ArrayList<Property> toListProperty(int[] combination, Object[] elements){
		ArrayList<Property> result = new ArrayList<Property>();
		for(int z = 0 ; z < combination.length;z++){
			
			result.add((Property)elements[combination[z]]);
		}
		//printList(result);
		return result;
	}
	
	public static void printList(ArrayList<ArrayList<String>> mylist){
		for(int i=0; i< mylist.size(); i++){
			for(int j=0; j< mylist.get(i).size(); j++){
				if(j!=0)
					System.out.print(" ,");	
				System.out.print(mylist.get(i).get(j));
			}
			System.out.println();
		}
	}
	public static String toStringList(ArrayList<Property> mylist){
			String result = "";
			for(int j=0; j< mylist.size(); j++){
				if(j!=0)
					result+=" ,";	
				result+=mylist.get(j);
			}
			return result;
			//System.out.println();
	}
}
