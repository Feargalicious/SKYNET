/*
 * Team Name: Risky_Business
 * Members: Declan Atkins 14388146
 * 			Mark Caulfield: 14475522
 * 			Feargal Kavangah: 14722459
 * 
 * We didnt get to implement the allowance of non-ambiguous shortening
 * of the name of territories
 */

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class UI {

	private static final int FRAME_WIDTH = 1200; //For changing frame dimensions
	private static final int FRAME_HEIGHT = 800;
	
	private JFrame frame = new JFrame();
	private MapPanel mapPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	
	UI (Board board) {
		mapPanel = new MapPanel(board);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Risk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel, BorderLayout.WEST);
		frame.add(infoPanel, BorderLayout.EAST);
		frame.add(commandPanel,BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true);
		return;
	}
	
	public String getCommand () {
		return commandPanel.getCommand();
	}

	public void displayMap () {
		mapPanel.refresh();
		return;
	}

	public void displayString (String string) {
		infoPanel.addText(string);
		return;
	}
	
}
