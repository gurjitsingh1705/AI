package com.ai2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BinCsp {

	private int numberOfVariables,domainSize =0;;
	private  int numberOfConstraints =0;
	private int nIncompatibleTuples;
	public String[] variables  ;
    private int variableSize = 0;
    ArrayList<Integer> domain = new ArrayList();;
    
	public Map<ArrayList<String>,Set<ArrayList<Integer>>> constraints = new HashMap<>();

	public BinCsp(int n, double p, double r, double a) {
		// TODO Auto-generated constructor stub
		this.numberOfVariables = n;


		this.domainSize =(int) Math.pow(n, a);
		this.numberOfConstraints= Math.round((float) (n *r *  Math.log(n)));
		this.nIncompatibleTuples = (int)Math.round(domainSize * domainSize * p);
		System.out.println("incompatible tuples pd^2   : "  + this.nIncompatibleTuples);
		System.out.println("numberOfConstraints rnln   : "  + this.numberOfConstraints);
		getVariables();
		getConstraints(numberOfVariables)	;
		printConstraints(constraints);

	}
	public void printConstraints(Map<ArrayList<String>, Set<ArrayList<Integer>>> constraints2) {
		// TODO Auto-generated method stub
		ArrayList<String> arr;// = new ArrayList<>();
		Set<ArrayList<Integer>> set;// = new HashSet<>();
		System.out.println("");
		System.out.println("*********************Printing the constraints and values***************************");
		System.out.print("Variables  :   ");
		for(int i =0; i<variables.length;i++) {
			if(i == (variables.length-1)){
				System.out.print(variables[i] );
			}
			else
				System.out.print(variables[i] + ",");
		}
		System.out.println();
		getDomain(this.domainSize);
		System.out.println("Constraints Incompatible");
		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints2.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
		    arr = new ArrayList<>();
		    arr = entry.getKey();
		    set = new HashSet<>();
		    System.out.print("(" + arr.get(0) + "," + arr.get(1) + ") :   " );
		    set = entry.getValue();
		    for (Iterator<ArrayList<Integer>> iterator = set.iterator(); iterator.hasNext();) {
				ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
				System.out.print(arrayList.get(0) + "," + arrayList.get(1) + "   ");
			}
		    System.out.println();
		}
		
	}
	
	private int[] getDomainValues(int domainSize2) {
		// TODO Auto-generated method stub
		int [] domain = null;
		for(int i=0; i <domainSize2;i++) {
			domain[i] = i;
		}
		return domain;
	}
	private void getDomain(int domainSize2) {
		// TODO Auto-generated method stub
		for(int i=0; i <domainSize2;i++) {
			domain.add(i);
			if(i==0) {
				
				System.out.print("Domain  :  {" + i + ",");
			}
			else if(i == (domainSize2-1)) {
				System.out.println(i +"}");
			}
			else
			System.out.print(i + ",");
		}
		
	}
	private void getConstraints(int numberOfVariables) {
		// TODO Auto-generated method stub

		Set<String> states = new HashSet<>();
		Set<ArrayList<String>> pairVariables = new HashSet<>(); 
		String var1 = null, var2 = null;
		outer:	while(pairVariables.size() != numberOfVariables) {
			ArrayList<String> consTemp = new ArrayList<>();
			var1 = variables[getRandomNumberforVariable(0,numberOfVariables)];
			var2 = variables[getRandomNumberforVariable(0,numberOfVariables)];
			String temp = var1 + var2;
			String temp2 = var2 + var1;

			again:	while(var1.equalsIgnoreCase(var2)){
				var1 = variables[getRandomNumberforVariable(0,numberOfVariables)];
				var2 = variables[getRandomNumberforVariable(0,numberOfVariables)];
				if(var1.equalsIgnoreCase(var2)) {
					continue again;
				}
				temp = var1 + var2;
				temp2 = var2 + var1;
			}

			if(states.contains(temp) || states.contains(temp2)) {
				continue outer;
			}else {
				states.add(temp);
				states.add(temp2);}
			consTemp.add(var1);
			consTemp.add(var2);
			pairVariables.add(consTemp);
			getValueforPairs();
			constraints.put(consTemp, getValueforPairs());

		}

		//System.out.println(constraints);
		//System.out.println(pairVariables );//var1 + "    " + var2);

	}
	private Set<ArrayList<Integer>> getValueforPairs() {
		// TODO Auto-generated method stub
		int var3 =0;
		int var4 =0;
		Set<String> states = new HashSet<>();
		Set<ArrayList<Integer>> pairValues = new HashSet<>(); 
		outer:	while(pairValues.size() != nIncompatibleTuples) {
			ArrayList<Integer> consTemp = new ArrayList<>();
			var3 = getRandomNumberforVariable(0,this.domainSize);
			var4 = getRandomNumberforVariable(0,this.domainSize);
			String temp = var3 + "" + var4 ;
			again:	while(states.contains(temp)){
				var3 = getRandomNumberforVariable(0,this.domainSize);
				var4 = getRandomNumberforVariable(0,this.domainSize);
				temp = var3 + "" + var4 ;
				if(states.contains(temp)) {
					continue again;
				}

				temp = var3 + "" + var4 ;
			}

			if(states.contains(temp)) {
				continue outer;
			}else {
				states.add(temp);
			}
			consTemp.add(var3);
			consTemp.add(var4);
			pairValues.add(consTemp);

		}
		//	System.out.println(pairValues);
		return pairValues;
	}
	private int getRandomNumberforVariable(int i, int j) {
		// TODO Auto-generated method stub
		Random r = new Random();
		//System.out.println(r.nextInt(numberOfVariables) );

		return r.nextInt(j);
	}
	public void getVariables() {
		// TODO Auto-generated method stub
		variables =  new String[numberOfVariables];
		for(int i=0; i<numberOfVariables; i++)
		{
			variables[i] =  "X" + i;
			//System.out.println(variables[i]);
			setVariableSize(getVariableSize() + 1);
		}

	}
	public int getVariableSize() {
		return variableSize;
	}
	public void setVariableSize(int variableSize) {
		this.variableSize = variableSize;
	}



}
