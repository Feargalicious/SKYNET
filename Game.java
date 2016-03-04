/*
 * Team Name: Risky_Business
 * Members: Declan Atkins 14388146
 * 			Mark Caulfield: 14475522
 * 			Feargal Kavangah: 14722459
 * 
 * We didnt get to implement the allowance of non-ambiguous shortening
 * of the name of territories
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.Random;

import java.awt.*;

public class Game {
	private static Player Player1, Player2;
	private static NeutralPlayer N1, N2, N3, N4;
	private static List<Integer> assignedTerr;
	private static int diceRollP1;
	private static int diceRollP2;
	private static  Player[] PLAYERS = {};
	private static  NeutralPlayer[] NEUTRAL_PLAYERS = {};
	private static List<Integer> cardsDrawn;
	public static Board board;
	public static UI ui;//made this public it can be accessed in other functions
	//Player arrays so we can iterate through player and neutral objects
	
	public static NeutralPlayer AssignNeutralTerr(NeutralPlayer P, int pID){
		int count=0;
		for(Integer i=0;i<42;i++){
			if(assignedTerr.contains(i) == false){
				P.addTerritory(i);
				assignedTerr.add(i);
				ui.displayString(P.getName() + " claims " + GameData.COUNTRY_NAMES[i]);
				board.addUnits(i, pID, 1);
				count++;
				if (count==6){
					break;
				}
			}
		} 
		return P;
	}
	
	public static int getIDFromString(String value){
		int i;
		for(i=0;i<42;i++){
			if (GameData.COUNTRY_NAMES[i].equalsIgnoreCase(value)){
				break;
			}
		}
		if (i<42)
			return i;
		else
			return -1;
	}
	
	public static void addUnits(Player P){
		
		int assignedUnits = 0, cID;
		String countryName;
		while(assignedUnits < 3){
			ui.displayString(P.getName() + " where do you want to assign units?");
			countryName = ui.getCommand();
			ui.displayString(">"+countryName);
			cID = getIDFromString(countryName);
			if (cID == -1 || P.hasTerritory(cID) == false){
				ui.displayString("Invalid Country");
			}
			else{
				ui.displayString(P.getName() + " how many units do you wish to assign?");
				String tmp = ui.getCommand();
				ui.displayString(">"+tmp);
				try{
					int x = Integer.parseInt(tmp);
					if (x+assignedUnits <= 3 || x < 0){
						assignedUnits += x;
						board.addUnits(cID, P.getId(), x);
						ui.displayMap();
					}
					else{
						ui.displayString("Invalid amount of units");
					}
				}
				catch (NumberFormatException e){
					ui.displayString("Invalid Integer");
				}
			}
		}
	}
	
	public static Player AssignTerr(Player P, int pID){
		int ID;
		Random rand = new Random();
		ID = rand.nextInt(41);
		while(cardsDrawn.contains(ID) ){
				ID = rand.nextInt(41);
		}
		assignedTerr.add(ID);
		P.addTerritory(ID);
		cardsDrawn.add(ID);
		ui.displayString(P.getName() + " draws " + GameData.COUNTRY_NAMES[ID]);
		board.addUnits(ID, pID, 1);
		return P;
	}
	
	public static void addUnitsNeutral(NeutralPlayer P){
		for(int i=0;i<6;i++){
			board.addUnits(P.territories.get(i), P.getId(), 3);
		}
	}
	
	public static void addUnitsStage(){
		int i, assignedUnits, cID;
		String countryName;
		Random rand = new Random();
		ui.displayString("Rolling Dice...");
		diceRollP1 = rand.nextInt(6)+1;
		diceRollP2 = rand.nextInt(6)+1;
		while(diceRollP1 == diceRollP2){
			diceRollP1 = rand.nextInt(6)+1;
			diceRollP2 = rand.nextInt(6)+1;
		}
		ui.displayString(Player1.getName() + " rolls " + diceRollP1 + "\n" + Player2.getName() + " rolls " + diceRollP2);
		
		if (diceRollP1 > diceRollP2){
			for(i=0;i<9;i++){
				addUnits(Player1);
				addUnits(Player2);
			}
		}
		else{
			for(i=0;i<9;i++){
				addUnits(Player2);
				addUnits(Player1);
			}
		}
		ui.displayString("Neutrals assigning units...");
		addUnitsNeutral(N1);
		addUnitsNeutral(N2);
		addUnitsNeutral(N3);
		addUnitsNeutral(N4);
		ui.displayMap();
	}
	
	public void main (String args[]) {	   
		board = new Board();
		ui = new UI(board);
		assignedTerr = new ArrayList<Integer>();
		cardsDrawn = new ArrayList<Integer>();
		int playerId, countryId;
		String name;
		
		// display blank board
		ui.displayMap();
		
		// get player names
		ui.displayString("Enter the name of player 1");
		name = ui.getCommand();
		ui.displayString("> " + name);
		Player1 = new Player(name, Color.RED);
		Player1.setId(0);
		
		ui.displayString("Enter the name of player 2");
		name = ui.getCommand();
		ui.displayString("> " + name);
		Player2 = new Player(name, Color.BLUE);
		Player2.setId(1);
		
		//Create Neutral Players
		N1 = new NeutralPlayer("Neutral1", MapPanel.PLAYER_COLORS[2]);
		N1.setId(2);
		N2 = new NeutralPlayer("Neutral2", MapPanel.PLAYER_COLORS[3]);
		N2.setId(3);
		N3 = new NeutralPlayer("Neutral3", MapPanel.PLAYER_COLORS[4]);
		N3.setId(4);
		N4 = new NeutralPlayer("Neutral4", MapPanel.PLAYER_COLORS[5]);
		N4.setId(5);
		//Populate arrays for player iteration
		PLAYERS = new Player[2];
		NEUTRAL_PLAYERS = new NeutralPlayer[4];
		PLAYERS[0] = Player1;
		PLAYERS[1] = Player2;
		NEUTRAL_PLAYERS[0] = N1;
		NEUTRAL_PLAYERS[1] = N2;
		NEUTRAL_PLAYERS[2] = N3;
		NEUTRAL_PLAYERS[3] = N4;
		
		Random rand = new Random();
		ui.displayString("Rolling Dice...");
		diceRollP1 = rand.nextInt(6)+1;
		diceRollP2 = rand.nextInt(6)+1;
		while(diceRollP1 == diceRollP2){
			diceRollP1 = rand.nextInt(6)+1;
			diceRollP2 = rand.nextInt(6)+1;
		}
		ui.displayString(Player1.getName() + " rolls " + diceRollP1 + "\n" + Player2.getName() + " rolls " + diceRollP2);
		if (diceRollP1 > diceRollP2){
			ui.displayString("Player 1 goes first...");
			for(int i=0;i<9;i++){
				Player1 = AssignTerr(Player1, 0);//assign territories to players
				Player2 = AssignTerr(Player2, 1);
			}
		}
		else{
			ui.displayString("Player 2 goes first...");
			for(int i=0;i<9;i++){
				Player2 = AssignTerr(Player2, 1);//assign territories to players
				Player1 = AssignTerr(Player1, 0);
			}
		}
				
		N1 = AssignNeutralTerr(N1, 2);
		N2 = AssignNeutralTerr(N2, 3);
		N3 = AssignNeutralTerr(N3, 4);
		N4 = AssignNeutralTerr(N4, 5);
		// display map
		ui.displayMap();
		
		addUnitsStage();
		
		

		return;
	}
}

