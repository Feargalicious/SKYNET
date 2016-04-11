/*
 * Team Name: Risky_Business
 * Members: Declan Atkins 14388146
 * 			Mark Caulfield: 14475522
 * 			Feargal Kavangah: 14722459
 * 
 * We didnt get to implement the allowance of non-ambiguous shortening
 * of the name of territories
 * 
 * 
 * the  test of a supply chain between two territories is not fully working yet
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.Random;

import java.awt.*;

public class Game {
	private  Player Player1, Player2;
	private  NeutralPlayer N1, N2, N3, N4;
	private  List<Integer> assignedTerr;
	private  int diceRollP1;
	private  int diceRollP2;
	private   Player[] PLAYERS = {};
	private   NeutralPlayer[] NEUTRAL_PLAYERS = {};
	private  List<Integer> cardsDrawn;
	public  Board board;
	public  UI ui;//made this public it can be accessed in other functions
	//Player arrays so we can iterate through player and neutral objects
	
	
	public Game(){
		board = new Board();
		ui = new UI(board);
		assignedTerr = new ArrayList<Integer>();
		cardsDrawn = new ArrayList<Integer>();
	}
	
	private NeutralPlayer AssignNeutralTerr(NeutralPlayer P, int pID){
		int count=0;
		for(Integer i=0;i<42;i++){
			if(assignedTerr.contains(i) == false){
				P.addTerritory(i);
				assignedTerr.add(i);
				ui.displayString(P.getName() + " claims " + GameData.COUNTRY_NAMES[i]);
				P.addArmiesToTerritory(i, 1);
				board.addUnits(i, pID, 1);
				count++;
				if (count==6){
					break;
				}
			}
		} 
		return P;
	}
	
	private  int getIDFromString(String value){
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
	
	private void addUnits(Player P, int reinforcements){
		
		int assignedUnits = 0, cID;
		String countryName;
		while(assignedUnits < reinforcements){
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
					if (x+assignedUnits <= reinforcements || x < 0){
						assignedUnits += x;
						String out = P.addArmiesToTerritory(cID, x);
						ui.displayString(out);
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
	
	private Player addTerr(Player P, int cID, int units){
		P.addTerritory(cID);
		P.addArmiesToTerritory(cID, units);
		board.addUnits(cID, P.getId(), units);
		
		return P;
	}
	
	private Player AssignTerr(Player P, int pID, int units){
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
		P.addUnitsAssignStage();
		board.addUnits(ID, pID, units);
		return P;
	}
	
	private void addUnitsNeutral(NeutralPlayer P){
		for(int i=0;i<6;i++){
			P.addArmiesToTerritory(P.territories.get(i), 3);
			board.addUnits(P.territories.get(i), P.getId(), 3);
		}
	}
	
	private void addUnitsStage(){
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
				addUnits(Player1, 3);
				addUnits(Player2, 3);
			}
		}
		else{
			for(i=0;i<9;i++){
				addUnits(Player2, 3);
				addUnits(Player1, 3);
			}
		}
		ui.displayString("Neutrals assigning units...");
		addUnitsNeutral(N1);
		addUnitsNeutral(N2);
		addUnitsNeutral(N3);
		addUnitsNeutral(N4);
		ui.displayMap();
	}
	
	public void gameStart () {	   
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
				Player1 = AssignTerr(Player1, 0, 1);//assign territories to players
				Player2 = AssignTerr(Player2, 1, 1);
			}
		}
		else{
			ui.displayString("Player 2 goes first...");
			for(int i=0;i<9;i++){
				Player2 = AssignTerr(Player2, 1, 1);//assign territories to players
				Player1 = AssignTerr(Player1, 0, 1);
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
	
	private int rollDice(){
		Random rand = new Random();
		return rand.nextInt(6)+ 1;
	}
	
	public void runTurns(){
		int gameWon = 0;
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
			while(gameWon == 0){
				gameWon = turn(Player1, Player2);
				if(gameWon != 0){
					break;
				}
				else{
					gameWon = turn(Player2, Player1);
				}
			}
		}
		else{
			ui.displayString("Player 2 goes first...");
			while(gameWon == 0){
				gameWon = turn(Player2, Player1);
				if(gameWon != 0){
					break;
				}
				else{
					gameWon = turn(Player1, Player2);
				}
			}
		}
	}
	
	private int turn(Player p, Player p2){
		
		ui.displayString(p.getName()+"'s turn");
		
		//add reinforcements
		int reinforcements = p.getReinforcements();
		if(p.testExchangePossible()){
			ui.displayString(p.getNumInfCards() + " infantry cards available");
			ui.displayString(p.getNumCavCards() + " cavalry cards available");
			ui.displayString(p.getNumArtCards() + " artillery cards available");
			ui.displayString("Exchange cards? Enter y/n");
			String input = ui.getCommand();
			while(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
				ui.displayString("what type will you exchange?\n(Enter i for infantry etc)");
				input = ui.getCommand();
				if((input.equalsIgnoreCase("i") || input.equalsIgnoreCase("infantry")) && p.getNumInfCards()>= 3){
					reinforcements += GameData.GoldenCavalry[GameData.GoldenCavPos];
					if(GameData.GoldenCavPos <= 12){
						GameData.GoldenCavPos++;
					}
					for(int j=0;j<3;j++){
						for(int i =0;i<p.getNumCards();i++){
							if(GameData.CARD_VALUES[p.getCardValue(i)] == 0){
								p.removeCard(i);
								break;
							}
						}
						p.removeCardType(0);
					}
				}
				else if((input.equalsIgnoreCase("c") || input.equalsIgnoreCase("Cavalry")) && p.getNumCavCards()>= 3){
					reinforcements += GameData.GoldenCavalry[GameData.GoldenCavPos];
					if(GameData.GoldenCavPos <= 12){
						GameData.GoldenCavPos++;
					}
					for(int i =0;i<p.getNumCards();i++){
						if(GameData.CARD_VALUES[p.getCardValue(i)] == 1){
							p.removeCard(i);
							break;
						}
					}
					p.removeCardType(1);
				}
				else if((input.equalsIgnoreCase("a") || input.equalsIgnoreCase("Artillery")) && p.getNumArtCards()>= 3){
					reinforcements += GameData.GoldenCavalry[GameData.GoldenCavPos];
					if(GameData.GoldenCavPos <= 12){
						GameData.GoldenCavPos++;
					}
					for(int i =0;i<p.getNumCards();i++){
						if(GameData.CARD_VALUES[p.getCardValue(i)] == 2){
							p.removeCard(i);
							break;
						}
					}
					p.removeCardType(2);
				}
				else{
					ui.displayString("Invalid Operation\nretry?");
					input = ui.getCommand();
				}
			}
		}
		ui.displayString( p.getName() + " you have " + reinforcements + " reinforcments available");
		addUnits(p,reinforcements);
		ui.displayMap();
		//attack stage
		ui.displayString("Enter \"Skip\" to skip attack phase");
		String input = ui.getCommand();
		
		while(input.equalsIgnoreCase("Skip") != true){
			String attackFrom, attackTo, numUnits;
			int nUnits, fromID, toID;
			ui.displayString("Where do you want to attack from?");
			attackFrom = ui.getCommand();
			fromID = getIDFromString(attackFrom);
			ui.displayString("Where do you want to attack?");
			attackTo = ui.getCommand();
			toID = getIDFromString(attackTo);
			ui.displayString("How many units will you attack with? (Maximun of 3)");
			numUnits = ui.getCommand();
			try{//to avoid number format exception when a non integer is entered
				nUnits = Integer.parseInt(numUnits);
			}
			catch(NumberFormatException e){
				nUnits = 4;//put it outside the maximum boundaries
			}
			
			if(nUnits > 0 && nUnits < 4 && fromID != -1 && toID !=-1){
				if(testAttack(p, fromID, toID, nUnits)){
					Attack(p, fromID, toID, nUnits);
				}
			}
			
			ui.displayString("Enter \"Skip\" to end attack phase");
			input = ui.getCommand();
			
			
		}
		
		//reinforce stage
		
		int adjacent=0;
		ui.displayString("Type \"Skip\" to skip reinforcement stage");
		input = ui.getCommand();
		while(input.equalsIgnoreCase("skip") != true){
			
			String reFrom, reTo, numUnits;
			int nUnits, fromID, toID;
			ui.displayString("Where do you want to reinforce from?");
			reFrom = ui.getCommand();
			fromID = getIDFromString(reFrom);
			ui.displayString("Where do you want to reinforce?");
			reTo = ui.getCommand();
			toID = getIDFromString(reTo);
			ui.displayString("How many units will you reinforce with?");
			numUnits = ui.getCommand();
			try{//to avoid number format exception when a non integer is entered
				nUnits = Integer.parseInt(numUnits);
			}
			catch(NumberFormatException e){
				nUnits = -1;//put it outside the boundaries
			}
			
			if(p.hasTerritory(toID) && p.hasTerritory(fromID) && nUnits > 0 && (p.getArmies(fromID) - nUnits > 1)){
				if(testAdjacency(fromID,toID,p,fromID) > 0){
					String out=p.addArmiesToTerritory(toID, nUnits);
					ui.displayString(out);
					p.removeArmy(fromID, nUnits);
					board.addUnits(toID, p.getId(), nUnits);
					board.addUnits(fromID, p.getId(), 0-nUnits);
				}
				else{
					ui.displayString("No link can be created");
				}
			}
			else{
				ui.displayString("Invalid operation");
			}
			
			ui.displayString("Type \"Skip\" to skip reinforcement stage");
			input = ui.getCommand();
		}
		
		
		if (p2.getTerritories() < 1){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	private int getOwner(int ID){
		
		if(Player1.hasTerritory(ID)){
			return 1;
		}
		else if(Player2.hasTerritory(ID)){
			return 2;
		}
		else if(N1.hasTerritory(ID)){
			return 3;
		}
		else if(N2.hasTerritory(ID)){
			return 4;
		}
		else if(N3.hasTerritory(ID)){
			return 5;
		}
		else if(N4.hasTerritory(ID)){
			return 6;
		}
		
		return 0;
	}
	
	private int getMaxIndex(int[] a){
		int indMax = 0;
		
		for(int i=0;i<a.length;i++){
			if (a[i] > a[indMax]){
				indMax = i;
			}
		}
		
		return indMax;
	}
	
	
	private int[] calcDiceRoll(int diceP1, int diceP2){
		
		int[] unitsLost = new int[2];
		int[] p1Rolls = new int[diceP1];
		int[] p2Rolls = new int[diceP2];
		int indMax1, indMax2;
		unitsLost[0] = 0;
		unitsLost[1] = 0;
		for(int i = 0; i<diceP1;i++){
			p1Rolls[i] = rollDice();
			ui.displayString("Attacker rolls " + p1Rolls[i]);
		}
		for(int i = 0; i<diceP2;i++){
			p2Rolls[i] = rollDice();
			ui.displayString("Defender rolls " + p2Rolls[i]);
		}
		
		for(int i = 0; i<diceP2;i++){
			indMax1 = getMaxIndex(p1Rolls);
			indMax2 = getMaxIndex(p2Rolls);
			
			if(p1Rolls[indMax1] > p2Rolls[indMax2]){
				unitsLost[1]++;
			}
			else{
				unitsLost[0]++;
			}
			p1Rolls[indMax1] = -1;
			p2Rolls[indMax2] = -1;
		}
		
		return unitsLost;
	}
	
	private void Attack(Player p, int fromID, int toID, int nUnits) {
		
		int owner = getOwner(toID);
		int[] unitsLost = new int[2];
		int defArmies;
		int isTaken = 0;
		switch (owner){
		case 1:{
			if(Player1.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			Player1.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, Player1.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(fromID, p.getId(), -3);
				p.addCard(drawCard());
				
			}
			break;
		}
		case 2:{
			if(Player2.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			isTaken = Player2.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, Player2.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
			}
			break;
		}
		case 3:{
			if(N1.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			isTaken = N1.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, N1.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
			}
			break;
		}
		case 4:{
			if(N2.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			isTaken = N2.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, N2.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
			}
			break;
			
		}
		case 5:{
			if(N3.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			isTaken = N3.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, N3.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
			}
			break;
		}
		case 6:{
			if(N4.getArmies(toID) >= 2){
				defArmies = 2;
			}
			else{
				defArmies = 1;
			}
			
			unitsLost = calcDiceRoll(nUnits, defArmies);
			
			p.removeArmy(fromID, unitsLost[0]);
			board.addUnits(fromID, p.getId(), 0-unitsLost[0]);
			isTaken = N4.removeArmy(toID, unitsLost[1]);
			board.addUnits(toID, N4.getId(), 0-unitsLost[1]);
			
			if(board.testTaken(toID)){
				p.removeArmy(fromID, nUnits-unitsLost[0]);
				board.removeOccupier(toID);
				addTerr(p, p.getId(), nUnits-unitsLost[0]);
				board.addUnits(toID, p.getId(), nUnits-unitsLost[0]);
			}
			break;
			
		}
		
		}
		
		ui.displayMap();
	}

	private boolean testAttack(Player player, int attacking, int attacked, int numUnits){
		boolean adjacent = false; // variable for returning pos/neg output
		if(player.hasTerritory(attacked) == true){
			ui.displayString("You already own that territory");
			return false;
		}
		if(player.hasTerritory(attacking) == false){ //check for controlled territory 
			ui.displayString("You dont control "+GameData.COUNTRY_NAMES[attacking]+" Cannot attack");
			return false;
		}
		
		for(int i=0;i<GameData.ADJACENT[attacked].length;i++){ //check if territories are adjacent
			if(GameData.ADJACENT[attacked][i] == attacking){
				adjacent = true;
			}
		}
		if(adjacent == false){ //return output for wrong attack
			ui.displayString("Territory "+GameData.COUNTRY_NAMES[attacking]+"Is not adjacent to "+GameData.COUNTRY_NAMES[attacked]+"Cannot attack");
			return false;
		}
		
		if(numUnits >= player.getArmies(attacking)){
			ui.displayString("You don't have enough armies");
			return false;
		}
		return true;
		
	}
	
	private int drawCard(){
		Random rand = new Random();
		int j=rand.nextInt(41);
		
		while(cardsDrawn.contains(j)){
			j=rand.nextInt(41);
		}
		
		return j;
	}
	
	public void postGame(){
		if(Player1.getTerritories() == 0){
			ui.displayString(Player2.getName() + " Wins!!!");
		}
		else{
			ui.displayString(Player1.getName() + " Wins!!!");
		}
		
		ui.displayString("Press any key to exit");
		ui.getCommand();
		return;
	}
	
	private int testAdjacency(int startID, int destinationID, Player p, int dontCheck){
		int sum = 0;
		
		if(startID == destinationID){
			return 1;
		}
		
		for(int i = 0; i<GameData.ADJACENT[startID].length;i++){
			if(p.hasTerritory(i) && i != dontCheck){
				sum += testAdjacency(GameData.ADJACENT[startID][i],destinationID,p,startID);
			}
			
		}
		
		
		return sum;
	}
	
}

