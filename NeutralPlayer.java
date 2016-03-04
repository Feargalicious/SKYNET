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
	
	public String getName(){
		return playerName;
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
