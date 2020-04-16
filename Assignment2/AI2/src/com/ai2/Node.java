package com.ai2;

import java.util.ArrayList;

public class Node {

	
	public String variable;
	
	public int value;
	
	 ArrayList<Integer> assignedVals = new ArrayList<>();
	
	 ArrayList<Integer> unassignedVals = new ArrayList<>();

	public Node(String variable, ArrayList<Integer> unassignedVals) {
		// TODO Auto-generated constructor stub
		this.variable = variable;
		this.unassignedVals = unassignedVals;
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
