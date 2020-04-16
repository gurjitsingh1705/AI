package com.puzzle;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;



public class DFS {
	// node to store the state
	private  Node root ;

	private String goalState;
	// stack saves the final list of states that leads to goal state
	private Stack<Node> finalStack = new Stack<Node>();

	public  DFS(Node state, String goalState2) {
		this.root = state;
		this.goalState = goalState2;
	}


	Node finalNode = null;

	// depth first search algorithm implementation
	public void myDepthSearch(int depth) {

		boolean depthFlag = false;
		Set<String> states = new HashSet<String>();
		Node node = new Node(root.getState());
		Stack<Node> stack = new Stack<Node>();
		Stack<Node> nodesFromStack = new Stack<Node>();
		node.setDepth(1);
		node.setParent(root);
		stack.push(node);
		// first stack loop for child states
		while(!stack.isEmpty()) {
			while(!stack.isEmpty()) {
				nodesFromStack.push(stack.pop()) ;
			}
			// second stack loop for parent states
			while(!nodesFromStack.isEmpty()) {

				Node pNode = nodesFromStack.pop();
				finalNode = pNode;
				if(pNode.getDepth() <=depth) {
					states.add(pNode.getState());

					if(!pNode.getState().equals(goalState) ) {
						// possible outcomes for a state are computed
						List<String> tempSuccessors = States.getPossibleMoves(pNode.getState());
						for (String string : tempSuccessors) {
							if(!states.contains(string)) {
								Node sNode = new Node(string);
								pNode.setChildren(sNode);
								states.add(sNode.getState());
								sNode.setParent(pNode);
								sNode.setDepth(pNode.getDepth()+1);

								// storing child states in the first stack
								stack.add(sNode);
							}
						}
					}
					else {
						stack.clear();
						nodesFromStack.clear();
					}
				}
				else {
					depthFlag=true;
				}
				if(depthFlag) {
					System.out.println("no solution found");

					System.exit(-1);
				}
			}
		}
		printSolution(finalNode);
	}
	//  method to print the solution
	private void printSolution(Node finalNode) {
		// TODO Auto-generated method stub
		Node preNode = null;
		preNode = finalNode;
		Node tempini = null;;
		Node tempfini;
		finalStack.push(preNode);
		while(!(preNode.getParent().getState().equals(root.getState()))) {
			finalStack.push(preNode.getParent());
			preNode = preNode.getParent();
		}
		finalStack.push(preNode.getParent());
		while(!finalStack.isEmpty()) {
			tempfini = finalStack.pop();
			if(!(tempini == null)){
			printMovement(tempini, tempfini);
			}
			printStack(tempfini);
			System.out.println("");
			System.out.println("");
			tempini = tempfini;
			//System.out.println(tempini);
		}
	}

	private void printMovement(Node tempini, Node tempfini) {
		// TODO Auto-generated method stub
		char first = '0', second = '0';
		for(int i=0;i<tempfini.getState().length();i++) {
			if(!(tempini.getState().charAt(i) == tempfini.getState().charAt(i)) ) {
				first = tempini.getState().charAt(i);
					second = tempfini.getState().charAt(i);
			}
		}
		
		System.out.println("Swap " + first + " and " + second);
	}
	private void printStack(Node pop) {
		// TODO Auto-generated method stub
		String str = pop.getState();
		for(int i=0;i<str.length();i++) {
			if(i==2 || i ==5) 
				System.out.println(str.charAt(i) + " ");
			else
				System.out.print( str.charAt(i) + " ");

		}

	}





}
