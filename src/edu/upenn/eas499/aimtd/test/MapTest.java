package edu.upenn.eas499.aimtd.test;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.eas499.aimtd.Map;

public class MapTest {

	private int[][] layout15x15, layout20x20, layout15x10, layout25x25;
	
	@Before
	public void setUp() {
		int[][] layout15x15 = 
				new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,1,1,1,1,1,1,1,1,1,1,1,2},
								{0,1,0,0,1,0,0,0,0,0,0,0,0,0,1},
								{0,1,0,0,1,0,0,0,0,0,0,1,1,1,1},
								{0,0,1,0,1,0,0,0,0,0,1,0,0,0,0},
								{0,1,0,0,1,0,0,0,0,0,1,1,1,0,0},
								{0,1,0,0,1,0,0,0,0,1,0,0,1,0,0},
								{0,1,0,0,1,0,0,0,0,0,1,1,1,1,0},
								{1,0,0,0,0,1,1,1,1,0,0,0,0,0,1},
								{0,1,0,0,0,1,0,0,1,0,0,0,0,1,0},
								{0,0,1,0,0,1,0,0,1,0,0,0,1,0,0},
								{0,1,0,0,0,1,0,0,1,0,0,1,0,1,0},
								{0,0,1,0,0,1,0,0,0,1,1,0,1,0,0},
								{0,0,0,1,1,1,0,0,0,0,0,0,0,0,0} };
		
		int[][] layout20x20 = 
				new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
								{0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
								{0,0,0,0,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0},
								{0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1,0},
								{0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
								{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
								{0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1,0},
								{0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
								{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0},
								{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
								{0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2} };
		
		int[][] layout15x10 = 
				new int[][]{	{0,0,1,1,1,1,1,1,1,0,0,0,1,0,0},
								{0,1,0,0,0,0,0,0,0,1,1,1,0,1,0},
								{0,0,1,0,0,0,8,0,0,0,0,0,0,1,0},
								{0,0,0,1,0,0,0,0,0,0,0,0,0,1,8},
								{0,0,0,0,1,0,0,0,2,0,0,0,0,1,0},
								{0,0,8,8,8,1,0,0,1,0,1,1,1,0,1},
								{1,1,1,1,8,0,1,0,0,1,1,0,0,0,1},
								{1,0,0,1,0,0,1,0,0,0,0,0,0,0,1},
								{1,0,0,0,1,0,1,0,0,0,0,0,0,0,1},
								{0,1,0,0,0,1,0,0,0,0,0,0,0,0,2} };
		
		int[][] layout25x25 = 
				new int[][]{	{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,6,6,0,0},
								{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,8,8,8,8,8},
								{0,0,0,1,0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1},
								{0,0,1,1,1,0},
								{0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
								{0,0,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,2},
								{0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,0,0,0,1,1},
								{0,0,1,0,0,0,0,0,0,1,0,1,1,1,1,0,0,0,0,1,1,1,0},
								{0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,1,1,1,0,0,0,1,0,0,0},
								{0,0,0,0,0,0,0,0,0,1,0,0,1,0},
								{0,0,0,0,6,6,6,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0},
								{0,0,1,1,1,1,1,1,1,1,0,0,2,0,0,0,0,0,1,1},
								{0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0},
								{1,0,0,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},
								{0,0,1,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0} };
	}
	
	@Test
	public void testMapConstruction() {
		Map map1 = new Map(layout15x15, true);
		
	}
	
}
