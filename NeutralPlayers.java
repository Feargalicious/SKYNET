import java.util.ArrayList;
import java.util.List;

public class NeutralPlayers {
	
	private List<Integer> territories = new ArrayList<Integer>();
	private int numArmies;
	private int numTerritories;
	private int numTerritoriesUnassigned;
	private String playerName;
	
	public NeutralPlayers(String Name){
		playerName = Name;
		numArmies = constants.INIT_UNITS_NEUTRAL;
		numTerritoriesUnassigned = constants.INIT_COUNTRIES_NEUTRAL;
	}
	
	public String getName(){
		return playerName;
	}
	
	public void addTerritory(int id){
		territories.add(id);
		numTerritories++;
		if (numTerritoriesUnassigned > 0){
			numTerritoriesUnassigned--;
		}
		return;
	}

}
