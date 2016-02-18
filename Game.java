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
		Integer ID = null, check = 0;

		while(check == 0)
		{
			ID = Integer.parseInt(JOptionPane.showInputDialog(null, P.getName() + " pick a territory"));

			if(ID <= 41 && ID >=0 && ID != null)
			{

				while(assignedTerr.contains(ID))
				{
					ID = Integer.parseInt(JOptionPane.showInputDialog(null, P.getName() + " that has been picked please choose again:"));
				}
				JOptionPane.showMessageDialog(null, P.getName() + " added: " + constants.COUNTRY_NAMES[ID]);
				assignedTerr.add(ID);
				P.addTerritory(ID);
				check = 1;

			}else
			{
				JOptionPane.showMessageDialog(null, P.getName() + " that is out of range (0 - 41). T1ry again: ");
				check = 0;
			}
		}

		return P;
	}

	public NeutralPlayers AssignNeutralTerr(NeutralPlayers P){
		int count=0;
		for(Integer i=0;i<42;i++){
			if(assignedTerr.contains(i) == false){
				P.addTerritory(i);
				assignedTerr.add(i);
				count++;
				if (count==5){
					break;
				}
			}
		} 
		return P;
	}
}
