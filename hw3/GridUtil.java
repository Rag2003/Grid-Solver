package hw3;

import static api.Orientation.*;
import static api.CellType.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Orientation;
import hw3.Block;
import static hw3.Block.*;
import static org.junit.Assert.assertArrayEquals;


/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a block. A block is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * blocks or set blocks on cells.</li>
	 * </ul>
	 * The method only creates cells and not blocks. See the other utility method
	 * findBlocks which is used to create the blocks.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any blocks present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		int numRows = desc.length;
		int numCols = desc[0].length;
		String cell;
		CellType celltype;
		int i;
		int j;
		
		   Cell[][] game = new Cell[numRows][numCols];
		   
		   for (i = 0; i < numRows; ++i) {
		       for (j = 0; j < numCols; ++j) {
		           cell = desc[i][j];
		           celltype = CellType.FLOOR;
		           if (cell.equals("*")) {
		               celltype = CellType.WALL;
		           } 
		           //}
		           else if (cell.equals("e")) {
		               celltype = CellType.EXIT;
		           }
		           
		          game[i][j] = new Cell(celltype, i, j);
		       }
		   }
	return game;
	}

	/**
	 * Returns a list of blocks that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal block</li>
	 * <li>"]" the end (right most column) of a horizontal block</li>
	 * <li>"^" the start (top most row) of a vertical block</li>
	 * <li>"v" the end (bottom most column) of a vertical block</li>
	 * <li>"#" inner segments of a block, these are always placed between the start
	 * and end of the block</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * block currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of blocks found in the given grid description
	 */
	public static ArrayList<Block> findBlocks(String[][] desc) {
		int row_length = desc.length;
		int col_length = desc[0].length;
		int i;
		int j;
		int c;
		int rowFirst = 0;
		int colFirst = 0;
		ArrayList<Block> blocklist = new ArrayList<Block>();

		
		for(i = 0; i < row_length; ++i) {
			c = 0;
		    for(j = 0; j < col_length; ++j) {
		    	if (desc[i][j].equals("[")) {
		    			c = 0;
		    			c += 1;
		    			rowFirst = i;
		    			colFirst = j;
		            }
		    	//}
		    	else if(desc[i][j].equals("]")) {
		    	   c += 1;
		    	   Block obj = new Block(rowFirst, colFirst, c, Orientation.HORIZONTAL);
		    	   blocklist.add(obj);
		    	   c = 0;
		        }
		    	else if(desc[i][j].equals("#")) {
		        	c += 1;
		        }
		    	else {
		    		c = 0;
		    	}
		   }
		}
		//}
		
		for(j = 0; j < col_length; ++j) {
			c = 0;
		    for(i = 0; i < row_length; ++i) {
		    	 if(desc[i][j].equals("^")) {
		    		 c = 0;
			         c += 1;
			       	rowFirst = i;
		    		colFirst = j;
			     }
		    	 
		          
		    	 else if(desc[i][j].equals("v")) {
		        	c += 1;
		            Block obj = new Block(rowFirst, colFirst, c, Orientation.VERTICAL);
		            blocklist.add(obj);
		            c = 0;
		            
		        }
		    	 else if(desc[i][j].equals("#")) {
		    			c += 1;
		         }
		    	 else {
		    		 c = 0;
		    	 }
		     }
		}

	return blocklist;

		}
	
}
