package com.ai3;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyClass {

	static boolean status = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 2){	getUsage();	}

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String term1 =null;
		String term2 = null;
		term1 = args[0];
		term2 = args[1];
		//		try {
		//			term1 = br.readLine();
		//			term2 = br.readLine();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//String result = unify(term1,term2);
		unify(term1,term2);

	}


	private static void unify(String term1, String term2) {
		// TODO Auto-generated method stub
		String result = null;
		if(isNumeric(term1) && isNumeric(term2)) {
			if(Integer.parseInt(term1) == Integer.parseInt(term2)) {
				result= term1 + " = " +  term2 + "\n"
						+ "Yes";}
			else {
				result= term1 + " != " +  term2 + "\n"
						+ "No";
			}
		}
		else if(isNumeric(term1) || isNumeric(term2)) 
		{
			if(isVariable(term1) && isNumeric(term2)) {
				result = term1 + " = " +  term2 + "\n"
						+ "Yes";
			}
			else if(isVariable(term2) && isNumeric(term1)) {
				result = term2 + " = " +  term1 + "\n"
						+ "Yes";
			}
			else if(isConstant(term1) && isNumeric(term2) || isConstant(term2) && isNumeric(term1)) {
				result = "Incorrect assignment \nConstant cannot be assigned a value";
			}

		}
		else if(isVariable(term1) && isVariable(term2) || isVariable(term1) && isConstant(term2) || isConstant(term1) && isVariable(term2)){
			result = term2 + " = " +  term1 + "\n"
					+ "Yes";
		}
		else if(isConstant(term1) && isConstant(term2)){
			result = "Incorrect assignment \nConstant cannot be assigned a constant";
		}
		else {
			//System.out.println("Its an expression");
			result = expressionUnify(term1,term2);
		}
		System.out.println("\nResult");
		if(status == true)
			System.out.println("Unification cannot be done");

		else //if(status = true){
		{
			if(result.equals("null")) {
				System.out.println("Cannot be Unified");
			}
			else
				System.out.println(result);
		}
	}

	private static String expressionUnify(String term1, String term2) {
		// TODO Auto-generated method stub
		String result = null;
		if(term1.length()>1 &&   !isNumeric(term2)) {
			int i=0;
			for( i=0;i<term1.length();i++) {
				if( i+2 != term1.length() || i+2 != term1.length()) {
					if(term1.charAt(i) == '(' && term1.charAt(i+1) == term2.charAt(0) && term1.charAt(i+2) == ')' ) {
						result = "Fail, Recursive function found";
						break;
					}
				}
			}
			if(i==term1.length()) {
				result = term2 + " = " + term1;
			}
		}
		/*
f(x, g(X,Y))
f(f(V,U),g(U,Y))
		 */
		if(term2.length()>1 && !isNumeric(term1)) {
			int i=0;
			for( i=0;i<term2.length();i++) {
				if( i+2 != term2.length() || i+2 != term2.length()) {
					if(term2.charAt(i) == '(' && term2.charAt(i+1) == term1.charAt(0) && term2.charAt(i+2) == ')' ) {
						result = "Fail, Recursive function found";
						break;
					}
				}
			}
			if(i==term2.length()) {
				result = term1 + " = " + term2;
			}
		}

		if(term1.length()>1 && term2.length()>1) {
			List<String> term1List = new ArrayList<>();
			List<String> term2List = new ArrayList<>();
			term1List = getList( term1);
			term2List = 	getList( term2);
			//System.out.println(term1List);
			//System.out.println(term2List);
			ArrayList<String> finalMap = compareListsForResult(term1List,term2List);
			Map<String,String> fin = new HashMap<>();
			fin = processMap(finalMap);
			if(fin !=null)
				result = fin.toString();
			else
				result = "null";
		}

		return result;
	}


	private static Map<String,String> processMap(ArrayList<String> finalMap) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<>();
		int check =0;
		//while(status==false) {
		for(int i=0; i<finalMap.size();i++) {
			String str = finalMap.get(i);
			for(int j=0;j<str.length();j++) {
				String sub1 = null;
				String sub2 = null;
				if(str.charAt(j)=='|') {
					sub1 = str.substring(0, j);
					sub2 = str.substring(j+1,str.length());
//					System.out.println(sub1);
//					System.out.println(sub2);
					if(sub1.length()>1 && sub2.length()>1) {
						int count1 = getParenthesesCount(sub1);
						int count2 = getParenthesesCount(sub2);
						List<String> var = new ArrayList<>();
						List<String> var2 = new ArrayList<>();
						if(count1>count2) {
							var = getList(sub2);
							boolean whileCheck = false;
							int executionNum =1;
							while(whileCheck == false) {

								if(isVariable(var.get(0).toString()) || isConstant(var.get(0).toString()) || isNumeric(var.get(0).toString())){
									whileCheck = true;

								}else {
									var = getList(sub2);
									executionNum++;
								}
							}

							while(executionNum!=0) {
								var2 = getList(sub1);
								executionNum--;
							}


							sub2 = var.get(0).toString();
							sub1 = var2.get(0).toString();
						}
						else if(count2>count1) {
							var = getList(sub1);
							boolean whileCheck = false;
							int executionNum =1;
							while(whileCheck == false) {

								if(isVariable(var.get(0).toString()) || isConstant(var.get(0).toString()) || isNumeric(var.get(0).toString())){
									whileCheck = true;

								}else {
									var = getList(sub1);
									executionNum++;
								}
							}

							while(executionNum!=0) {
								var2 = getList(sub2);
								executionNum--;
							}


							sub2 = var.get(0).toString();
							sub1 = var2.get(0).toString();
						}

					}
					/*
					 * 
					 * 
					 * 
					 * 
					 * f(f(x,y),g(I),f(i(k)))
f(f(P,E),g(k(L)),f(i(o(p(I)))))
					 */
					if(isVariable(sub1) && isVariable(sub2) ) {

						if(map.size()!=0) {
							Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<String,String> entry = entries.next();
								if(entry.getKey().equals(sub1) && !(entry.getValue().equals(sub2))) {
									map.put(sub2,sub1);
								}
								else if(entry.getKey().equals(sub2) && !(entry.getValue().equals(sub1))) {
									map.put(sub1, sub2);
								}
								else {
									check = 1;
								}
							}
						}
						else{
							map.put(sub1, sub2);
							map.put(sub2,sub1);
						}
						if(check ==1) {
							map.put(sub1, sub2);
							map.put(sub2,sub1);
						}
					}
					else if(isVariable(sub1) && (isNumeric(sub2) || isConstant(sub2)))
					{
						if(map.size()!=0) {
							Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<String,String> entry = entries.next();
								if(entry.getKey().equals(sub1) && !(entry.getValue().equals(sub2))) {
									System.out.println("!1 Illegal assignment");
									status = true;
								}
								else if(entry.getKey().equals(sub2) && !(entry.getValue().equals(sub1))) {
									System.out.println("!2 Illegal assignment");
									status = true;
								}
								else {
									check = 1;
								}
							}
						}
						else{
							map.put(sub1, sub2);
							map.put(sub2,sub1);
						}
						if(check ==1) {
							map.put(sub1, sub2);
							map.put(sub2,sub1);
						}
					}
					else if((isNumeric(sub1) || isConstant(sub1)) && isVariable(sub2))
					{
						String temp = sub1;
						sub1 = sub2;
						sub2 = temp;
						if(map.size()!=0) {
							Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<String,String> entry = entries.next();
								if(entry.getKey().equals(sub1) && !(entry.getValue().equals(sub2))) {
									System.out.println("!3 Illegal assignment");
									status = true;
								}
								else if(entry.getKey().equals(sub2) && !(entry.getValue().equals(sub1))) {
									System.out.println("!4 Illegal assignment");
									status = true;
								}
								else {
									check = 1;
								}
							}
						}
						else {
							map.put(sub1, sub2);
						}
						if(check ==1) {
							map.put(sub1, sub2);
						}

					}	//map.put(sub2, sub1);
					else if((isVariable(sub1) || isConstant(sub1) || isNumeric(sub1)) && sub2.length()>1) {
						if(map.size()!=0) {
							Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<String,String> entry = entries.next();
								if(entry.getKey().equals(sub1) && !(entry.getValue().length()>1)) {
									String strTemp = entry.getValue();
									map.put(sub1, sub2);
									map.put(strTemp, sub2);
								}
								else {
									check = 1;
								}
							}
						}
						else {
							map.put(sub1, sub2);
						}
						if(check ==1) {
							map.put(sub1, sub2);
						}
						//System.out.println(map);
					}
					else if	((isVariable(sub2) || isConstant(sub2) || isNumeric(sub2)) && sub1.length()>1) {
						if(map.size()!=0) {
							Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<String,String> entry = entries.next();
								if(entry.getKey().equals(sub2) && !(entry.getValue().length()>1)) {
									String strTemp = entry.getValue();
									map.put(sub2, sub1);
									map.put(strTemp, sub1);
								}
								else {
									check =1;
								}
							}
						}
						else {
							map.put(sub2, sub1);
						}
						if(check ==1) {
							map.put(sub2, sub1);
						}
					}
				}

			}
		}

		//System.out.println(map);
		map = processFinalmap(map);
		return map;
	}


	private static Map<String, String> processFinalmap(Map<String, String> map) {
		// TODO Auto-generated method stub
		Map<String, String> mapNew = new HashMap<>();
		mapNew.putAll(map);
		Map<String, String> extraMap = new HashMap<>();
		Iterator<Map.Entry<String,String>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String,String> entry = entries.next();
			Iterator<Map.Entry<String,String>> entries2 = map.entrySet().iterator();
			while (entries2.hasNext()) {
				Map.Entry<String,String> entry2 = entries2.next();
				if(entry2.getValue().contains(entry.getKey()) && !(isVariable(entry2.getValue()))) {
					String str = entry2.getValue().replace(entry.getKey(), entry.getValue());
					mapNew.put(entry2.getKey(), str);
				}
				if(entry2.getValue().equals(entry.getKey()) && (isVariable(entry2.getValue()))){
					/////remove the extra pair 
					mapNew.remove(entry.getKey());
					extraMap.put(entry.getKey(), entry.getValue());
				}
			}
		}


		mapNew = comparingMapsforFinalResults(extraMap,mapNew);

		return mapNew;
	}


	private static Map<String,String> comparingMapsforFinalResults(Map<String, String> extraMap, Map<String, String> mapNew) {
		// TODO Auto-generated method stub
		Iterator<Map.Entry<String,String>> entries = extraMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String,String> entry = entries.next();
			Iterator<Map.Entry<String,String>> entries2 = extraMap.entrySet().iterator();
			while (entries2.hasNext()) {
				Map.Entry<String,String> entry2 = entries2.next();

				if(entry2.getValue().equals(entry.getKey()) && (isVariable(entry2.getValue()))){
					/////remove the extra pair 
					if(!mapNew.containsKey(entry.getValue()) && !mapNew.containsKey(entry.getKey())) {
						mapNew.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}


		Iterator<Map.Entry<String,String>> finalEntries = mapNew.entrySet().iterator();
		while (finalEntries.hasNext()) {
			Map.Entry<String,String> entry = finalEntries.next();
			if(isVariable(entry.getKey()) || isConstant(entry.getKey()) || isNumeric(entry.getKey())) {
				for(int i=0;i<entry.getValue().length();i++) {
					if( i+2 != entry.getValue().length() || i+2 != entry.getValue().length()) {
						if(entry.getValue().charAt(i) == '(' && entry.getValue().charAt(i+1) == entry.getKey().charAt(0) && entry.getValue().charAt(i+2) == ')' ) {
							System.out.println("Recursive Function found \nNo solution");
							mapNew = null;
						}
					}
				}
			}
			else if(isVariable(entry.getValue()) || isConstant(entry.getValue()) || isNumeric(entry.getValue())) {
				for(int i=0;i<entry.getKey().length();i++) {
					if( i+2 != entry.getKey().length() || i+2 != entry.getKey().length()) {
						if(entry.getKey().charAt(i) == '(' && entry.getKey().charAt(i+1) == entry.getKey().charAt(0) && entry.getKey().charAt(i+2) == ')' ) {
							System.out.println("Recursive Function found \nNo solution");
							mapNew = null;
						}
					}
				}
			}

		}



		return mapNew;
	}


	private static ArrayList<String> compareListsForResult(List<String> term1List, List<String> term2List) {
		// TODO Auto-generated method stub

		ArrayList<String> finalMap = new ArrayList<>();

		for(int i=0;i<term1List.size();i++) {
			if(term1List.get(i).length()>1 && term2List.get(i).length()>1) {
				List<String> temp1 = new ArrayList<>();
				temp1=getList(term1List.get(i));
				//System.out.println(temp1);
				////////////////
				List<String> temp2 = new ArrayList<>();
				temp2=getList(term2List.get(i));
				//System.out.println(temp2);
				if(temp1.size() == temp2.size()) {
					for (int k = 0; k < temp1.size(); k++) {
						if(isVariable(temp1.get(k)) && isVariable(temp2.get(k))) {
							finalMap.add(temp1.get(k) +"|" + temp2.get(k));
						}
						else if(isVariable(temp1.get(k)) && (isNumeric(temp2.get(k)) || isConstant(temp2.get(k)))) {
							finalMap.add(temp1.get(k) +"|" + temp2.get(k));
						}
						else if(isVariable(temp2.get(k)) && (isNumeric(temp1.get(k)) || isConstant(temp1.get(k)))) {
							finalMap.add(temp2.get(k) +"|" + temp1.get(k));
						}
						else if(isVariable(temp2.get(k)) && temp1.get(k).length()>1) {
							finalMap.add(temp2.get(k) + "|" + temp1.get(k));
						}
						else if(isVariable(temp1.get(k)) && temp2.get(k).length()>1) {
							finalMap.add(temp1.get(k) + "|" + temp2.get(k));
						}

						else if(temp1.get(k).length()>1 && temp2.get(k).length()>1) {
							finalMap.add(temp1.get(k) + "|" + temp2.get(k));
						}


					}
				}
				///////////////////
			}else if((term1List.get(i).length()>1) && !(term2List.get(i).length()>1)) {
				finalMap.add (term1List.get(i) + "|" + term2List.get(i)); 
			}
			else if(!(term1List.get(i).length()>1) && (term2List.get(i).length()>1)) {
				finalMap.add (term1List.get(i) + "|" + term2List.get(i));
			}
			else if(!(term1List.get(i).length()>1) && !(term2List.get(i).length()>1)) {
				finalMap.add (term1List.get(i) + "|" + term2List.get(i));
			}
		}
//		System.out.println(finalMap);
		return finalMap;
	}


	/*
	 * f(f(x,y),g(I),f(i(o(p(I)))))
f(f(P,E),g(k(L)),f(i(k)))
	 */
	private static List<String> getList(String term1) {
		// TODO Auto-generated method stub
		//int countParentheses = 0;
		List<String> term1List = new ArrayList<>();
		//countParentheses = getParenthesesCount(term1);
		String term1Split = null;
		int i =0;
		while(i!=term1.length()) {
			if(!isVariable(String.valueOf(term1.charAt(i))) && (term1.charAt(i)!='(')){
				i++;
				continue;
			}else if (term1.charAt(i)=='(') {
				term1Split  = term1.substring(i+1, term1.length()-1) + "|";
				int k=0;
				while(k!=term1Split.length()) {
					//System.out.println(term1Split.charAt(k));
					if((  k+1 != term1Split.length() && !isVariable(String.valueOf(term1Split.charAt(k))) && term1Split.charAt(k+1)=='(')  ){
						for(int j=k;j<term1Split.length();j++) {
							if(term1Split.charAt(j)==')' && term1Split.charAt(j+1)==',') {
								term1List.add(term1Split.substring(k, j+1));
								if(j+1==term1Split.length()) {
									k=term1Split.length();
								}else {
									k=j;
								}
								break;
							}
							else if(term1Split.charAt(j)==')' && term1Split.charAt(j+1)=='|') {
								term1List.add(term1Split.substring(k, j+1));
								if(j+1==term1Split.length()) {
									k=term1Split.length();
								}else {
									k=j;
								}
								break;
							}
						}
					}
					else if(isVariable(String.valueOf(term1Split.charAt(k)))) {
						term1List.add(String.valueOf(term1Split.charAt(k)));
						k++;
					}
					else if( isConstant(String.valueOf(term1Split.charAt(k))) &&  !(k+1 > term1Split.length()) && !(term1Split.charAt(k+1)=='(')) {
						term1List.add(String.valueOf(term1Split.charAt(k)));
						k++;
					}
					else {
						k=k+1;
					}
				}
				i=i+k+1;
			}
		}
		return term1List;
	}

	private static int getParenthesesCount(String term1) {
		// TODO Auto-generated method stub
		int l=0,r=0;
		for(int i=0;i<term1.length();i++) {
			if(term1.charAt(i) == '('){
				l++;
			}
			else if(term1.charAt(i) == ')'){
				r++;
			}
		}
		if((l+r)%2==0) {
			return l+r;
		}else
			return 0;
	}

	public static  String[] getArray(String term){
		String arr[] = new String[term.length()];

		for(int i=0;i<term.length();i++) {
			if(term.charAt(i)==',') {
				continue;
			}
			else {
				arr[i] = String.valueOf(term.charAt(i));
			}
		}

		return arr;
	}

	public static boolean isVariable(String term){

		if(term.length()>1)
			return false;
		char variable = term.charAt(0);
		if(Character.isUpperCase(variable))
			return true;
		else
			return false;
	}

	public static boolean isConstant(String term){

		if(term.length()>1)
			return false;
		char variable = term.charAt(0);
		if(Character.isLowerCase(variable))
			return true;
		else
			return false;
	}


	private static void getUsage() {
		// TODO Auto-generated method stub
		System.out.println("Usage: java -jar unify.jar <first argument> <second argument> ");
		System.out.println("Ex-  java -jar unify.jar" + " \"" + "X" +"\""+" \""+ "f(x)" +"\"");
		System.exit(-1);
	}


	/*
	 * Code taken from 
	 * https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	 */
	public static boolean isNumeric(String str)
	{
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
}
