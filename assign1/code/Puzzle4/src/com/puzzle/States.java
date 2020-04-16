package com.puzzle;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class States {

	Set initState = new HashSet<ArrayList<String>>() ;
	static int cleari, clearj;
	static List<String> possibleMoves = new ArrayList<String>() ;


	public States() {
		// TODO Auto-generated constructor stub
	}
					// retrieves the clear block from the state
	public static  void getClearBlock(Set initState) {
		int cli =0 ,clj = 0;
		for (Iterator iterator = initState.iterator(); iterator.hasNext();) {
			ArrayList object = (ArrayList) iterator.next();
			//System.out.println(object.get(0));
			if(Integer.parseInt(object.get(0).toString()) ==0) {
				cli =Integer.parseInt(object.get(1).toString());
				clj =Integer.parseInt(object.get(2).toString());
			}
		}
		cleari = cli;
		clearj = clj;
	}
				//retrieves the adjacent tile from the state
	public static int getNumberfromState(Set initState2, int i, int j) {
		int num =0;

		for (Iterator iterator = initState2.iterator(); iterator.hasNext();) {
			ArrayList object = (ArrayList) iterator.next();
			//System.out.println(object.get(0));
			if((Integer.parseInt(object.get(1).toString()) == i) && (Integer.parseInt(object.get(2).toString()) == j) ) {
				//System.out.println("yes");
				num = Integer.parseInt(object.get(0).toString());
				return num; 
			}
		}
		return num;
	}
				// retrieves the possible using strips
	public  static List<String> getPossibleMoves(String node) {
		Set initialState = new Converter().getValuesinSet(node);
		String tempState;
		Strips par; 
		getClearBlock(initialState);
		int number =0;
		if ((cleari - 1) > 0) {
			number = getNumberfromState(initialState, (cleari - 1), clearj);
			par = new Strips(initialState);
			par.move(number, cleari, clearj, (cleari-1), clearj);
			tempState = new Converter().getValuesinString(par.getIniState());
			possibleMoves.add(tempState);
			tempState = null;
		}

		if ((cleari + 1) < 4) {
			initialState =  new Converter().getValuesinSet(node);
			number = getNumberfromState(initialState, (cleari + 1), clearj);
			par = new Strips(initialState);
			par.move(number,cleari,clearj,cleari+1,clearj);;
			tempState = new Converter().getValuesinString(par.getIniState());
			possibleMoves.add(tempState);
			tempState = null;

		}

		if ((clearj - 1) > 0) {
			initialState =  new Converter().getValuesinSet(node);
			number = getNumberfromState(initialState, cleari , clearj-1);
			par = new Strips(initialState);
			par.move(number, cleari, clearj, cleari, clearj-1);
			tempState = new Converter().getValuesinString(par.getIniState());
			possibleMoves.add(tempState);
			tempState = null;
		}

		if ((clearj + 1) < 4) {
			initialState =  new Converter().getValuesinSet(node);
			number = getNumberfromState(initialState,cleari, clearj+1);
			par = new Strips(initialState);
			par.move(number, cleari, clearj, cleari, clearj+1);
			tempState = new Converter().getValuesinString(par.getIniState());
			possibleMoves.add(tempState);
			tempState = null;
		}
		return possibleMoves;

	}

}
