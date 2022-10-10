package wumpus;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

class WumpusView extends JPanel{

    private int boxSize;
    private int worldSize;
    private World world;

    WumpusView(World w){
	super();
	
	world=w;
	worldSize=w.SIZE;

	boxSize=50;
	
    }
    
    public void paint(Graphics g){
	for(int i=0;i<worldSize;i++){
	    for(int j=0;j<worldSize;j++){
		int val=world.agent.percepts[i][j];
		if((val&World.CELL_PIT)!=0){
		    g.setColor(Color.BLUE);
		    g.fillRect(i*boxSize,j*boxSize,boxSize,boxSize);
		}
		if((val&World.CELL_WUMPUS)!=0){
		    g.setColor(Color.RED);
		    g.fillOval(i*boxSize,j*boxSize,boxSize,boxSize);
		}
		if((val&World.CELL_GOLD)!=0){
		    g.setColor(Color.YELLOW);
		    g.fillOval(i*boxSize,j*boxSize,boxSize,boxSize);
		}
	    }
	    }
	
	for(int i=0;i<worldSize;i++){
	    for(int j=0;j<worldSize;j++){
		int val=world.agent.percepts[i][j];
		if((val&World.CELL_BREEZE)!=0){
		    g.setColor(Color.BLUE);
		    g.fillOval(i*boxSize,j*boxSize,boxSize/3,boxSize/3);
		}
		if((val&World.CELL_SMELL)!=0){
		    g.setColor(Color.RED);
		    g.fillOval(i*boxSize+boxSize/3,j*boxSize,boxSize/3,boxSize/3);
		}
	    }
	}
	

	int x=world.agent.posX;int []polyX=new int [3];
	int y=world.agent.posY;int []polyY=new int [3];
	int box13=boxSize/3;
	switch(world.agent.orientation){
	case N:
	    polyX[0]=x*boxSize+box13;polyX[1]=x*boxSize+(boxSize/2);polyX[2]=x*boxSize+(2*box13);
	    polyY[0]=y*boxSize+(2*box13);polyY[1]=y*boxSize+box13;polyY[2]=y*boxSize+(2*box13);	    
	    break;
	case E:
	    polyX[0]=x*boxSize+box13;polyX[1]=x*boxSize+(box13*2);polyX[2]=x*boxSize+(box13);
	    polyY[0]=y*boxSize+(box13);polyY[1]=y*boxSize+(boxSize/2);polyY[2]=y*boxSize+(2*box13);	    
	    break;
	case S:
 	    polyX[0]=x*boxSize+box13;polyX[1]=x*boxSize+(box13*2);polyX[2]=x*boxSize+(boxSize/2);
	    polyY[0]=y*boxSize+(box13);polyY[1]=y*boxSize+(box13);polyY[2]=y*boxSize+(box13*2);	    
	    break;
	case W:
	    polyX[0]=x*boxSize+box13;polyX[1]=x*boxSize+(2*box13);polyX[2]=x*boxSize+(2*box13);
	    polyY[0]=y*boxSize+(boxSize/2);polyY[1]=y*boxSize+(box13);polyY[2]=y*boxSize+(box13*2);	 
	    break;
	}
	g.setColor(Color.BLACK);
	g.fillPolygon(polyX,polyY,3);
	for(int i=0;i<=worldSize;i++){
	    g.drawLine(i*boxSize,0,i*boxSize,worldSize*boxSize);
	    g.drawLine(0,i*boxSize,worldSize*boxSize,i*boxSize);
	}
	for(int i=0;i<worldSize;i++){
	    for(int j=0;j<worldSize;j++){
		g.drawString(Double.toString(world.getProb(i,j)),i*boxSize,(j+1)*boxSize);	
	    }
	}
	
    }
}

public class Viewer extends JFrame implements KeyListener{
    
    private WumpusView wumpusView;
    private CommandsBar commandsBar;
    private Controller control;

    public Viewer(World w,Controller c){
	super("TP-IAD : Bayes");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	
	CommandsBar commandsBar=new CommandsBar(w,this);
	this.setJMenuBar(commandsBar);
	control=c;
	wumpusView=new WumpusView(w);
	this.addKeyListener(this);
	super.setSize(new Dimension(222,272));
	this.add(wumpusView);

	this.setVisible(true);
    }
    public void keyPressed(KeyEvent e){
	control.manage(e);
	this.repaint();
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e) {
	control.manage(e);
	this.repaint();
    }    

}
