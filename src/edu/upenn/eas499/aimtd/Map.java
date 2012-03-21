package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The TD map.
 * @author fedenusy
 *
 */
public class Map {

	// Instance variables
	Tile[][] tiles;
	HashMap<Tile, ArrayList<Edge>> edges;
	
	/**
	 * Instantiates the TD map.
	 * @param layout The layout of the map represented as an array of rows, which are themselves arrays 
	 * of integers. Within rows, 0s represent Field tiles (upon which towers can be built), 1s 
	 * represent Road tiles (upon which monsters can travel), and 2s represent Objective tiles (which 
	 * monsters are trying to reach). 8s represent Rock tiles, which are dead space. For AIMTD's purposes, 
	 * the map will be built as a square with length=(# of row arrays) and width=(# of integers in the first row). 
	 * Undefined row integers will be treated as Rocks.
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
					if (layout[i][j]==0) tiles[i][j] = new Tile(j, i, Tile.Type.FIELD);
					else if (layout[i][j]==1) tiles[i][j] = new Tile(j, i, Tile.Type.ROAD);
					else if (layout[i][j]==2) tiles[i][j] = new Tile(j, i, Tile.Type.OBJECTIVE);
					else tiles[i][j] = new Tile(j, i, Tile.Type.ROCK);
				} catch (ArrayIndexOutOfBoundsException e) {
					tiles[i][j] = new Tile(j, i, Tile.Type.ROCK);
				}
			}
		}
		
		edges = new HashMap<Tile, ArrayList<Edge>>();
		
		for (int i=0; i<length; i++) {
			for (int j=0; j<width; j++) {
				if(tiles[i][j].isWalkable()) 
					edges.put(tiles[i][j], new ArrayList<Edge>());
			}
		}

		if (createEdges) for (Tile walkable : edges.keySet()) {
			int x = walkable.getX();
			int y = walkable.getY();
			for (int offY=-1; offY<=1; offY++) {
				for (int offX=-1; offX<=1; offX++) {
					try {
						Tile candidate = tiles[y+offY][x+offX];
						if (!candidate.isWalkable()) continue;
						edges.get(walkable).add(new Edge(walkable, candidate));
					} catch (ArrayIndexOutOfBoundsException e) { }
				}
			}
		}

	}
	
	
}
