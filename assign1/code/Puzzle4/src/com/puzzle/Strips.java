package com.puzzle;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Strips {
	private int x, i, j, k, l;
	
	Set iniState = new HashSet<ArrayList<Integer>>();

	int tempk = 0;
	int templ = 0;

	public Set getIniState() {
		return iniState;
	}

	public Strips(Set intState) {
		this.iniState = intState;
		//System.out.println(iniState);
	}

	public void move(int x, int i, int j, int k, int l) {
		this.x = x;
		this.i = i;
		this.j = j;
		this.k =k;
		this.l =l;
		if(preConditions( x, i, j, k, l)) {
			add(x, i, j, k, l);
			remove(0, i, j, tempk, templ);
		}
	}
	
	public boolean preConditions( int x, int i, int j, int k, int l)
	{
		if(onTile(x,k,l) == true && clearTile(i, j)==true && adjacentTile(x, k, l, i, j)==true) {
			return true;
		}
		return false;
	}

	
	public void add( int x, int i, int j, int k, int l) {
		tempk = i;
		templ = j;
		for (Iterator iterator = iniState.iterator(); iterator.hasNext();) {
			ArrayList arr = (ArrayList) iterator.next();
			if((Integer.parseInt(arr.get(0).toString()) ==x) 
					&& (Integer.parseInt(arr.get(1).toString()) ==k) 
					&& (Integer.parseInt(arr.get(2).toString()) ==l)  ){
				arr.set(1, i) ;
				arr.set(2, j) ;
			}
		}
	}

	public void remove( int x, int i, int j, int tempk, int templ) {
		for (Iterator iterator = iniState.iterator(); iterator.hasNext();) {
			ArrayList arr = (ArrayList) iterator.next();
			if((Integer.parseInt(arr.get(0).toString()) == x) 
					&& (Integer.parseInt(arr.get(1).toString()) ==tempk) 
					&& (Integer.parseInt(arr.get(2).toString()) ==templ)  ){
				arr.set(1, k) ;
				arr.set(2, l) ;;
			}
		}
	}

	public boolean onTile( int x, int i, int j) {
		for (Iterator iterator = iniState.iterator(); iterator.hasNext();) {
			ArrayList arr = (ArrayList) iterator.next();
			if( (Integer.parseInt(arr.get(0).toString()) ==x) 
					&& (Integer.parseInt(arr.get(1).toString()) ==i) 
					&& (Integer.parseInt(arr.get(2).toString()) ==j)  ){
				return true;
			}
		}

		return false;

	}

	public boolean clearTile( int k, int l) {
		for (Iterator iterator = iniState.iterator(); iterator.hasNext();) {
			ArrayList arr = (ArrayList) iterator.next();
			if((Integer.parseInt(arr.get(0).toString()) ==0) 
					&& (Integer.parseInt(arr.get(1).toString()) ==k) 
					&& (Integer.parseInt(arr.get(2).toString()) ==l)  ){
				return true;
			}
		}

		return false;

	}


	public boolean adjacentTile( int x, int i, int j, int k, int l) {
		for (Iterator iterator = iniState.iterator(); iterator.hasNext();) {
			ArrayList arr = (ArrayList) iterator.next();
			if((Integer.parseInt(arr.get(0).toString()) ==x) 
					&& (Integer.parseInt(arr.get(1).toString()) ==i) 
					&& (Integer.parseInt(arr.get(2).toString()) ==j)  ){
				if((i-1 ) > 0 && (i-1 ) == k && j == l) {
					return true;
				}
				if((i+1 ) < 4 && (i+1 ) == k && j == l) {
					return true;
				}
				if((j ) > 0 && (j-1 ) == l && i == k) {
					return true;
				}
				if((j ) > 0 && (j+1 ) == l && i == k) {
					return true;
				}
			}	
		}
		return false;
	}
}
