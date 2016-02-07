import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Integer> territories = new ArrayList<Integer>();
	private int numArmies;
	private int numTerritories;
	private int numReinforcements;
	private String playerName;
	
	public Player(String Name){
		playerName = Name;
		numArmies = Constants.INIT_UNITS_PLAYER;
		numTerritories = Constants.INIT_COUNTRIES_PLAYER;
		numReinforcements = 0;
	}
}
