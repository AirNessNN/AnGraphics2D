import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import AcitonListener.BorderHitListener;
import AcitonListener.UpdateListener;
import BaseCanvas.HorizontalCanvas;
import Element.BaseElement;
import Element.GravityElement;
import Manager.GravityElementManager;
import Window.BaseWindow;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screen = tk.getScreenSize();

	GravityElementManager GEM=null;
	Player player=null;
	GravityElement element=null;
	HorizontalCanvas canvas ;
	
	

	int x = 10, y = 30;
	protected int preX;
	protected int preY;
	boolean up,down,left,right;
	long start,end;
	

	public MainWindow() {
		setSize(1000,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		canvas=new HorizontalCanvas(this,this.getSize());
		canvas.setExceptFPS(60);
		canvas.setLandingCheck(true);
		//File file=new File("E:\\Desktop\\JavaÏîÄ¿\\GraphicsTest\\Graphics\\1.jpg");
		getContentPane().add(canvas);
		canvas.setIsborderHit(new BorderHitListener() {
			
			@Override
			public void onHit(BaseElement element,int model) {
				// TODO Auto-generated method stub
				GravityElement element2=(GravityElement)element;
				element2.y=getHeight()-element2.getHeight();
				element2.setOnFloor(true);
				
				/*element.y=0;
				element.x+=10;*/
				end=System.currentTimeMillis();
				System.out.println(end-start);
			}
		});
		GEM=new GravityElementManager(canvas);
		player=new Player(500, 0, 100, 300,GEM);
		element=new GravityElement(200, 0, 100, 100,GEM);
		GEM.addElement(player);
		GEM.addElement(element);
		player.setGravity(true);
		element.setGravity(true);
		
		canvas.addManager(GEM);
		canvas.startUpdate(null);
		start=System.currentTimeMillis();
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				/*MainWindow mainWindow=new MainWindow();
				mainWindow.canvas.setMouseListener();
				//mainWindow.canvas.removeMouseListener();
				mainWindow.setVisible(true);*/
				//new BaseWindow().setVisible(true);
				new TestWindow().setVisible(true);
			}
		});
	}
}
