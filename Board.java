/*
 * Team Name: Risky_Business
 * Members: Declan Atkins 14388146
 * 			Mark Caulfield: 14475522
 * 			Feargal Kavangah: 14722459
 * 
 * We didnt get to implement the allowance of non-ambiguous shortening
 * of the name of territories
 */
public class Board {
	
	private boolean[] occupied = new boolean [GameData.NUM_COUNTRIES];
	private int[] occupier = new int [GameData.NUM_COUNTRIES];
	private int[] numUnits = new int [GameData.NUM_COUNTRIES];
	
	Board() {
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			occupied[i] = false ;
			numUnits[i] = 0;
		}
		return;
	}
	
	public void addUnits (int country, int playerId, int addNumUnits) {	
		// prerequisite: country must be unoccupied or already occupied by this player
		if (!occupied[country]) {
			occupied[country] = true;
			occupier[country] = playerId;
		}
		numUnits[country] = numUnits[country] + addNumUnits;
		return;
	}
	
	public boolean isOccupied(int country) {
		return occupied[country];
	}
	
	public int getOccupier (int country) {
		return occupier[country];
	}
	
	public int getNumUnits (int country) {
		return numUnits[country];
	}

}
