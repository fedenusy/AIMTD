package test.edu.upenn.eas499.aimtd;

import java.util.ArrayList;

import edu.upenn.eas499.aimtd.Coordinator;
import edu.upenn.eas499.aimtd.Map;
import edu.upenn.eas499.aimtd.Monster;

/**
 * Monte Carlo simulator used to test the efficiency of the various AIMTD monster intelligence levels.
 * @author fedenusy
 *
 */
public class Simulator {
	
	/**
	 * <p>Simulates a number of games using Monsters with the specified intelligence level. After each game,
	 * the simulator outputs the number of lives that the simulated player has left. A lower number of lives means 
	 * the simulator (and hence AIMTD) was more effective. We thus expect that, all else equal, higher Monster
	 * intelligence levels will leave the player with fewer remaining lives.</p>
	 * <p>At the end of the simulation, the Simulator outputs the player's average number of remaining lives and a
	 * number of other summary statistics.</p>
	 * <p>Internally, the simulated player will randomly place towers on empty field tiles; these towers are built as 
	 * soon as the player acquires enough resources to build them. Many of the relevant simulated game values (eg 
	 * map layout, tower damage, monster health and speed, etc) are hard-coded into the Simulator and SimulatedPlayer 
	 * classes for convenience, but can be easily changed.</p>
	 * @param args The simulator takes the following arguments, in order:
	 * [0] Number of simulations to run
	 * [1] Monster intelligence level
	 * 
	 */
	public static void main(String[] args) {
		int numGames = new Integer(args[0]);
		int intLevel = new Integer(args[1]);
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (int i=0; i<numGames; i++) {
			System.out.print("Beginning simulation #" + (i+1) + "... ");
			
			Coordinator coordinator = new Coordinator(_map);
			SimulatedPlayer player = new SimulatedPlayer(coordinator, _map);
			simulateGame(coordinator, player, intLevel);
			
			if (player.hasLives()) results.add(player.getLives());
			else results.add(0);
			
			System.out.println(player.getLives() + " lives left.");
		}
		
		printSummary(results, numGames);
	}
	
	private static void simulateGame(Coordinator coordinator, SimulatedPlayer player, int intLevel) {
		for (int i=0; i<50; i++) {
			Monster monster1 = new MonsterImpl(1, 0, 100, 20, intLevel); // Top left starting point
			Monster monster2 = new MonsterImpl(17, 0, 75, 32, intLevel); // Top right starting point
			coordinator.addMonster(monster1);
			coordinator.addMonster(monster2);
			
			player.tick();
			coordinator.tick();
		}
		
		do {
			player.tick();
			coordinator.tick();
		} while (!coordinator.getActiveMonsters().isEmpty() && player.hasLives());
		player.tick();
	}
	
	private static Map _map = new Map(new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
													{0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},
													{0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0},
													{0,0,1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,0},
													{0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0},
													{0,0,0,0,1,1,1,1,0,0,0,0,1,0,0,0,0,1,0,0},
													{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
													{0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1,0,0},
													{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
													{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
													{0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,0},
													{0,0,0,0,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,0},
													{0,0,0,1,1,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0},
													{0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0},
													{0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0},
													{0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,1,1,1,1,0},
													{0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
													{0,1,0,0,1,0,0,0,0,0,2,0,0,0,0,1,1,0,0,0},
													{0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
													{0,2,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2} });
	
	public static void printSummary(ArrayList<Integer> results, int numGames) {
		System.out.println();
		System.out.println("##### SUMMARY STATISTICS #####");
		System.out.println("Number of simulations: " + numGames);
		
		double avgLives = 0.0;
		for (int lives : results) avgLives += lives;
		avgLives = avgLives / numGames;
		System.out.println("Average lives left: " + avgLives);
		
		int max = -1, min = 101;
		for (int lives : results) {
			if (lives > max) max = lives;
			if (lives < min) min = lives;
		}
		System.out.println("Max lives left: " + max);
		System.out.println("Min lives left: " + min);
		
		double stDev = 0.0;
		for (int lives : results) stDev += Math.pow(lives - avgLives, 2);
		stDev = Math.sqrt(stDev / (results.size()-1));
		System.out.println("Standard deviation: " + stDev);
	}
	
}
