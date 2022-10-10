package wumpus;

//import java.util.Hashtable;
//import java.util.ArrayList;

public class World{
    public static final int SIZE =4;

    public static final int CELL_EMPTY =1;
    public static final int CELL_BREEZE =2;
    public static final int CELL_SMELL =4;
    public static final int CELL_PIT = 8;
    public static final int CELL_WUMPUS= 16;
    public static final int CELL_GOLD=32;
    
    public static final int CELL_PLAYER=64;

    public static final double PROBA_PIT=0.2;

    private int [][] world;
    public Agent agent;

    public World(){
	world=new int [SIZE][SIZE];
	
	agent=new Agent();
	genPercepts();
	genNewWumpus();
	agent.computeProba();
	
    }


    public int get(int i, int j){
	return world[i][j];
    }

    public double getProb(int i, int j){
	return agent.probaPit[i][j];
    }

    public void genNewWumpus(){
	for(int i=0;i<SIZE;i++){
	    for(int j=0;j<SIZE;j++)
		if(Math.random()<PROBA_PIT){
		    if(i!=0 && j!=0)world[i][j]=CELL_PIT;
		}
		else{world[i][j]=CELL_EMPTY;}
	}
	int x,y;
	/*PLACEMENT DU WUMPUS*/
	do{
	    x= (int)Math.floor(Math.random()*SIZE);
	    y = (int)Math.floor(Math.random()*SIZE);
	}while((x==0 && y==0) || world[x][y]!=CELL_EMPTY);
	world[x][y]=CELL_WUMPUS;

	/*PLACEMENT DE L'OR*/
	do{
	    x= (int)Math.floor(Math.random()*SIZE);
	    y = (int)Math.floor(Math.random()*SIZE);
	}while((x==0 && y==0) || world[x][y]!=CELL_EMPTY);
	world[x][y]=CELL_GOLD;
	agent.resetPercepts();
	agent.resetProba();
	agent.setPosition(0,0,Agent.Orientation.N); 
	genPercepts();
	agent.computeProba();
    }
    
    public void movePlayer(Agent.Action a){

	switch(a){
	case FOREWARD:
	    oneStep();
	    break;
	case TURN_L:
	    turnL();
	    break;
	case TURN_R:
	    turnR();
	    break;
	case SHOOT:
	    //	    shoot();
	    break;
	}
	agent.computeProba();
    }
    
    public void turnL(){
	switch(agent.orientation){
	case N:
	    agent.orientation=Agent.Orientation.W;
	    break;
	case S:  
	    agent.orientation=Agent.Orientation.E;
	    break;
	case E:
	    agent.orientation=Agent.Orientation.N;
	    break;
	case W:
	    agent.orientation=Agent.Orientation.S;
	    break;
	}
    }

    public void turnR(){
	switch(agent.orientation){
	case N:
	    agent.orientation=Agent.Orientation.E;
	    break;
	case S:  
	    agent.orientation=Agent.Orientation.W;
	    break;
	case E:
	    agent.orientation=Agent.Orientation.S;
	    break;
	case W:
	    agent.orientation=Agent.Orientation.N;
	    break;
	}
    }
    public void oneStep(){
	switch(agent.orientation){
	case N:
	    if(agent.posY>0)agent.posY--;
	    break;
	case S:  
	    if(agent.posY<World.SIZE-1)agent.posY++;
	    break;
	case E:
	    if(agent.posX<World.SIZE-1)agent.posX++;
	    break;
	case W:
	    if(agent.posX>0)agent.posX--;
	    break;
	}
    }
    

    public void genPercepts(){
	int x=agent.posX;
	int y=agent.posY;
	agent.percepts[x][y]=world[x][y];
	agent.known[x][y]=true;
	if(y-1>=0){
	    if((world[x][y-1]&CELL_PIT) !=0)agent.percepts[x][y]|=CELL_BREEZE;
	    if((world[x][y-1]&CELL_WUMPUS) !=0)agent.percepts[x][y]|=CELL_SMELL;
	}
	if(x+1<SIZE){
	    if((world[x+1][y]&CELL_PIT) !=0)agent.percepts[x][y]|=CELL_BREEZE;
	    if((world[x+1][y]&CELL_WUMPUS) !=0)agent.percepts[x][y]|=CELL_SMELL;
	}
	if(y+1<SIZE){
	    if((world[x][y+1]&CELL_PIT) !=0)agent.percepts[x][y]|=CELL_BREEZE;
	    if((world[x][y+1]&CELL_WUMPUS) !=0)agent.percepts[x][y]|=CELL_SMELL;
	}
	if(x-1>=0){
	    if((world[x-1][y]&CELL_PIT) !=0)agent.percepts[x][y]|=CELL_BREEZE;
	    if((world[x-1][y]&CELL_WUMPUS) !=0)agent.percepts[x][y]|=CELL_SMELL;
	}
	agent.computeProba();
    }
}
    

