import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ActionPacket.BorderHitListener;
import ActionPacket.UpdateListener;
import BaseCanvas.HorizontalCanvas;
import Element.BaseElement;
import Element.GravityElement;
import Manager.GravityElementManager;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screen = tk.getScreenSize();

	GravityElementManager GEM=null;
	Player player=new Player(500, 0, 100, 300);
	GravityElement element=new GravityElement(200, 0, 100, 100);
	HorizontalCanvas canvas ;
	
	

	int x = 10, y = 30;
	protected int preX;
	protected int preY;
	boolean up,down,left,right;
	

	public MainWindow() {
		// TODO Auto-generated constructor stub
		setSize(1000,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel jp=new JPanel();
		setContentPane(jp);
		setUndecorated(true);  
		canvas=new HorizontalCanvas(this.getSize());
		canvas.setExceptFPS(60);
		canvas.setLandingCheck(true);
		//File file=new File("E:\\Desktop\\JavaÏîÄ¿\\GraphicsTest\\Graphics\\1.jpg");
		jp.add(canvas);
		canvas.setIsborderHit(new BorderHitListener() {
			
			@Override
			public void onHit(BaseElement element,int model) {
				// TODO Auto-generated method stub
				/*GravityElement element2=(GravityElement)element;
				element2.y=getHeight()-element2.getHeight();*/
				//element2.setOnFloor(true);
				
				/*element.y=0;
				element.x+=10;*/
			}
		});
		GEM=new GravityElementManager();
		GEM.addElement(player);
		GEM.addElement(element);
		player.setGravity(true);
		element.setGravity(true);
		
		canvas.addManager(GEM);
		canvas.startUpdate(new UpdateListener() {
			
			@Override
			public void updatePerformed() {
				// TODO Auto-generated method stub
				//GEM.update();
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}
}
