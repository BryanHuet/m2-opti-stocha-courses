package wumpus;
import java.util.* ;


public class Agent{



    public enum Action{
	FOREWARD,TURN_L,TURN_R,SHOOT
    };

    public enum Orientation{
	N,E,S,W
    };

    public int [][] percepts;
    public boolean [][] known;
    public double [][]probaPit;

    int posX,posY;
    Orientation orientation;


    public Agent(){
	percepts=new int [World.SIZE][World.SIZE];
	probaPit=new double[World.SIZE][World.SIZE];
	known=new boolean[World.SIZE][World.SIZE];
	resetPercepts();
	resetProba();
	computeProba();
    }

    public void resetPercepts(){
	for(int i=0;i<World.SIZE;i++){
	    for(int j=0;j<World.SIZE;j++){
		percepts[i][j]=0;
		known[i][j]=false;
	    }
	}
    }

    public void resetProba(){
	for(int i=0;i<World.SIZE;i++){
	    for(int j=0;j<World.SIZE;j++){
		probaPit[i][j]=World.PROBA_PIT;
	    }
	}
    }
    public void setPosition(int x, int y,Orientation o){
	posX=0;posY=0;orientation=o;
    }

    public Action act(){
	return Action.FOREWARD;
    }


    public void addElementBordure(ArrayList<int[]> bordure, int x, int y){
        ArrayList<int[]> temp = new ArrayList<>();
        if(x-1>=0 && ! known[x-1][y]) temp.add(new int[] {x-1,y});
        if(x+1<World.SIZE && ! known[x+1][y]) temp.add(new int[] {x+1,y});
        if(y-1>=0 && known[x][y-1]) temp.add(new int[] {x,y-1});
        if(y+1<World.SIZE && known[x][y+1]) temp.add(new int[] {x,y+1});

        for(int[] element: temp){
            boolean alreadyIn = false;
            for (int[] bordureElement: bordure){
                if (bordureElement[0] == element[0] &&
                        bordureElement[1] == element[1]) {
                    alreadyIn = true;
                    break;
                }
            }
            if (! alreadyIn) bordure.add(element);
        }


    }



    public void computeProba(){


        ArrayList<int[]> bordure = new ArrayList<int[]>();

        for(int x=0; x<World.SIZE; x++) {
            for(int y=0; y<World.SIZE; y++) {


                if ((percepts[x][y]&(World.CELL_BREEZE|World.CELL_SMELL)) !=0)  addElementBordure(bordure, x,y);

                else if (known[x][y]) {
                    if (y - 1 >= 0) probaPit[x][y - 1] = 0;
                    if (x + 1 < World.SIZE) probaPit[x + 1][y] = 0;
                    if (y + 1 < World.SIZE) probaPit[x][y + 1] = 0;
                    if (x - 1 >= 0) probaPit[x - 1][y] = 0;
                }

                if (known[x][y]) probaPit[x][y] = 0;
            }
        }

        System.out.println("----------");
        for (int[] element: bordure){
            System.out.println("("+element[0] +" "+element[1] + ") ");
        }
/*
        System.out.println("#### Perception ####");
        for(int i=0; i<World.SIZE;i++){
            for(int j=0;j<World.SIZE;j++){
                System.out.print(percepts[j][i]+" ");
            }
            System.out.println();
        }
        System.out.println("#### Known  ####");
        for(int i=0; i<World.SIZE;i++){
            for(int j=0;j<World.SIZE;j++){
                System.out.print(known[j][i]+" ");
            }
            System.out.println();
        }
        */

    }

    private double arrondir(double value, int n) {
	double r = (Math.round(value * Math.pow(10, n))) / (Math.pow(10, n));
	return r;
    }

    public boolean existCell(int i, int j){
	return (i>=0 && j>=0 && i<World.SIZE && j<World.SIZE);
    }
}
