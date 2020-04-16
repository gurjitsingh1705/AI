package com.ai2;

import java.util.ArrayList;
import java.util.Collections;
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
	private int numberOfParents = 0;
	public String[] variables  ;
	private int variableSize = 0;
	ArrayList<Integer> domain = new ArrayList();;

	public Map<ArrayList<String>,Set<ArrayList<Integer>>> constraints = new HashMap<>();
	public Map<String, Set<String>> depend = new HashMap<String, Set<String>>();
	public Map<String, Map<String, ArrayList<Integer>>> cpt = new HashMap<String, Map<String, ArrayList<Integer>>>();
	
	
	public Map<String, Set<String>> getDepend() {
		return depend;
	}
	public void setDepend(Map<String, Set<String>> depend) {
		this.depend = depend;
	}
	public Map<String, Map<String, ArrayList<Integer>>> getCpt() {
		return cpt;
	}
	public void setCpt(Map<String, Map<String, ArrayList<Integer>>> cpt) {
		this.cpt = cpt;
	}
	public BinCsp(int n, double p, double r, double a,int np) {
		// TODO Auto-generated constructor stub
		this.numberOfVariables = n;

		this.numberOfParents = np;
		this.domainSize =(int) Math.pow(n, a);
		this.numberOfConstraints= Math.round((float) (n *r *  Math.log(n)));
		this.nIncompatibleTuples = (int)Math.round(domainSize * domainSize * p);
		System.out.println("incompatible tuples pd^2   : "  + this.nIncompatibleTuples);
		System.out.println("numberOfConstraints rnln   : "  + this.numberOfConstraints);
		getVariables();
		getConstraints(numberOfVariables)	;
		printConstraints(constraints);
		getDependencies();
		printDependencies(cpt,depend);

	}
	public void printDependencies(Map<String, Map<String, ArrayList<Integer>>> cpt,
			Map<String, Set<String>> depend) {
		// TODO Auto-generated method stub
		System.out.println( );
		System.out.println("********************* Printing dependencies *********************");
		System.out.println("Variables \t" + "Parents");
		for (String keys : depend.keySet())
		{
			
			System.out.print(keys + " : \t\t");
			Set<String> selected2 = depend.get(keys);
			if(selected2.size() == 0) {
				System.out.print("Nil");
			}
			else {
				for(String var : selected2){
					System.out.print(var + " " );
				}
			}
			System.out.println();
		}
		System.out.println( );
		System.out.println("********************* Printing cpt *********************");
		for (String keys : cpt.keySet())
		{
			System.out.println(keys); 
			Map<String, ArrayList<Integer>> tempeachVar = cpt.get(keys) ;
			for (String keys2 : tempeachVar.keySet())
			{
				System.out.print(keys2 + " : ");
				ArrayList<Integer> array = tempeachVar.get(keys2);
				for (int q = 0; q < array.size(); q++) {
					if(q == array.size()-1) {
						System.out.print(array.get(q));}
					else
						System.out.print(array.get(q) + " > " );
				}
				for(Integer var : array){

				}
				System.out.println( );
			}

			System.out.println( );
		}
		
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

	public void getDependencies() {
		for(int i=0; i <getVariableSize();i++) {
			//System.out.println(variables[i]);
			//list.get(rand.nextInt(list.size())); 
			Map<String, ArrayList<Integer>> eachVar = new HashMap<String, ArrayList<Integer>>();
			int random = 0;
			ArrayList<Integer> temp_domain = domain;
			Set<String> selected = new HashSet<String>();
			if(i==0) {
				random = 0;

				depend.put(variables[i], selected);
				//////////// does not need selected as no parent
				Collections.shuffle(temp_domain);
				eachVar.put("Nil",temp_domain);
				cpt.put(variables[i], eachVar);
			}
			else if(i <= numberOfParents) {
				random = new Random().nextInt(i);
				for(int k =0; k<=random;k++) {
					int random2 = new Random().nextInt(i);
					selected.add(variables[random2]);
					//System.out.println(variables[random2]);
				}
				depend.put(variables[i], selected);
				eachVar = generateCPT(eachVar,selected);
				cpt.put(variables[i], eachVar);
			}
			else {
				random = new Random().nextInt(numberOfParents);
				for(int k =0; k<=random;k++) {
					int random2 = new Random().nextInt(i);
					selected.add(variables[random2]);
					//System.out.println(variables[random2]);
				}
				depend.put(variables[i], selected);
				eachVar = generateCPT(eachVar,selected);
				cpt.put(variables[i], eachVar);
			}
		}
	}


	private Map<String, ArrayList<Integer>> generateCPT(Map<String, ArrayList<Integer>> eachVar, Set<String> selected) {
		// TODO Auto-generated method stub
		ArrayList<Integer> temp_domain = domain;
		if(selected.size()==0) {
			Collections.shuffle(temp_domain);
			eachVar.put("Nil", temp_domain);
		}
		else if(selected.size()==1) { 
			for (int l = 0; l < temp_domain.size(); l++) {
				temp_domain = domain;
				Collections.shuffle(temp_domain);
				ArrayList<Integer> td = new ArrayList();;
				td.addAll(temp_domain);
				eachVar.put(Integer.toString(l), td);
				//System.out.println(eachVar.size() + "  " +  domain.get(l) + " " + l);
			}

		}
		else if(selected.size()==2) { 
			for (int m = 0; m < temp_domain.size(); m++) {
				for (int n = 0; n < temp_domain.size(); n++) {
					Collections.shuffle(temp_domain);
					ArrayList<Integer> td1 = new ArrayList();;
					td1.addAll(temp_domain);
					String str = m +"," + n;
					eachVar.put(str, td1);
				}
			}

		}
		else if(selected.size()==3) { 
			for (int m = 0; m < temp_domain.size(); m++) {
				for (int n = 0; n < temp_domain.size(); n++) {
					for (int o = 0; o < temp_domain.size(); o++) {
						Collections.shuffle(temp_domain);
						ArrayList<Integer> td1 = new ArrayList();;
						td1.addAll(temp_domain);
						String str = m +"," + n + "," + o;
						eachVar.put(str, td1);
					}
				}
			}

		}
		return eachVar;
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
	
	
	public void addConstraints(Map<ArrayList<String>, Set<ArrayList<Integer>>> constraints2, ArrayList<Node> newConstraints) {
		Set<ArrayList<Integer>> set = new HashSet<>();
		ArrayList<String> arr2 = new ArrayList<>();
		ArrayList<Integer> temparr = new ArrayList<>();
		for (int q = 0; q <newConstraints.size()-1; q++) {
			arr2.add(newConstraints.get(q).variable);
			temparr.add(newConstraints.get(q).value);
		}
			if(constraints2.containsKey(arr2)  ) {//check for reverse too
				set = constraints.get(arr2);
				set.add(temparr);
				constraints.put(arr2, set);
				//printConstraints(constraints);
			}
			else {
				set = new HashSet<>();
				set.add(temparr);
				System.out.println(arr2);
				System.out.println(set);
				constraints.put(arr2, set);
			}
			printConstraints(constraints);	
			
	}
//			for (int q = 0; q <newConstraints.size()-1; q++) {
//				Set<ArrayList<Integer>> set;// = new HashSet<>();
//				ArrayList<String> arr2 = new ArrayList<>();
//				ArrayList<Integer> temparr = new ArrayList<>();
//				ArrayList<String> arr3 = new ArrayList<>();
//				ArrayList<Integer> temparr3 = new ArrayList<>();
//				arr2.add(newConstraints.get(q).variable);
//				arr2.add(newConstraints.get(q+1).variable);
//				temparr.add(newConstraints.get(q).value);
//				temparr.add(newConstraints.get(q+1).value);
//				arr3.add(newConstraints.get(q+1).variable);
//				arr3.add(newConstraints.get(q).variable);
//				temparr3.add(newConstraints.get(q+1).value);
//				temparr3.add(newConstraints.get(q).value);
//				if(constraints2.containsKey(arr2)  ) {//check for reverse too
//					set = constraints.get(arr2);
//					set.add(temparr);
//					constraints.put(arr2, set);
//					//printConstraints(constraints);
//				}
//				else if(constraints2.containsKey(arr3)  ) {
//					set = constraints.get(arr3);
//					set.add(temparr3);
//					constraints.put(arr3, set);
//				}
//				else {
//					set = new HashSet<>();
//					set.add(temparr);
//					constraints.put(arr2, set);
//				}
			
//		}
		
//		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints2.entrySet().iterator();
//		while (entries.hasNext()) {
//			Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
//			arr = new ArrayList<>();
//			arr = entry.getKey();
//			set = new HashSet<>();
//			
//			System.out.print("(" + arr.get(0) + "," + arr.get(1) + ") :   " );
//			set = entry.getValue();
//			for (Iterator<ArrayList<Integer>> iterator = set.iterator(); iterator.hasNext();) {
//				ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
//				System.out.print(arrayList.get(0) + "," + arrayList.get(1) + "   ");
//			}
//			System.out.println();
//		}

	
	
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
