package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.Direction;
import api.Move;
import api.Orientation;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 */

public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left corner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory;
	
	/**
	 * An ArrayList of type Object for the grid at the start
	 */
	
	private ArrayList<Object> gridAtStart;
	
	/**
	 * variable to define the grabbed cell
	 */

	private Cell grabedCell;
	
	/**
	 * the value of the grabbed block in the grid
	 */

	private Block grabedBlock;
	
	/**
	 * A boolean variable to test if the game has ended
	 */

	private boolean test;
	
	/**
	 * A int variable to count the number of moves in the game
	 */

	private int count;
	

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	/**
	 * Constructed a constructor by initiallizing variables
	 * used a for loop to set each cell into the grid
	 * @param grid
	 * @param blocks
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		this.grid = grid;
		this.blocks = blocks;
		this.moveHistory = new ArrayList<Move>();
		this.gridAtStart = new ArrayList<Object>();
		test = false;
		int i;
		for (Block f : blocks) {
			int len = f.getLength();
			int row = f.getFirstRow();
			int col = f.getFirstCol();
			if (f.getOrientation() == Orientation.HORIZONTAL) {
				for (i = col; i < col + len; ++i) {
					this.grid[row][i].setBlock(f);
				}
			}
			else if (f.getOrientation() == Orientation.VERTICAL) {
				for (i = row; i < row + len; ++i) {
					this.grid[i][col].setBlock(f);
				}
			}
		}
	}

	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		grabedBlock = grid[row][col].getBlock();
		grabedCell = grid[row][col];
		
		
	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		grabedBlock = null;
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		return grabedBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		return grabedCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	/**
	 * checked if the block can be placed by testing conditions "isWall()" and "hasBlock()"
	 * returns true or false depending on the conditions used
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canPlaceBlock(int row, int col) {
		boolean y = true;
		if (grid[row][col].isWall() == true || grid[row][col].hasBlock() == true) {
			y = false;
		}
		else {
			y = true;
		}

	return y;

	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	
	public int getMoveCount() {
		return this.moveHistory.size();
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */

	public int getRowSize() {
		return this.grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return this.grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	
	public ArrayList<Block> getBlocks() {
		return this.blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		return test;
	}

	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	/**
	 * firstly checked if the grabed Block is null or if the game has ended
	 * then proceded by checking whether the orientation is horizontal and direction is RIGHT
	 * then checked if the block can be placed towards the right by using the method "canPlaceBlock"
	 * proceded to check all possible conditions in the same way
	 * @param dir
	 */
	public void moveGrabbedBlock(Direction dir) {
		
		if (this.grabedBlock == null || this.isGameOver()) {
			return;
		}
		else if (this.grabedBlock.getOrientation() == VERTICAL) {
			if (this.grabedBlock.getOrientation() == VERTICAL && dir == Direction.DOWN) {
				if (canPlaceBlock(grabedBlock.getFirstRow() + grabedBlock.getLength(),grabedBlock.getFirstCol())) {
					grid[grabedBlock.getFirstRow() + grabedBlock.getLength()][grabedBlock.getFirstCol()].setBlock(grabedBlock);
					//grid[grabedBlock.getLength()][grabedBlock.getFirstCol()].setBlock(grabedBlock);
					grabedBlock.move(dir);
					grid[grabedBlock.getFirstRow() - 1][grabedBlock.getFirstCol()].clearBlock();
					moveHistory.add(new Move(grabedBlock, dir));
					count += 1;
				if(grid[getGrabbedBlock().getFirstRow() + getGrabbedBlock().getLength() - 1][getGrabbedBlock().getFirstCol()].isExit())
					test = true;
				}
			}
			//}
			//}
			else if (this.grabedBlock.getOrientation() == VERTICAL && dir == Direction.UP) {
				if (canPlaceBlock(grabedBlock.getFirstRow() - 1, grabedBlock.getFirstCol())) {
					grid[grabedBlock.getFirstRow() - 1][grabedBlock.getFirstCol()].setBlock(grabedBlock);
					grabedBlock.move(dir);
					grid[grabedBlock.getLength() + grabedBlock.getFirstRow()][grabedBlock.getFirstCol()].clearBlock();
					moveHistory.add(new Move(grabedBlock, dir));
					count += 1;
				if(grid[getGrabbedBlock().getFirstRow() - 1][getGrabbedBlock().getFirstCol()].isExit())
					test = true;
				}
			}
		}
		else if (this.grabedBlock.getOrientation() == HORIZONTAL) {
			if (this.grabedBlock.getOrientation() == HORIZONTAL && dir == Direction.LEFT) {
				//	if (canPlaceBlock(grabedBlock.getFirstRow(), grabedBlock.getFirstCol() - getGrabbedBlock().getLength())) 
					if (canPlaceBlock(grabedBlock.getFirstRow(), grabedBlock.getFirstCol() - 1)) {
						this.getCell(grabedBlock.getFirstRow(), grabedBlock.getFirstCol() - 1).setBlock(grabedBlock);
						grabedBlock.move(dir);
						grid[grabedBlock.getFirstRow()][grabedBlock.getFirstCol() + grabedBlock.getLength()].clearBlock();
						moveHistory.add(new Move(grabedBlock, dir));
						count += 1;
					if(grid[getGrabbedBlock().getFirstRow()][getGrabbedBlock().getFirstCol() - 1].isExit())
						test = true;
					}
				}
			else if (this.grabedBlock.getOrientation() == HORIZONTAL && dir == Direction.RIGHT) {
				if (canPlaceBlock(grabedBlock.getFirstRow(), grabedBlock.getLength() + grabedBlock.getFirstCol())) {
					grid[grabedBlock.getFirstRow()][grabedBlock.getLength() + grabedBlock.getFirstCol()].setBlock(grabedBlock);
					grabedBlock.move(dir);
					//}
					
					grid[grabedBlock.getFirstRow()][grabedBlock.getFirstCol() - 1].clearBlock();
					moveHistory.add(new Move(grabedBlock, dir));
					count += 1;
				if(grid[getGrabbedBlock().getFirstRow()][getGrabbedBlock().getFirstCol() + getGrabbedBlock().getLength() - 1].isExit())
					test = true;
	
				}
			}
		}
	releaseBlock();
}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	/**
	 * firstly used a for loop to reset the blocks in the grid
	 * then set the blocked using a for loop by checking if orientation is HORIZONTAL or VERTICAL
	 */
	public void reset() {
		int i;
		int k;
		int r = grid.length;
		int c = grid[0].length;
		count = 0;
		test = false;
		moveHistory.clear();
		for (Block f : blocks) {
			f.reset();
		}
		for (i = 0; i < r; ++i) {
			for (k = 0; k < c; ++k) {
				grid[i][k].clearBlock();
			}
		}
		for (Block f : blocks) {
			int co = f.getFirstCol();
			int ro = f.getFirstRow();
			int le = f.getLength();
			if (f.getOrientation() == VERTICAL) {
				//for (i = f.getFirstCol(); i <  +; ++i) {
				for (i = ro; i < le + ro; ++i) {
					this.grid[i][co].setBlock(f);
				}
			}
			else {
				for (i = co; i < le + co; ++i) {
					this.grid[ro][i].setBlock(f);
				}
			}
		}
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		int j;
		ArrayList<Move> moves = new ArrayList<Move>();
		if (test == true) {
			return null;
		} 
		else {
			for (j = 0; j < blocks.size(); ++j) {
				if (blocks.get(j).getOrientation() == Orientation.HORIZONTAL) {
					if (canPlaceBlock(blocks.get(j).getFirstRow(), blocks.get(j).getFirstCol() + blocks.get(j).getLength())) {
						moves.add(new Move(blocks.get(j), Direction.RIGHT));
					}
					else if (canPlaceBlock(blocks.get(j).getFirstRow() - 1, blocks.get(j).getFirstCol())) {
						moves.add(new Move(blocks.get(j), Direction.LEFT));
					}
					
					//f.getFirstCol()
				}
				else if (blocks.get(j).getOrientation() == Orientation.VERTICAL) {
					if (canPlaceBlock(blocks.get(j).getFirstRow()+ blocks.get(j).getLength(), blocks.get(j).getFirstCol())) {
						moves.add(new Move(blocks.get(j), Direction.DOWN));
					}
					//}
					
					else if (canPlaceBlock(blocks.get(j).getFirstRow(), blocks.get(j).getFirstCol() - 1)) {
						moves.add(new Move(blocks.get(j), Direction.UP));
					}
				}
			}
		}
return moves;
}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		Move f = moveHistory.get(-1); // -1 is used because to check from the last/behind
		
		if (f.getDirection() == Direction.LEFT) {
			f.getBlock().move(LEFT);
		}	
		else if (f.getDirection() == Direction.RIGHT) { 
			f.getBlock().move(RIGHT);
		}
	//}
		else if (f.getDirection() == Direction.DOWN) {
			f.getBlock().move(DOWN);
			//f.getBlock
		}
		else if (f.getDirection() == Direction.UP) {
			f.getBlock().move(UP);
		}
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}