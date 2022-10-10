package wumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class CommandsBar extends JMenuBar implements ActionListener{

    World world;
    JFrame mainView;

    public CommandsBar(World w,JFrame mainView){
	super();
	
	world=w;
	this.mainView=mainView;
	JMenu menu=new JMenu("Commands");
	this.add(menu);
	
	JMenuItem item=new JMenuItem("Create new wumpus");
	menu.add(item);
	item.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
	world.genNewWumpus();
	mainView.repaint();
    }
    
}
