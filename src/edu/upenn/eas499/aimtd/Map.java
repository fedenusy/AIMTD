package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The TD map.
 * @author fedenusy
 *
 */
public class Map {

	// Instance variables
	Tile[][] tiles;
	HashMap<Tile, ArrayList<Tile>> edges;
	
	// Public methods
	/**
	 * Convenience method, identical to Map(int[][] layout, true).
	 * @param layout The map's layout.
	 */
	public Map(int[][] layout) {
		this(layout, true);
	}
	
	/**
	 * @param layout The layout of the map represented as an array of rows; rows are themselves arrays 
	 * of integers. Within rows, 0s represent Field tiles (upon which towers can be built), 1s 
	 * represent Road tiles (upon which monsters can travel), and 2s represent Objective tiles (which 
	 * monsters are trying to reach). 8s represent Rock tiles, which are dead space. For AIMTD's purposes, 
	 * the map will be built as a square with length=(# of row arrays) and width=(# of integers in the first row). 
	 * Undefined row integers will be treated as Rocks. Example 5x5 map layout:
	 * new int[][] {	{1,1,0,0,0},
	 * 					{0,1,8,0,0},
	 * 					{0,1,1,8,0},
	 * 					{0,0,0,1,0},
	 * 					{0,0,0,0,2} };
	 * @param createEdges Whether to automatically generate the edges between Road/Objective tiles and other 
	 * adjacent Road/Objective tiles, including diagonals. Monsters can only travel between two Road/Objective tiles 
	 * if there exists an edge between them.
	 * 
	 */
	public Map(int[][] layout, boolean createEdges) {
		if (layout==null || layout[0]==null) throw new IllegalArgumentException("Invalid map layout");
		
		int length = layout.length;
		int width = layout[0].length;
		tiles = new Tile[length][width];
		
		for (int i=0; i<length; i++) {
			for (int j=0; j<width; j++) {
				try {
					if (layout[i][j]==0) tiles[j][i] = new Tile(j, i, Tile.Type.FIELD);
					else if (layout[i][j]==1) tiles[j][i] = new Tile(j, i, Tile.Type.ROAD);
					else if (layout[i][j]==2) tiles[j][i] = new Tile(j, i, Tile.Type.OBJECTIVE);
					else tiles[j][i] = new Tile(j, i, Tile.Type.ROCK);
				} catch (ArrayIndexOutOfBoundsException e) {
					tiles[j][i] = new Tile(j, i, Tile.Type.ROCK);
				}
			}
		}
		
		edges = new HashMap<Tile, ArrayList<Tile>>();
		
		for (int i=0; i<length; i++) {
			for (int j=0; j<width; j++) {
				if(tiles[i][j].isWalkable()) 
					edges.put(tiles[i][j], new ArrayList<Tile>());
			}
		}

		if (createEdges) for (Tile walkable : edges.keySet()) {
			int x = walkable.getX();
			int y = walkable.getY();
			for (int offX=-1; offX<=1; offX++) {
				for (int offY=-1; offY<=1; offY++) {
					try {
						Tile candidate = tiles[x+offX][y+offY];
						if (!candidate.isWalkable()) continue;
						edges.get(walkable).add(candidate);
					} catch (ArrayIndexOutOfBoundsException e) { }
				}
			}
		}

	}
	
	/**
	 * Creates an Edge from (x1,y1) to (x2,y2), thus allowing Monsters to travel between 
	 * the two Tiles.
	 * @param biDirectional Whether another edge should be created from (x2,y2) to (x1,y1).
	 * @return Whether the edge was successfully created.
	 */
	public boolean createEdge(int x1, int y1, int x2, int y2, boolean biDirectional) {
		try {
			Tile one = tiles[x1][y1], two = tiles[x2][y2];
			if (!one.isWalkable() || !two.isWalkable()) return false;
			edges.get(one).add(two);
			if (biDirectional) edges.get(two).add(one);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) { return false; }
	}
	
	/**
	 * @param x The tile's x-coordinate.
	 * @param y The tile's y-coordinate.
	 * @return A collection of all neighboring Tiles.
	 */
	public Collection<Tile> getNeighbors(Tile t) {
		return edges.get(tiles[t.getX()][t.getY()]);
	}
	
	
}
