import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Integer> territories = new ArrayList<Integer>();
	private int numArmies;
	private int numTerritories;
	private int numTerritoriesUnassigned;
	private int numReinforcements;
	private String playerName;
	
	public Player(String Name){
		playerName = Name;
		numArmies = Constants.INIT_UNITS_PLAYER;
		numTerritoriesUnassigned = Constants.INIT_COUNTRIES_PLAYER;
		numReinforcements = 0;
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
	
	public void getReinforcements(){
		numReinforcements = 0;
		if (territories.contains(0) && territories.contains(1) && territories.contains(2) && territories.contains(3)&&territories.contains(4) && territories.contains(5) && territories.contains(6) && territories.contains(7) && territories.contains(8)){
			numReinforcements += 5;
		}
		if (territories.contains(9) && territories.contains(10) && territories.contains(11) && territories.contains(12)&&territories.contains(13) && territories.contains(14) && territories.contains(15)){
			numReinforcements += 5;
		}
		if (territories.contains(16) && territories.contains(17) && territories.contains(18) && territories.contains(19)&&territories.contains(20) && territories.contains(21) && territories.contains(22) && territories.contains(23) && territories.contains(24) && territories.contains(25) && territories.contains(26) && territories.contains(27)){
			numReinforcements += 7;
		}
		if (territories.contains(28) && territories.contains(29) && territories.contains(30) && territories.contains(31)){
			numReinforcements += 2;
		}
		if (territories.contains(32) && territories.contains(33) && territories.contains(34) && territories.contains(35)){
			numReinforcements += 2;
		}
		if (territories.contains(36) && territories.contains(37) && territories.contains(38) && territories.contains(39)&&territories.contains(40) && territories.contains(41)){
			numReinforcements += 3;
		}
		
		numReinforcements += numTerritories/3;
		if (numReinforcements < 3){
			numReinforcements = 3;
		}
		
		return;
	}
}
