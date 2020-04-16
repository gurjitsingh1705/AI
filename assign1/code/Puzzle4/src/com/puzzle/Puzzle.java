package com.puzzle;

public class Puzzle {

	public static void main(String[] args) {
		
		if (args.length != 3)
		{
			getUsage();
		}
		
		
		int depth = Integer.parseInt(args[0]);
		String iniState = args[1];
		String goalState = args[2];
										//initializing the class with the  initial state and goal state
		DFS dfs = new DFS( new Node(iniState),goalState);
		System.out.println("Starting the process");
		System.out.println("Initial State    -   "  + iniState );
		System.out.println("Goal State       -   "  + goalState);
										// algorithm to traverse from initial state to goal state
		dfs.myDepthSearch(depth);
		
		System.out.println("Process ends");
	}

	private static void getUsage() {
		// TODO Auto-generated method stub
		System.out.println("Usage: java -jar puzzle.jar Main <depth> [Initial State] [Goal State]");
		System.out.println("Ex-  java -jar puzzle.jar 10 123456780 234516870");
		System.exit(-1);
	}

}
