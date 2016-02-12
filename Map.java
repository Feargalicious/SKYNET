import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;

public class Map extends JFrame {

	private JPanel contentPane;
	public static int optionInt;
	public static int Width = 1500; //Use these to change dimensions of map
	public static int Height = 1000;//And painted objects

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Map frame = new Map();
					Panel content = new Panel();
					frame.getContentPane().add(content); //Using frame to import content pane
					frame.setVisible(true);				 //From Panel.java
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	public Map() {
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension d = tk.getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, d.width, d.height); //Sizes based off option
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
	}
	
	
}


