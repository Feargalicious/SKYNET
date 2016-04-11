/*
 * Team Name: Risky_Business
 * Members: Declan Atkins 14388146
 * 			Mark Caulfield: 14475522
 * 			Feargal Kavangah: 14722459
 * 
 * We didnt get to implement the allowance of non-ambiguous shortening
 * of the name of territories
 */
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player  {

	private List<Integer> territories = new ArrayList<Integer>();
	private List<Integer> armiesInTerritory = new ArrayList<Integer>();
	private List<Integer> Cards = new ArrayList<Integer>();
	private int PlayerId;
	private int numArmies;
	private int numTerritories;
	private int numReinforcements;
	private String playerName;
	private Color playerColour;
	private int countInfantryCards;
	private int countCavalryCards;
	private int countArtilleryCards;
	public  Player(String Name, Color Colour){
		playerName = Name;
		playerColour = Colour;
		numArmies = GameData.INIT_UNITS_PLAYER;
		numReinforcements = 0;
		countInfantryCards = 0;
		countCavalryCards = 0;
		countArtilleryCards = 0;
	}
	
	public void removeCard(int ID){
		for(int i=0;i<Cards.size();i++){
			if(Cards.get(i) == ID){
				Cards.remove(i);
			}
		}
	}
	public void removeCardType(int i){
		if(i==0){
			countInfantryCards -= 3;
		}
		else if(i==1){
			countCavalryCards -= 3;
		}
		else {
			countArtilleryCards -= 3;
		}
	}
	
	public boolean testExchangePossible(){
		if(countArtilleryCards >= 3 || countCavalryCards >= 3 || countInfantryCards >= 3){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getCardValue(int i){
		return Cards.get(i);
	}
	public int getNumCards(){
		return Cards.size();
	}
	public  int getNumCavCards(){
		return countCavalryCards;
	}
	public  int getNumInfCards(){
		return countInfantryCards;
	}
	public  int getNumArtCards(){
		return countArtilleryCards;
	}
	
	public void addCard(int ID){
		Cards.add(ID);
		if(GameData.CARD_VALUES[ID] == 0){
			countInfantryCards++;
		}
		else if(GameData.CARD_VALUES[ID] == 1){
			countCavalryCards++;
		}
		else{
			countArtilleryCards++;
		}
	}
	public int removeArmy(int ID, int num){
		int i;
		for(i=0;i<territories.size();i++){
			if(territories.get(i) == ID){
				break;
			}
		}
		
		armiesInTerritory.set(i, (armiesInTerritory.get(i) - num));
		
		if(armiesInTerritory.get(i) <= 0){
			territories.remove(i);
			armiesInTerritory.remove(i);
			return 1;
		}
		
		return 0;
	}
	
	public int getArmies(int ID){
		int i;
		int found = 0;
		for(i=0;i<territories.size();i++){
			if(territories.get(i) == ID){
				found =1;
				break;
			}
		}
		if(found == 1)
			return armiesInTerritory.get(i);
		else
			return 0;
	}
	
	public int getTerritories(){
		return numTerritories;
	}
	
	public void removeTerritory(int ID){
		int i;
		for(i=0;i<territories.size();i++){
			if(territories.get(i) == ID){
				break;
			}
		}
		territories.remove(i);
		numTerritories--;
		return;
	}
	public boolean hasTerritory(int ID){
		return territories.contains(ID);
	}
	public void setId(int id){
		this.PlayerId = id;
	}
	
	public String getName(){
		return playerName;
	}
	
	public int getId(){
		return PlayerId;
	}
	
	public void addUnitsAssignStage(){
		armiesInTerritory.add(1);
	}
	
	public void addTerritory(int id){
		Cards.add(id);
		territories.add(id);
		if(GameData.CARD_VALUES[id] == 0){
			countInfantryCards++;
		}
		else if(GameData.CARD_VALUES[id] == 1){
			countCavalryCards++;
		}
		else{
			countArtilleryCards++;
		}
		numArmies--;//decrease amount of available armies
		numTerritories++;
		return;
	}
	
	public String addArmiesToTerritory(int ID, int numUnits){
		int i;
		if (territories.contains(ID)){
			if (numUnits > numArmies){
				return "Sorry you do not have enough armies available";
			}
			for(i = 0;i<territories.size();i++){
				if (territories.get(i) == ID){
					break;//exit and keep value of position for i
				}
			}
			armiesInTerritory.add(i, armiesInTerritory.get(i) + numUnits);
			return "Added " + numUnits + " to " + GameData.COUNTRY_NAMES[ID];
		}
		else{
			return "That territory is not under your control";
		}
	}
	
	public int getReinforcements(){
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
		
		return numReinforcements;
	}
}
