package com.ai2;

/*
 * Code written by Gurjit Singh
 * Student ID -200392341
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Starter {

	public static Map<ArrayList<String>, Set<ArrayList<Integer>>> constraints = new HashMap<ArrayList<String>, Set<ArrayList<Integer>>>();

	////////  shaveta a3
	public static Map<String, Set<String>> dependencies = new HashMap<String, Set<String>>();
	public static Map<String, Map<String, ArrayList<Integer>>> cpt = new HashMap<String, Map<String, ArrayList<Integer>>>();
	public   ArrayList<Node> variablesFinal = new  ArrayList<Node>();
	public static Map<ArrayList<String>, Set<ArrayList<Integer>>> newConstraints = new HashMap<ArrayList<String>, Set<ArrayList<Integer>>>();;
	private static int n,np =0;
	private static double p=0.00, a =0.0, r =0.0;
	private static int variableSize =0;
	private static ArrayList<Integer> domain = null;
	private static String[] variables;
	public static	long startTime = 0;
	public static Map<Integer, ArrayList<Node>> solutions = new HashMap<Integer, ArrayList<Node>>();;
	public static boolean allsolfound = false;
	public static int numOfsol = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				if (args.length != 5){	getUsage();	}
		
				getValues(args);
//		n = 6 ;
//		p = 0.33;
//		a = 0.8;
//		r = 0.7;
//		np = 3 ;
		System.out.println("");
		System.out.println("Values  entered  :  " + n + "     " + p + "    " + a + "     " + r );
		System.out.println("Number of parents  :  " + np );

		BinCsp csp = new BinCsp(n, p, r, a, np);

		variableSize = csp.getVariableSize();
		dependencies = csp.getDepend();
		constraints = csp.constraints;
		variables = csp.variables;
		domain = csp.domain;
		cpt = csp.getCpt();

		System.out.println("Please choose an option in a number format");
		int input = readMethodChoice();
		int numOftotsol = 100;
		

		startTime =  System.nanoTime();;

		ArrayList<Node> arr = null;
		long endTime = 0;
		try
		{
			switch (input) {
			case 1:

				for (int i = 0; i < numOftotsol; i++) {
					BackTrack b = new BackTrack(variables, constraints, domain, dependencies, cpt,solutions);
					arr = b.backtrack(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}


				break;
			case 2:
				for (int i = 0; i < numOftotsol; i++) {
					ForwardCheck fc = new ForwardCheck(variables, constraints, domain, dependencies, cpt, solutions);
					arr = fc.forwardCheck(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}

				break;
			case 3:
				for (int i = 0; i < numOftotsol; i++) {
					FullLookAhead flc = new FullLookAhead(variables, constraints, domain, dependencies, cpt, solutions);
					arr = flc.fullLookAhead(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}

				break;
			case 4:
				arcConsistency();
				for (int i = 0; i < numOftotsol; i++) {
					BackTrack ba = new BackTrack(variables, newConstraints, domain, dependencies, cpt, solutions);
					arr = ba.backtrack(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}
				//csp.printConstraints(newConstraints);

				break;
			case 5:
				arcConsistency();
				for (int i = 0; i < numOftotsol; i++) {
					ForwardCheck fca = new ForwardCheck(variables, newConstraints, domain, dependencies, cpt, solutions);
					arr = fca.forwardCheck(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}
				break;
			case 6:
				arcConsistency();
				for (int i = 0; i < numOftotsol; i++) {
					FullLookAhead flca = new FullLookAhead(variables, newConstraints, domain, dependencies, cpt, solutions);
					arr = flca.fullLookAhead(0);
					if(!arr.isEmpty())
						solutions.put(i, arr);
					//csp.addConstraints(constraints,arr);
				}

				break;
			}
		}
		catch(Exception e){
			if(e.getMessage().equalsIgnoreCase("String index out of range: -1"));
				//System.out.println("Not enough solutions");
				
		}

		totalSolutions();
	}

	private static void arcConsistency() {
		// TODO Auto-generated method stub
		Map<ArrayList<String>, Set<ArrayList<Integer>>> constraintsTemp = new HashMap<ArrayList<String>, Set<ArrayList<Integer>>>();;
		constraintsTemp = constraints;
		boolean status = false;
		String var1 = null;
		String var2 = null;
		for(int i=0;i<variables.length;i++) {
			for(int j=0;j<variables.length;j++) {
				if(variables[i] == variables[j]) {
					continue;
				}
				else {
					var1 = variables[i];
					var2= variables[j];
					for(int k=0;k<domain.size();k++) {
						if(getValuetoRemovefromDomain(var1, var2,k)) {
							//System.out.println(var1 + var2 + k);
							//System.out.println();
							constraintsTemp = resetConstraints(var1, var2,k,constraintsTemp);
						}

					}
				}
			}

		}
		newConstraints.putAll(constraintsTemp);

	}

	private static void totalSolutions() {
		System.out.println("Total possible solutions found: " + solutions.size());
		System.out.println("Time taken " + (System.nanoTime()-new Starter().startTime)/1e6 + "ms");
		System.out.println();
		System.out.println("Please provide the number of pareto optimal solutions required");
		numOfsol = readNumberOfSol();
		startTime =  System.nanoTime();;
		System.out.println("Pareto optimal solutions are as follows: ");
		ArrayList<Node> variablesFinal = null;
		for (int r = 0; r < numOfsol; r++) {
			variablesFinal = solutions.get(r);
			int temp = r+1;
			System.out.println("solution no. :" + temp);
			for(int i=0;i<variablesFinal.size();i++) {
				System.out.print(variablesFinal.get(i).variable + "  ");
				System.out.print(variablesFinal.get(i).value);
				System.out.println();
			}
			System.out.println();
		}
		
		System.out.println("Time taken " + (System.nanoTime()-new Starter().startTime)/1e6 + "ms");
	}


	private static void bestSolutions() {
		ArrayList<Node> variablesFinal = null;
		for (int r = 0; r < solutions.size(); r++) {
			variablesFinal = solutions.get(r);
			for(int i=0;i<variablesFinal.size();i++) {
				System.out.print(variablesFinal.get(i).variable + "  ");
				System.out.print(variablesFinal.get(i).value);
				System.out.println();
			}
			System.out.println();
		}
	}


	private static Map<ArrayList<String>, Set<ArrayList<Integer>>> resetConstraints(String var1, String var2, int k, Map<ArrayList<String>, Set<ArrayList<Integer>>> constraintsTemp) {
		// TODO Auto-generated method stub

		ArrayList<String> arr = new ArrayList<>();
		Set<ArrayList<Integer>> set;// = new HashSet<>();
		Set<ArrayList<Integer>> newset = new HashSet<>();
		ArrayList<String> arrVar = new ArrayList<>();
		arrVar.add(var1);arrVar.add(var2);

		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraintsTemp.entrySet().iterator();

		while (entries.hasNext()) {
			Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
			arr = new ArrayList<>();
			arr = entry.getKey(); 
			newset = new HashSet<>();
			//if((arr.get(0).equals(var1) && arr.get(1).equals(var2))|| (arr.get(0).equals(var2) && arr.get(1).equals(var1))) {
			if(arr.equals(arrVar)) {
				set = new HashSet<>();
				set = entry.getValue();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
					if((arr.get(0).equals(arrVar.get(0)) && arrayList.get(0).equals(k)) || (arr.get(1).equals(arrVar.get(0) ) && arrayList.get(1).equals(k)) ) {
						continue;
					}
					else {

						newset.add(arrayList);
					}
				}
				constraintsTemp.put(arr, newset);			
			}


		}

		return constraintsTemp;
	}

	private static boolean getValuetoRemovefromDomain(String var1, String var2,
			int value) {
		// TODO Auto-generated method stub
		ArrayList<String> arr = new ArrayList<>();
		ArrayList<Integer> temparr = new ArrayList<>();
		boolean status = false;

		Set<ArrayList<Integer>> set;// = new HashSet<>();
		Iterator<Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>>> entries = constraints.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<ArrayList<String>, Set<ArrayList<Integer>>> entry = entries.next();
			arr = new ArrayList<>();
			arr = entry.getKey(); 
			if((arr.get(0).equals(var1) && arr.get(1).equals(var2))) {//|| (arr.get(0).equals(var2) && arr.get(1).equals(var1))) {
				set = new HashSet<>();
				set = entry.getValue();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
					if((arr.get(0).equals(var1 ) && arrayList.get(0).equals(value)) ) {
						temparr.add(arrayList.get(1));
						//						System.out.println(var1 + "," + var2);
					}
					if((arr.get(1).equals(var1 ) && arrayList.get(1).equals(value)) ) {
						temparr.add(arrayList.get(0));
						//						System.out.println(var2 + "," + var1);
					}

				}
			}
		}
		if(domain.size() == temparr.size()) {
			status = true;
			temparr.clear();
		}

		//status = true;
		return status;
	}

	private static int readMethodChoice() {
		// TODO Auto-generated method stub
		int input =0;
		System.out.println();
		System.out.println("Please provide number for the search strategies" + "\n"
				+ "1. BT Standard Backtracking" + "\n"
				+ "2. FC Forward Checking" + "\n" 
				+ "3. FLA Full Look Ahead"  + "\n" 
				+ "4. BT Standard Backtracking with Arc consistency" + "\n"
				+ "5. FC Forward Checking with Arc consistency" + "\n" 
				+ "6. FLA Full Look Ahead with Arc consistency ");


		try {
			input = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return input;
	}

	private static int readNumberOfSol() {
		// TODO Auto-generated method stub
		int input =0;
		System.out.println();
		try {
			input = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return input;
	}

	
	private static void getValues(String[] args) {
		// TODO Auto-generated method stub
		n = Integer.parseInt(args[0]);
		p = Double.parseDouble(args[1]);
		a = Double.parseDouble(args[2]);
		r = Double.parseDouble(args[3]);
		np = Integer.parseInt(args[4]);
	}

	private static void getUsage() {
		// TODO Auto-generated method stub
		System.out.println("Usage: java -jar csp.jar  <number of variables> <constraint tightness> <constant r> <constant a> <number of parents np>");
		System.out.println("Ex-  java -jar csp.jar 4 0.33 0.8 0.7 2");
		System.exit(-1);
	}

}
