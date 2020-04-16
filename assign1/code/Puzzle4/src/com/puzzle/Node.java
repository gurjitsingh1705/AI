package com.puzzle;
import java.util.ArrayList;


public class Node {
	 private String state;
	    private ArrayList<Node> children;
	    private Node parent;
	    
	    private int depth;


		public Node getParent() {
			return parent;
		}


		public void setParent(Node parent) {
			this.parent = parent;
		}


		public ArrayList<Node> getChildren() {
			return children;
		}


		public void setChildren(Node children) {
			this.children.add(children) ;
		}


	public Node(String inistate) {
		// TODO Auto-generated constructor stub
		this.state = inistate;
		this.children = new ArrayList<Node>();
		
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}

}
