import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Test extends JPanel implements ActionListener,KeyListener{
	Timer tm= new Timer(15,this);
	int x=0,y=455,Vx=0,Vy=0;
	static int highScore=0;
	ImageIcon background = new ImageIcon("bg.jpg");
	ImageIcon player = new ImageIcon("p3.gif");
	ImageIcon gOff = new ImageIcon("obs.gif");
	ImageIcon gOver = new ImageIcon("bg2.jpg");
	
	String level = "start";
	int score = 0;
        
	ArrayList<Rectangle> list;
	public Test()
	{
            tm.start();
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);

            list = new ArrayList<Rectangle>();
            list.add(new Rectangle(700, 455, 25, 50));
            list.add(new Rectangle(1200,455, 25 , 50));
            // list.add(new Rectangle(1400,435, 25, 50));
           BufferedReader br = null;
           try {                
                br  = new BufferedReader(new FileReader("highscore.txt"));
                highScore = Integer.parseInt(br.readLine());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                }
            }
	}
	
	public void restart(){
            list.clear();
            x=0;
            y=455;
            list.add(new Rectangle(700, 455, 25, 50));
            list.add(new Rectangle(1200,455, 25 , 50));
        }
	
	//  DRAW ALL THE THINGS
	public void paintComponent(Graphics ge)
	{
		super.paintComponent(ge);
		Graphics g = (Graphics2D)ge;
                
                if(level.equals("start")){
                    background.paintIcon(this, g, 0, 0);
                    g.setFont(new Font("Berlin Sans MS", 1, 50));
                    g.drawString("Start", 360, 300);
                    g.setFont(new Font("Berlin Sans MS", 1, 24));
                    g.drawString("Press Enter to Start", 310, 340);
                }
		if(level.equals("play")) {
                    background.paintIcon(this, g, 0, 0);

                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Comic Sans MS", 1, 20));

                    //if(gameover == false) 
                            player.paintIcon(this, g, x, y-40);

                    g.drawString("SCORE: "+score/10, 700 , 50);
                    
                    
                    g.setFont(new Font("Berlin Sans MS", 1, 50));
                    
		
		//OBSTACLE
		
                    for(Rectangle r: list){

                            gOff.paintIcon(this, g, r.x, r.y-30);
                    }
		
                }
                
                else if(level.equals("gameover")){
                    g.drawString("SCORE: "+score/10, 700 , 50);


                    
                    gOver.paintIcon(this, g, 0, 0);
                    g.setFont(new Font("Berlin Sans MS", 1, 50));
                    g.drawString("HIGH SCORE: "+highScore/10, 200, 250);
                    g.setFont(new Font("Berlin Sans MS", 1, 50));
                    g.drawString("SCORE: "+score/10 , 280, 320);
                    g.setFont(new Font("Berlin Sans MS", 1, 24));
                    g.drawString("Press R to Restart", 310, 360);
                    if(score>highScore){
                        BufferedWriter bw = null;
                        try {
                            highScore=score;
                            bw = new BufferedWriter(new FileWriter("highscore.txt"));
                            bw.write(highScore+"");
                            //System.out.println(highScore/10);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        } finally {
                            try {
                                bw.close();
                            } catch (IOException ex) {
                                 System.out.println(ex.getMessage());
                            }
                        }
                    }	
		
		
                }

	}

	
	
	// WHEN OBJECT IS MOVING
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int c= e.getKeyCode();
		
		if(c == KeyEvent.VK_R || (level.equals("start") && c == KeyEvent.VK_ENTER)){
                    level = "play";
                    score = 0;
                    restart();
                }
                
		
		if(c == KeyEvent.VK_SPACE || c == KeyEvent.VK_T)
		{
			
			//Vx=3;
			
			if(y == 455) Vy -= 20;

		}

	}

	
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	
	
	//  POSITION CHANGER 
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		//Gravity
		Vy+=1;
		if(Vy > 10) Vy = 10;
		
		x = x+Vx;
		y = y+Vy;
		
		if(x<0)
		{
			x=0;
			Vx=0;
		}
		
		
		
		
		if(y>455)
		{
			y=455;
			Vy=0;
			

			
		}
	
		if(level.equals("play")){
                    score += 1;
                    for(Rectangle r: list){
                            if(r.x < 0){
                                    r.x = (int) (800 + (Math.random() * 100));
                            }
                            r.x -= 5;
                            if(collision(r, new Rectangle(x + 50 , y, player.getIconWidth() - 60     , player.getIconHeight() - 50 ))){


                                    level = "gameover";
                                    //tm.stop(); 
                                    //System.exit(0);
                            }
                    }
                }
		
		
			
		
		repaint();
	}
	
	public boolean collision(Rectangle r1, Rectangle r2){
		if(r1.intersects(r2)){
			return true;
		}
		else{
			return false;
		}
	}

	
}
