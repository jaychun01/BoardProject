//Frame for PostList & Postcontents
package boardsys;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

class BoardFrame extends JFrame {
	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 700;
	private PostContents pc = null;
	private PostList pl =null;
	public BoardFrame() {
		
		pl=new PostList(this);
		
		add(pl);
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		if(CurrentUser.getUserID().equals("manager"))
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	public void change(String panelName) {
		if(panelName.equals("PostList")) {
			getContentPane().removeAll();
            getContentPane().add(pl);
            pl.jTableRefresh();
            revalidate();
            repaint();
		}else if(panelName.equals("PostContents")){
			getContentPane().removeAll();
			pc=new PostContents(this);
            getContentPane().add(pc);
            revalidate();
            repaint();
		}else {
			getContentPane().removeAll();
			pc = new PostContents(this, panelName);
            getContentPane().add(pc);
            revalidate();
            repaint();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BoardFrame();
		
	}

}
