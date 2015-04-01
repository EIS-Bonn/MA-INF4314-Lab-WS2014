package com.chartadvisor.model;

import java.util.*;

import com.chartadvisor.utils.Combinations;

public class Allocation {
	
	public Allocation(){
		super();
		leftAllocations = null;
		rightAllocations = null;
	}
	public Allocation(ArrayList<Property> left, ArrayList<Property> right){
		super();
		leftAllocations = left;
		rightAllocations = right;
	}
	public Allocation(Allocation alloc){
		super();
		leftAllocations = new ArrayList<Property>(alloc.leftAllocations);
		rightAllocations = new ArrayList<Property>(alloc.rightAllocations);
	}
	
	public ArrayList<Property> leftAllocations, rightAllocations;
	
	public String toString(){
		String printed = "(";
		printed += Combinations.toStringList(leftAllocations);
		printed	+=")---> (";
		printed += Combinations.toStringList(rightAllocations);
		printed += ")";
		//System.out.println();
		return printed;
		
	}
	
	
	public boolean equals(Object o){
		if(!(o instanceof Allocation))
			return false;
		else{
			Allocation alloc = (Allocation)o;
			Allocation removeClosure = new Allocation(this);
			if(this.rightAllocations.get(0).getPropertyType().endsWith("*")){
				this.rightAllocations.get(0).setPropertyType(this.rightAllocations.get(0).getPropertyType().substring(0, this.rightAllocations.get(0).getPropertyType().length()-1));
				removeClosure.rightAllocations.clear();
				for(int i=0; i<alloc.rightAllocations.size(); i++)
					removeClosure.rightAllocations.add(this.rightAllocations.get(0));
			}
			if(removeClosure.getLength()!=alloc.getLength())
				return false;
			else{
				for (Property s : alloc.leftAllocations){
					if (!removeClosure.leftAllocations.contains(s))
							return false;
				}
				for (Property s : alloc.rightAllocations){
					if (!removeClosure.rightAllocations.contains(s))
							return false;
				}
				return true;
			}
		}
	}
	
	
	public static Allocation toLOMAllocation(Allocation alloc){
		ArrayList<Property> left = new ArrayList<Property>();
		ArrayList<Property> right = new ArrayList<Property>();
		for (Property s : alloc.leftAllocations){
			String LOM = Dictionary.getLOM(s.getPropertyName(), s.getPropertyType());
			left.add(new Property("",LOM));
		}
		for (Property s : alloc.rightAllocations){
			String LOM = Dictionary.getLOM(s.getPropertyName(), s.getPropertyType());
			right.add(new Property("",LOM));
		}
		
		return new Allocation(left, right);
	}
	
	public int getLength(){
		return this.leftAllocations.size()+this.rightAllocations.size();
	}
	
}
