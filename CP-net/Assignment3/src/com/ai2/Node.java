package com.ai2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {

	
	public String variable;
	public Set<String> parents = new  HashSet<String>();
	public Map<String, ArrayList<Integer>> bestVals = new HashMap<String, ArrayList<Integer>>();
	public Map<String, ArrayList<Integer>> getBestVals() {
		return bestVals;
	}

	public void setBestVals(Map<String, ArrayList<Integer>> bestVals) {
		this.bestVals = bestVals;
	}

	public Set<String> getParents() {
		return parents;
	}

	public void setParents(Set<String> parents) {
		this.parents = parents;
	}

	public int value;
	
	 ArrayList<Integer> assignedVals = new ArrayList<>();
	
	 ArrayList<Integer> unassignedVals = new ArrayList<>();

	public Node(String variable, ArrayList<Integer> unassignedVals, Set<String> parents, Map<String, ArrayList<Integer>> bestVals) {
		// TODO Auto-generated constructor stub
		this.variable = variable;
		this.unassignedVals = unassignedVals;
		this.parents = parents;
		this.bestVals = bestVals;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public ArrayList<Integer> getAssignedVals() {
		return assignedVals;
	}

	public void setAssignedVals(ArrayList<Integer> assignedVals) {
		this.assignedVals = assignedVals;
	}

	public ArrayList<Integer> getUnassignedVals() {
		return unassignedVals;
	}

	public void setUnassignedVals(ArrayList<Integer> unassignedVals) {
		this.unassignedVals = unassignedVals;
	}
}
