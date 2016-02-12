import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Game {
	
	private int GoldenCavPos;
	private Player Player1, Player2;
	private List<Integer> assignedTerr;
	public Game(){
		GoldenCavPos=4;
		assignedTerr = new ArrayList<Integer>();
	}

	public void GameStart(){
		String tmpName = "";
		tmpName = JOptionPane.showInputDialog(null, "Enter Player 1 Name:");
		Player1 = new Player(tmpName);
		tmpName = JOptionPane.showInputDialog(null, "Enter Player 2 Name:");
		Player2 = new Player(tmpName);
		
		for(int i=0;i<9;i++){
			Player1 = AssignTerr(Player1);//assign territories to players
			Player2 = AssignTerr(Player2);
		}
		return;
	}
	
	public Player AssignTerr(Player P){
		int ID;
		
		ID = Integer.parseInt(JOptionPane.showInputDialog(null, P.getName() + " pick a territory"));
		while(assignedTerr.contains(ID)){
			ID = Integer.parseInt(JOptionPane.showInputDialog(null, P.getName() + " that has been picked please choose again:"));
		}
		assignedTerr.add(ID);
		P.addTerritory(ID);
		
		return P;
	}
}
