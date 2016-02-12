import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;

public class Panel extends JPanel{

  private JPanel contentPane;

  public Panel(){
		setBounds(0, 0, Map.Width, Map.Height);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
    
  }

  public void paintComponent(Graphics g){ //Component Paints all graphics
    super.paintComponent(g);
    try 
    {Image map = ImageIO.read(new File("RiskMap.jpg"));
    g.drawImage(map.getScaledInstance(Map.Width-110, Map.Height, Image.SCALE_DEFAULT), 0, 0, null);
    }	//scale -100 to correct frame creation offset
	catch (IOException e)
    {e.printStackTrace();}
    int x_pos=0;
	int y_pos=0;
	for (int i=0;i<constants.NUM_COUNTRIES;i++){
		x_pos=constants.GetCoord(i,0); //Section draws nodes on each
		y_pos=constants.GetCoord(i,1); //Territory based on Co-ords from constants
		Color myNewPurple = new Color (160, 32, 240); //RGB values for colors not
		Color myNewBrown = new Color (101, 67, 33);	  //In imported colors class
		switch(constants.CONTINENT_IDS[i]){
		case 0: g.setColor(Color.YELLOW);
			break;
		case 1: g.setColor(Color.BLUE);
			break;
		case 2: g.setColor(Color.GREEN);
			break;
		case 3: g.setColor(myNewPurple);
			break;
		case 4: g.setColor(Color.ORANGE);
			break;
		case 5: g.setColor(myNewBrown);
			break;
		default: g.setColor(Color.BLACK);
		}
		g.fillOval((((x_pos)*Map.Width)/1000)+20, (((y_pos)*Map.Height)/600), 15, 15);//constants Correcting offsets of object origens
		for(int j=0;j<constants.ADJACENT[i].length;j++){                   //and frame, panel borders
			g.drawLine(((constants.GetCoord(i,0)*1500)/1000)+25,
					((constants.GetCoord(i,1)*1000)/600)+5, 
					((constants.GetCoord(constants.ADJACENT[i][j],0)*1500)/1000)+25,
					((constants.GetCoord(constants.ADJACENT[i][j],1)*1000)/600)+5);
		}
		g.setColor(Color.BLACK);
		g.drawString(constants.COUNTRY_NAMES[i], (((x_pos)*1500)/1000)+20, (((y_pos)*1000)/600)-5); //Print names of countries
		g.drawString(String.valueOf(i), (((x_pos)*1500)/1000), (((y_pos)*1000)/600)+20);	//Print node number(testing)
	}													//Position constants for off-setting strings for visibility
  }
 }


