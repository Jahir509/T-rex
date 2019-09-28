 import java.awt.Color;
import java.awt.Image;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String[]args)
	{
		
		Test t = new Test();
		JFrame f= new JFrame("Cat Chase");
		
		
		f.setBackground(Color.WHITE);
		f.setSize(850,550);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(t);
		f.setLocationRelativeTo(null);
		f.add(t);  
		
	}
	
}
