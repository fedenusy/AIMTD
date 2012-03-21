package edu.upenn.eas499.aimtd;

/**
 * An edge connecting two Tile.Type.ROAD tiles in the TD map.
 * @author fedenusy
 *
 */
class Edge {

	// Instance variables
	private Tile t1, t2;
	
	
	public Edge(Tile t1, Tile t2) {
		if ( !Tile.Type.ROAD.equals(t1.getType()) || !Tile.Type.ROAD.equals(t2.getType()) )
			throw new IllegalArgumentException("Edges can only exist between two ROAD tiles");
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/**
	 * @return The length of the edge, computed as the Euclidean distance between Tiles.
	 */
	public double getLength() {
		return Math.sqrt((t1.getX()-t2.getX())^2+(t1.getY()-t2.getY())^2);
	}
	
}

