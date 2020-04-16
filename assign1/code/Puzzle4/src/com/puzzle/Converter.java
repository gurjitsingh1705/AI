package com.puzzle;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Converter {

	public  Set getValuesinSet(String node) {
		// TODO Auto-generated method stub
		Set iniState = new HashSet<ArrayList<Integer>>();
		ArrayList arr =null;
		int k=1;
		for(int i=0;i<=2;i++) {
			arr = new ArrayList<Integer>();
			arr.add(node.charAt(i));
			arr.add(k);
			arr.add(i+1);
			iniState.add(arr);
		}
		k=2;
		for(int i=3;i<=5;i++) {
			arr = new ArrayList<Integer>();
			arr.add(node.charAt(i));
			arr.add(k);
			arr.add(i-2);
			iniState.add(arr);
		}
		k=3;
		for(int i=6;i<=8;i++) {
			arr = new ArrayList<Integer>();
			arr.add(node.charAt(i));
			arr.add(k);
			arr.add(i-5);
			iniState.add(arr);
		}
		//System.out.println(iniState);
		
		return iniState;
	}
	
	public String getValuesinString(Set node) {
		StringBuffer sb = new StringBuffer();
		String s1 = "0",s2 = null,s3 = null;
		String s4 = null,s5 = null,s6 = null;
		String s7 = null,s8 = null,s9 = null;
		
		int i =1, j=1;
		//for(int k=1;k<=9;k++) {
		for (Iterator iterator = node.iterator(); iterator.hasNext();) {
			ArrayList object = (ArrayList) iterator.next();
			
				if(Integer.parseInt(object.get(1).toString()) == 1 && Integer.parseInt(object.get(2).toString()) == 1) {
						s1=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 1 && Integer.parseInt(object.get(2).toString()) == 2) {
						s2=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 1 && Integer.parseInt(object.get(2).toString()) == 3) {
						s3=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 2 && Integer.parseInt(object.get(2).toString()) == 1) {
						s4=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 2 && Integer.parseInt(object.get(2).toString()) == 2) {
						s5=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 2 && Integer.parseInt(object.get(2).toString()) == 3) {
						s6=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 3 && Integer.parseInt(object.get(2).toString()) == 1) {
						s7=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 3 && Integer.parseInt(object.get(2).toString()) == 2) {
						s8=object.get(0).toString();
					}
				if(Integer.parseInt(object.get(1).toString()) == 3 && Integer.parseInt(object.get(2).toString()) == 3) {
						s9=object.get(0).toString();
					}
				}
			
		//	}
	
		
		sb.append(s1);
		sb.append(s2);
		sb.append(s3);
		sb.append(s4);
		sb.append(s5);
		sb.append(s6);
		sb.append(s7);
		sb.append(s8);
		sb.append(s9);
		
		String str = sb.toString();
		return str;
	}
	
}
