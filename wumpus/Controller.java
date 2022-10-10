package wumpus;
import java.awt.event.KeyEvent;
public class Controller {

    World controlledWorld;
    
    public Controller(World w){
	controlledWorld=w;
    }

    public void manage(KeyEvent e){
	switch(e.getKeyCode()){
	    /*	case KeyEvent.VK_DOWN:
	    controlledWorld.movePlayer(Agent.Action);
	    break;*/

	case KeyEvent.VK_UP:
	    controlledWorld.movePlayer(Agent.Action.FOREWARD);
	    break;
	case KeyEvent.VK_LEFT:
	    controlledWorld.movePlayer(Agent.Action.TURN_L);
	    break;
	case KeyEvent.VK_RIGHT:
	    controlledWorld.movePlayer(Agent.Action.TURN_R);
	    break;
	}
	controlledWorld.genPercepts();
    }
}
