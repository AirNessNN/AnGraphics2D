package Window;

import javax.swing.JFrame;

import BaseCanvas.BaseCanvas;

public class BaseWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseWindow() {
		// TODO Auto-generated constructor stub
		setSize(1000,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
	}
	
	
	
	
	public void addCanvas(BaseCanvas comp) {
		if(comp==null) {
			return;
		}
		this.getContentPane().add(comp);
	}
	

}
