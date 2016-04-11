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

public class NeutralPlayer  {

	public List<Integer> territories = new ArrayList<Integer>();
	private List<Integer> armiesInTerritory = new ArrayList<Integer>();
	private int numArmies;
	private int numTerritories;
	private Color playerColour;
	private String playerName;
	private int PlayerId;
	
	public NeutralPlayer(String Name, Color Colour){
		playerName = Name;
		playerColour = Colour;
		numArmies = GameData.INIT_UNITS_NEUTRAL;
	}
	
	public boolean hasTerritory(int ID){
		return territories.contains(ID);
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
		for(i=0;i<territories.size();i++){
			if(territories.get(i) == ID){
				break;
			}
		}
		
		return armiesInTerritory.get(i);
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
	
	public String getName(){
		return playerName;
	}
	

	public void addArmiesToTerritory(int ID, int numUnits){
		int i;
		if (territories.contains(ID)){
			for(i = 0;i<territories.size();i++){
				if (territories.get(i) == ID){
					break;//exit and keep value of position for i
				}
			}
			armiesInTerritory.add(i, armiesInTerritory.get(i) + numUnits);
		}
		
		return;
	}
	public void addUnitsAssignStage(){
		armiesInTerritory.add(1);
	}
	
	public int getId(){
		return PlayerId;
	}
	public void setId(int newId){
		this.PlayerId = newId;
	}
	public void addTerritory(int id){
		territories.add(id);
		armiesInTerritory.add(1);//add an army to the territory
		numArmies--;//decrease amount of available armies
		numTerritories++;
		return;
	}
	
}
