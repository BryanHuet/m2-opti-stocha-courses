package wumpus;


public class Main{

    public static void main(String [] args){

	World w=new World();
	Controller c=new Controller(w);
	Viewer v=new Viewer(w,c);
	
    }
    
}
