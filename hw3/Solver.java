package hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.Move;

/**
 * A puzzle solver for the the Block Slider game.
 * <p>
 * THE ONLY METHOD YOU ARE CHANGING IN THIS CLASS IS solve().
 */
public class Solver {
	/**
	 * Maximum number of moves allowed in the search.
	 */
	private int maxMoves;

	/**
	 * Associates a string representation of a grid with the move count required to
	 * reach that grid layout.
	 */
	private Map<String, Integer> seen = new HashMap<String, Integer>();

	/**
	 * All solutions found in this search.
	 */
	private ArrayList<ArrayList<Move>> solutions = new ArrayList<ArrayList<Move>>();

	/**
	 * Constructs a solver with the given maximum number of moves.
	 * 
	 * @param givenMaxMoves maximum number of moves
	 */
	public Solver(int givenMaxMoves) {
		maxMoves = givenMaxMoves;
		solutions = new ArrayList<ArrayList<Move>>();
	}

	/**
	 * Returns all solutions found in the search. Each solution is a list of moves.
	 * 
	 * @return list of all solutions
	 */
	public ArrayList<ArrayList<Move>> getSolutions() {
		return solutions;
	}

	/**
	 * Prints all solutions found in the search.
	 */
	public void printSolutions() {
		for (ArrayList<Move> moves : solutions) {
			System.out.println("Solution:");
			for (Move move : moves) {
				System.out.println(move);
			}
			System.out.println();
		}
	}

	/**
	 * EXTRA  15 POINTS
	 * <p>
	 * Recursively search for solutions to the given board instance according to the
	 * algorithm described in the assignment pdf. This method does not return
	 * anything its purpose is to update the instance variable solutions with every
	 * solution found.
	 * 
	 * @param board any instance of Board
	 */
	/**
	 * firstly checked if the total count is greater than the maximum moves
	 * then checked used "containsKey" which is used to check if the mapping for the specified key is existing in the hashmap
	 * then checked if the game is over, if true used ".add" to add the move history
	 * then checked if the game is not over
	 * if non of the conditions satisfy, used "put" 
	 * used put (key,value) to define the specified value with the specific key in this map
	 * then made an ArrayList of type "Move"
	 * used a for loop and called solve(board) recursively
	 * @param board
	 */
	public void solve(Board board) {
		ArrayList<Move> g = board.getAllPossibleMoves();
		if (board.getMoveCount() > maxMoves) {
			return;
		} 
		
		//}
	else if (board.isGameOver()) {
		this.solutions.add(board.getMoveHistory()); // recording the move history as a solution
			return;
		} 
		else if(seen.containsKey(board.toString())) {
			if (seen.get(board.toString()) >= board.getMoveCount()) { // the "get" parameter is used in hashmaps
				return;
			} 
			else {
				seen.replace(board.toString(), board.getMoveCount());
			}
		}
		else if (!board.isGameOver()) {
			//this.solutions.add
			return;
		}
		 
		else {
			seen.put(board.toString(), board.getMoveCount());
		}
		for (Move f : g) {
			Block block = f.getBlock();
			int row = block.getFirstRow();
			int col = block.getFirstCol();
			// solve(board)
			board.moveGrabbedBlock(f.getDirection());
			board.grabBlockAtCell(row, col);
		//}
			solve(board);  // the recursively called method
			board.undoMove();
		}
			
	}
}
