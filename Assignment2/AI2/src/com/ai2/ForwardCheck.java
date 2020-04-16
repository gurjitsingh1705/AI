package com.ai2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ForwardCheck {


	public  Map<ArrayList<String>, Set<ArrayList<Integer>>> constraints = new HashMap<ArrayList<String>, Set<ArrayList<Integer>>>();

	private  ArrayList<Integer> domain;
	private  String[] variables;

	public  ArrayList<Node> variablesFinal = new  ArrayList<Node>();

	public ForwardCheck(String[] variables, Map<ArrayList<String>, Set<ArrayList<Integer>>> constraints,
			ArrayList<Integer> domain) {
		// TODO Auto-generated constructor stub

		this.variables = variables;
		this.constraints = constraints;
		this.domain = domain;
	}

	public void forwardCheck(int level) {
		// TODO Auto-generated method stub
		if(level <0) {
			System.out.println("no Solution");
			System.out.println("Time taken " + (System.nanoTime()-new Starter().startTime)/1e6 + "ms");
			System.exit(0);
		}
		if(level>=variables.length) {
			for(int i=0;i<variablesFinal.size();i++) {
				System.out.print(variablesFinal.get(i).variable + "  ");System.out.print(variablesFinal.get(i).value);
				System.out.println();
			}
			System.out.println("Time taken " + (System.nanoTime()-new Starter().startTime)/1e6 + "ms");
			System.exit(0);
		}
		Node node = null;

		//	System.out.println(variablesFinal);
		boolean foundVal = false;
		if(!variablesFinal.isEmpty() && level != variables.length) {
			for(int k=0;k<variablesFinal.size();k++) 
				if(variablesFinal.get(k).getVariable().equals(variables[level]))
					node = variablesFinal.get(k);
		}
		if( !variablesFinal.contains(node)) {
			node	= new Node(variables[level], domain);
		}
		if(  level == 0 && node.getAssignedVals() !=null && domain.equals(node.getAssignedVals())) {
			
			System.out.println("No solution found");
			System.out.println("Time taken " + (System.nanoTime()-new Starter().startTime)/1e6 + "ms");
		}
		else {
			if( node.getAssignedVals().isEmpty() || node.getAssignedVals().size() < 1) {
				node.setUnassignedVals(domain);
			}
			if(level==0)
			{
				node.value = getUnassignedValsFromNode(node).get(0);
				node.assignedVals.add(node.value);
				node.setUnassignedVals(getUnassignedValsFromNode(node));
				if(variablesFinal.isEmpty()) {
					variablesFinal.add(node);}
				foundVal = true;
			}
			else {
				for(int i=0; i < node.getUnassignedVals().size(); i++)
				{
					foundVal = checkConstraint(level, node);
					if(foundVal == true) {
						if(!variablesFinal.contains(node)) {
							variablesFinal.add(node);
						}
						break;
					}
				}
			}
			if(foundVal) {
				level = level+1;
				forwardCheck(level);
			}
			else {
				for(int i=0;i<variablesFinal.size();i++) {
					if(i==level && variablesFinal.get(i).value == node.getValue()) {
						variablesFinal.remove(node);
					}
				}
				node.unassignedVals.clear();
				node.assignedVals.clear();
				node.value =0;
				node.variable = null;
				level = level -1;
				forwardCheck(level);
			}
		}
	}


	private  ArrayList<Integer> getUnassignedValsFromNode(Node node) {
		// TODO Auto-generated method stub
		ArrayList<Integer> arr = new ArrayList<>();
		if(node.getAssignedVals().size()<1) {
			arr.add(node.getUnassignedVals().get(0));
		}
		else {
			for(int i= 0; i<node.getUnassignedVals().size();i++){
				if(!node.getAssignedVals().contains(node.getUnassignedVals().get(i))) {
					arr.add(node.getUnassignedVals().get(i));	
				}
			}
		}
		return arr;
	}





	private  boolean checkConstraint(int level, Node node) {
		// TODO Auto-generated method stub

		boolean noPair = true;
		ArrayList<String> tempArr = new ArrayList<>();
		ArrayList<String> arr = new ArrayList<>();
		Set<ArrayList<Integer>> set;// = new HashSet<>();
		boolean status = false;
		tempArr.add(variables[level-1]);
		tempArr.add(node.variable);
		int preVarVal = 0;

		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
			arr = new ArrayList<>();
			arr = entry.getKey();
			if((arr.get(0).equals(tempArr.get(0)) && arr.get(1).equals(tempArr.get(1))) ||  ( arr.get(0).equals(tempArr.get(1)) &&  arr.get(1).equals(tempArr.get(0))) ) {
				set = new HashSet<>();
				set = entry.getValue();
				//System.out.println("pair found");
				// checks the previous node value for comparison in next step
				for(int i=0;i<variablesFinal.size();i++) {
					if(variablesFinal.get(i).getVariable().equals(tempArr.get(0))) {
						preVarVal = 	variablesFinal.get(i).getValue();
					}
				}
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
					if(arr.get(0).equals(tempArr.get(0)) && arrayList.get(0) == preVarVal) {
						node.assignedVals.add(arrayList.get(1));

					}
					else if(arr.get(1).equals(tempArr.get(0)) && arrayList.get(1) == preVarVal) {
						node.assignedVals.add(arrayList.get(0));
					}
					else if((arr.get(0).equals(tempArr.get(0)) && arrayList.get(0) != preVarVal) && (arr.get(1).equals(tempArr.get(0)) && arrayList.get(1) != preVarVal)){
						node.setAssignedVals(node.getUnassignedVals());
					}
				}
				if(node.assignedVals.equals(node.unassignedVals)) {
					status = false;
					node.assignedVals.clear();
					break;

				}
				else {
					node.assignedVals.clear();
					if((getUnassignedValsFromNode(node).get(0)) == null) {///////////////
						status = false;
					}
					else
					{////////////////////////////loop for fcac
						if (variables.length==level || ((variables.length-1)==level))
						{
							node.setValue( getUnassignedValsFromNode(node).get(0));
							node.assignedVals.add(node.value);
							node.setUnassignedVals(getUnassignedValsFromNode(node));
							status = true;
						}
						else {
							for(int i=0;i<node.getUnassignedVals().size();i++) {
								if(forwardCheckArc(node.getUnassignedVals().get(i), node, level) == true ){
									node.value  =getUnassignedValsFromNode(node).get(0);
									node.assignedVals.add(node.value);
									node.setUnassignedVals(getUnassignedValsFromNode(node));
									status = true;
								}
								else
									status = false;
							}
						}

					}
				}
				noPair = false;

			}


		}
		if(noPair) {
			////////////////////////loop for fcac
			if (variables.length==level || ((variables.length-1)==level))
			{
				node.setValue( getUnassignedValsFromNode(node).get(0));
				node.assignedVals.add(node.value);
				node.setUnassignedVals(getUnassignedValsFromNode(node));
				status = true;
			}
			else {
				for(int i=0;i<node.getUnassignedVals().size();i++) {
					if(forwardCheckArc(node.getUnassignedVals().get(i), node, level) == true ){
						node.value  =getUnassignedValsFromNode(node).get(0);
						node.assignedVals.add(node.value);
						node.setUnassignedVals(getUnassignedValsFromNode(node));
						status = true;
					}
					else
						status = false;
				}
			}
		}

		if(checkInCompatibilityWithOtherNodes(level, node)) {
			status = false;
		}
		return status;
	}



	private boolean forwardCheckArc(Integer value, Node node, int level) {
		// TODO Auto-generated method stub
		boolean status = false;

		ArrayList<String> arr = new ArrayList<>();
		ArrayList<Integer> temparr = new ArrayList<>();
		String var1 = node.getVariable();
		String var2 = variables[level+1];
		Set<ArrayList<Integer>> set;// = new HashSet<>();
		if(level < variables.length) {
			Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
				arr = new ArrayList<>();
				arr = entry.getKey(); 
				if((arr.get(0).equals(var1) && arr.get(1).equals(var2))|| (arr.get(0).equals(var2) && arr.get(1).equals(var1))) {
					set = new HashSet<>();
					set = entry.getValue();
					for (Iterator iterator = set.iterator(); iterator.hasNext();) {
						ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
						if((arr.get(0).equals(var1 ) && arrayList.get(0).equals(value)) ) {
							temparr.add(arrayList.get(1));
						}
						if((arr.get(1).equals(var1 ) && arrayList.get(1).equals(value)) ) {
							temparr.add(arrayList.get(0));
						}
					}
				}
			}
			if(domain.equals(temparr)) {
				status = false;
			}
			else
				status = true;
		}
		else {
			status = false;
		}


		return status;
	}

	private  boolean checkInCompatibilityWithOtherNodes(int level, Node node) {
		// TODO Auto-generated method stub
		Node first = null;
		Node second  = node;
		boolean incompatibilityCheck = false;
		for(int i=0;i<level;i++) {
//			if(level==0) {
//				incompatibilityCheck = false;
//			}

			if(i ==  level-1) {
				for(int k=1;k<=level;k++) {
					
						first = variablesFinal.get(k-1);
						incompatibilityCheck = getInCompatibility(first,second);
						if(incompatibilityCheck==true) {
							break;
						}
					
				}
			}

		}

		return incompatibilityCheck;
	}


	private  boolean getInCompatibility(Node first, Node second) {
		// TODO Auto-generated method stub
		boolean compatibilityCheck = false;
		ArrayList<String> arr = new ArrayList<>();
		Set<ArrayList<Integer>> set;// = new HashSet<>();
		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
			arr = new ArrayList<>();
			arr = entry.getKey();
			if(arr.get(0).equals(first.getVariable()) && arr.get(1).equals(second.getVariable())) {
				set=new HashSet<>();
				set = entry.getValue();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
					if(arrayList.get(0).equals(first.getValue()) && arrayList.get(1).equals(second.getValue()) ) {
						compatibilityCheck = true;
					}
				}
			}
			if(arr.get(1).equals(first.getVariable()) && arr.get(0).equals(second.getVariable())) {
				set=new HashSet<>();
				set = entry.getValue();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
					if(arrayList.get(1).equals(first.getValue()) && arrayList.get(0).equals(second.getValue()) ) {
						compatibilityCheck = true;
					}
				}
			}
		}
		return compatibilityCheck;
	}



	private  ArrayList<Integer> getRemainingValS(ArrayList<Integer> domain2, ArrayList<Integer> assignedVals) {
		// TODO Auto-generated method stub
		ArrayList<Integer> arr = new ArrayList<>();
		for (int i = 0; i < domain2.size(); i++) {
			for (int k = 0; k < assignedVals.size(); k++) {
				if(assignedVals.get(k) == domain2.get(i)) {
					continue;
				}
				else
					arr.add(domain2.get(i));
			}
		}
		return arr;
	}

}
