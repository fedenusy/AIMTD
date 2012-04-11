package test.edu.upenn.eas499.aimtd;

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
	 * <p>Simulates a number of games using a Coordinator with the specified intelligence level. After each game,
	 * the simulator outputs the number of lives that the player has left. A lower number of lives means the
	 * simulator (and hence AIMTD) was more effective. We thus expect that, all else equal, higher Coordinator
	 * intelligence levels will leave the player with less remaining lives.</p>
	 * <p>At the end of the simulation, the Simulator outputs the player's average number of remaining lives.</p>
	 * <p>Internally, the simulated player randomly places towers adjacent to at least one road tile; these towers 
	 * are built as soon as the player acquires enough resources. Many of the relevant simulated game values (eg map 
	 * layout, tower damage, monster health and speed, etc) are hard-coded into the Simulator and SimulatedPlayer 
	 * classes for convenience.</p>
	 * @param args The simulator takes the following arguments, in order:
	 * [0] Number of games to simulate.
	 * [1] Coordinator intelligence level
	 * 
	 */
	public static void main(String[] args) {
		int numGames = new Integer(args[0]);
		int intLevel = new Integer(args[1]);
		
		double totalLivesLeft = 0.0;
		for (int i=0; i<numGames; i++) {
			System.out.print("Beginning simulation #" + (i+1) + "... ");
			
			Coordinator coordinator = new Coordinator(_map, intLevel);
			SimulatedPlayer player = new SimulatedPlayer(coordinator, _map);
			simulateGame(coordinator, player);
			
			if (player.hasLives()) totalLivesLeft += player.getLives();
			System.out.println(player.getLives() + " lives left.");
		}
		
		System.out.println("Average lives left: " + totalLivesLeft / numGames);
	}
	
	private static void simulateGame(Coordinator coordinator, SimulatedPlayer player) {
		for (int i=0; i<50; i++) {
			Monster monster1 = new MonsterImpl(1, 0, 100, 20); // Top left starting point
			Monster monster2 = new MonsterImpl(17, 0, 75, 32); // Top right starting point
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
}
