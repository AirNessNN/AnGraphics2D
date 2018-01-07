package BaseCanvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import AcitonListener.BorderHitListener;
import AcitonListener.ElementHitListener;
import AcitonListener.MouseActionListener;
import AcitonListener.UpdateListener;
import Element.BaseElement;
import Enum.BorderHitlState;
import Enum.MouseState;
import HitCheck.QuadTreeManager;
import Manager.BaseElementManager;

public class BaseCanvas extends Canvas{
	
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	/*
	 * ��Ա
	 */
	private ArrayList<BaseElementManager>			elementManagers;//Ԫ�ع�����
	private ArrayList<BaseElement>							wall;//���ڱ���ǽ��Ԫ��
	//����
	private JFrame														context;
	private BufferedImage 										backgroundImage;//����
	private Color 														defaultColor;//������ɫ
	private Dimension 												backgroundSize;//������С
	private Dimension												canvasSize;//�ؼ���С
	private double													unit=1f;
	private ArrayList<BaseElement>							totalElement;//������Ⱦ�ܺ͵�Ԫ��
	//�������
	private boolean 													isEnable;
	private boolean 													isRunning;
	//FPS���
	private long 														exceptFPS=60;//Ԥ��FPS
	private float 														fps=0;//��ǰFPS
	private volatile long 											startTime=0;
	private volatile long 											endTime=0;
	private long 														sleepTime=0;
	private Object														timeLocker;
	
	//��ײ���
	private boolean													isborderHit;//�Ƿ�򿪱߽���ײ���
	private boolean													isElementHit;//�Ƿ��Ԫ����ײ���
	//������
	private Point														cameraLocation;//���λ��
	private boolean													isSmoothMove;//���ƽ���ƶ�����
	private BaseElement											cameraLockObject;//��������Ķ���
	//������
	private boolean													isMouseListening=false;
	
	private int															mouseKey;
	private int															isMousePress=-1;
	
	private int															lastMouseState=-1;
	public static final int											MOUSE_LEFT_BUTTON=1;
	public static final int 											MOUSE_WHEEL=2;
	public static final int											MOUSE_RIGHT_BUTTON=3;
	private int 															wheelMoveValue;
	private boolean													mouseVisable=true;
	
	//�����¼�
	public MouseActionListener									mouseActionListener;
	public BorderHitListener 										borderhitListener;
	public ElementHitListener									elementHitListener;
	public ElementControl											elementControl;
	
	
	private QuadTreeManager									quadTreeManager;//�Ĳ���������
	
	
	public interface ElementControl{
		public void addElement(BaseElement element);
		public void removeElement(BaseElement element);
	}
	
	
	
	
	
	
	
	
	//���ڵ��������¼�
	private MouseListener mouseListener=new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseKey=e.getButton();
			lastMouseState=isMousePress;
			isMousePress=0;
			if(mouseActionListener!=null) {
				mouseActionListener.mouseAction(mouseKey, MouseState.MouseReleased);
			}
			getClickElement(e, MouseState.MouseReleased);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseKey=e.getButton();
			lastMouseState=isMousePress;
			isMousePress=1;
			if(mouseActionListener!=null) {
				mouseActionListener.mouseAction(mouseKey, MouseState.MousePress);
			}
			getClickElement(e, MouseState.MousePress);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	};
	
	//���̼���
	private KeyListener keyListener=new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	};






	
	
	
	
	/*
	 * ����
	 */
	public boolean isElementHit() {
		return isElementHit;
	}
	public void setElementHit(boolean isElementHit) {
		this.isElementHit = isElementHit;
	}
	
	
	
	
	
	public ArrayList<BaseElement> getTotalElements(){
		return totalElement;
	}
	protected void setTotalElements() {
		
	}
	
	
	
	
	
	public int getIsMousePress() {
		return isMousePress;
	}
	
	
	
	
	public boolean isMouseListening() {
		return isMouseListening;
	}
	public void setMouseListening(boolean isMouseListening) {
		this.isMouseListening = isMouseListening;
	}
	
	
	
	
	public ArrayList<BaseElement> getWall() {
		return wall;
	}
	public void setWall(ArrayList<BaseElement> wall) {
		if(wall==null)
			return;
		this.wall = wall;
	}
	
	
	
	
	public Dimension getCanvasSize() {
		return canvasSize;
	}
	public void setCanvasSize(Dimension canvasSize) {
		if(canvasSize==null)
			return;
		this.canvasSize = canvasSize;
	}
	
	
	
	
	public boolean isIsborderHit() {
		return isborderHit;
	}
	public void setIsborderHit(BorderHitListener I) {
		if(I!=null) {
			isborderHit=true;
			borderhitListener=I;
		}else {
			isborderHit=false;
		}
	}
	
	
	
	
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(BufferedImage backgroundImage) {
		if(backgroundImage==null)
			return;
		if(backgroundImage.getWidth()<this.getWidth()||backgroundImage.getHeight()<this.getHeight()) {
			this.backgroundImage=new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g=this.backgroundImage.getGraphics();
			g.setColor(defaultColor);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(backgroundImage, 0, 0, null);
		}else {
			this.backgroundImage = backgroundImage;
		}
	}


	
	
	public Color getDefaultColor() {
		return defaultColor;
	}
	public void setDefaultColor(Color defaultColor) {
		if(defaultColor==null)
			return;
		this.defaultColor = defaultColor;
	}
	


	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}




	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}




	public long getExceptFPS() {
		return exceptFPS;
	}
	public void setExceptFPS(long exceptFPS) {
		if(exceptFPS<=0) {
			return;
		}
		this.exceptFPS = exceptFPS;
		sleepTime=1000/exceptFPS;
	}
	
	
	
	
	
	public double getFPS() {
		return fps;
	}




	public Dimension getBackgroundSize() {
		return backgroundSize;
	}




	public ArrayList<BaseElementManager> getElementManagers() {
		return elementManagers;
	}
	
	
	
	
	public Point getCameraLocation() {
		return cameraLocation;
	}
	public void setCameraLocation(Point cameraLocation) {
		this.cameraLocation = cameraLocation;
	}
	
	
	
	
	
	public boolean isSmoothMove() {
		return isSmoothMove;
	}
	public void setSmoothMove(boolean isSmoothMove) {
		this.isSmoothMove = isSmoothMove;
	}
	
	
	
	//�ù�������ȡ����ǰ��ˢ��ʱ��
	private void setStartTime(long time) {
		synchronized (timeLocker) {
			startTime=time;
		}
	}
	
	
	
	
	private void setEndTime(long time) {
		synchronized (timeLocker) {
			//System.out.println("endtime:"+endTime);
			endTime=time;
		}
	}
	public long getProcessTime() {
		synchronized (timeLocker) {
			long time=System.currentTimeMillis();
			return time-startTime;
		}
	}
	
	
	
	
	public double getUnit() {
		return unit;
	}
	public void setUnit(double unit) {
		this.unit = unit;
	}
	
	
	
	
	/*
	 * ˽�з���
	 */
	
	
	
	private void initialize() {
		elementManagers=new ArrayList<>();
		wall=new ArrayList<>();
		isEnable=true;
		exceptFPS=60;
		sleepTime=16;
		isRunning=false;
		timeLocker=new Object();
		defaultColor=Color.LIGHT_GRAY;
		totalElement=new ArrayList<>();
		quadTreeManager=new QuadTreeManager(getBounds(), totalElement);
		
		/*
		 * Ԫ�ؿ��ƽӿ�����Ϊ������ļ��������ṩͼ��֧�֣�Ԫ�صĵ���¼��ͻ�������˳���
		 * �����Canvas����һ��Arraylist�й�����BaseElement��ά��һ����С��������飬
		 * ��Ԫ�صĻ��ƺͽ������𵽺ܴ������
		 */
		elementControl=new ElementControl() {

			@Override
			public void addElement(BaseElement element) {
				// TODO Auto-generated method stub
				if(totalElement!=null) {
					totalElement.add(element);
					Collections.sort(totalElement);
				}
			}

			@Override
			public void removeElement(BaseElement element) {
				// TODO Auto-generated method stub
				if(totalElement!=null) {
					totalElement.remove(element);
					Collections.sort(totalElement);
				}
			}
			
		};
	}
	
	//ÿ������������¼���Ԫ�ر���
	private void getClickElement(MouseEvent e,MouseState state) {
		boolean findElement=false;
		for(BaseElement element:totalElement) {
			if(element.isInElement(e.getX(), e.getY())) {
				if(element.actionListener!=null) {
					element.actionListener.mouseAction(e.getButton(), state);
				}
				if(state==MouseState.MousePress) {
					element.setPressState(true);
					element.setMouseRelativeLocation();
					findElement=true;
				}
				if(state==MouseState.MouseReleased) {
					element.setPressState(false);
					element.clearMouseRelativeLocation();
					findElement=true;
				}
			}
			if(element.getPressState()&&!element.isInElement(e.getX(), e.getY())) {
				element.actionListener.mouseAction(e.getButton(), MouseState.MouseReleased);
				findElement=true;
			}
			if(findElement) {
				System.out.println(element.getCoveage());
				break;
			}
		}
	}
	
	
	
	
	/*
	 * ���췽��
	 */
	public BaseCanvas(JFrame context,Dimension size) {
		// TODO Auto-generated constructor stub
		if(size==null) {
			size=new Dimension(500, 500);
		}
		this.setSize(size);
		this.canvasSize=size;
		this.context=context;
		initialize();
	}
	
	public BaseCanvas(JFrame context,int width,int height) {
		if(width<0) {
			width=0;
		}
		if(height<0) {
			height=0;
		}
		this.setSize(width, height);
		this.canvasSize=new Dimension(width, height);
		this.context=context;
		initialize();
	}
	
	
	
	
	
	
	
	
	
	/*
	 * ���з�����Ԫ����ɾ�Ĳ�
	 */
	
	public void addManager(BaseElementManager manager) {
		if(manager!=null) {
			this.elementManagers.add(manager);
			totalElement.addAll(manager.getElements());
			Collections.sort(totalElement);
		}
	}
	
	
	
	
	public void removeManager(BaseElementManager manager) {
		if(manager!=null) {
			this.elementManagers.remove(manager);
			for(BaseElement element:manager.getElements()) {
				this.totalElement.remove(element);
			}
			Collections.sort(totalElement);
		}
	}
	
	
	
	
	public void clearManager() {
		if(elementManagers.size()>0) {
			elementManagers.clear();
		}
	}
	
	
	
	
	
	public void addWallElement(BaseElement element) {
		if(element!=null)
			wall.add(element);
	}
	
	
	
	
	public void removeWallElement(BaseElement element) {
		if(element!=null)
			wall.remove(element);
	}
	
	
	
	
	
	
	
	
	
	/*
	 * ֡�����߳�
	 */
	public void startUpdate(UpdateListener I) {
		if(isRunning) {
			return;
		}
		new Thread(new Runnable() {
			

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isRunning=true;
				quadTreeManager.buildTree();
				while(isRunning) {
					if(!isEnable) {
						continue;
					}
					if(!isRunning) {
						break;
					}
					//���FPS
					setStartTime(System.currentTimeMillis());
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(I!=null) {
						I.updatePerformed();
					}
					//�������¼�
					if(isMouseListening) {
						if(isMousePress==1) {
							//��갴��
							
						}
						if(isMousePress==0) {
							//���ſ�
						}
						
					}
					//updateԪ��
					for(BaseElementManager m:elementManagers) {
						m.update();
					}
					//�ػ���Ļ
					repaint();
					
					
					//��̬����FPS
					setEndTime(System.currentTimeMillis());
					if(endTime-startTime==0) {
						try {
							fps=1000;
							Thread.sleep(1000/exceptFPS);
							continue;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					fps=1000/(endTime-startTime);
					if((int)fps<(int)exceptFPS) {
						if(sleepTime<1) {
							sleepTime=0;
						}else {
							sleepTime--;
						}
					}
					if((int)fps>(int)exceptFPS) {
						sleepTime++;
					}
				}
			}
		}).start();
	}
	
	
	
	
	public void stopUpdate() {
		isRunning=false;
	}
	
	
	
	
	
	//���������ڵ��������¼�
	public void setMouseListener() {
		//System.out.println(isMouseListening);
		if(!isMouseListening) {
			this.addMouseListener(mouseListener);
		}
		isMouseListening=true;
		
	}
	//�Ƴ����ڵ�������
	public void removeMouseListener() {
		if(!isMouseListening) {
			return;
		}
		isMouseListening=false;
		if(context!=null) {
			this.removeMouseListener(mouseListener);
		}
	}
	
	
	
	
	
	//���������ڵļ��̼����¼�
	public void setKeyboardListener() {
		if(context!=null) {
			addKeyListener(keyListener);
		}
	}
	public void removeKeyboardListener() {
		if(context!=null) {
			removeKeyListener(keyListener);
		}
	}
	
	
	
	
	
	
	/*
	 * ���ƣ���д����
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	
	@Override
	public void paint(Graphics g) {
		// ���Ʊ���
		BufferedImage bufferedImage =(BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		Graphics2D g2d=null;
		if(backgroundImage!=null) {
			g2d=(Graphics2D) bufferedImage.getGraphics();
			g2d.drawImage(backgroundImage, 0, 0, null);
		}else {
			g2d=(Graphics2D) bufferedImage.getGraphics();
			g2d.setColor(defaultColor);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		//�滭Ԫ��
		if(elementManagers!=null&&elementManagers.size()>0) {
			for(BaseElementManager manager:elementManagers) {
				for(BaseElement element:manager.getElements()) {
					//�߿���ײ
					if(isborderHit) {
						if(element.x<0) {
							borderhitListener.onHit(element,BorderHitlState.LEFT);
						}
						if(element.x+element.getWidth()>this.getWidth()) {
							borderhitListener.onHit(element,BorderHitlState.RIGHT);
						}
						if(element.y<0) {
							borderhitListener.onHit(element,BorderHitlState.TOP);
						}
						if(element.y+element.getHeight()>this.getHeight()) {
							borderhitListener.onHit(element,BorderHitlState.BOTTOM);
						}
						//�߿�Ԫ����ײ
						for(BaseElement e:wall){
							if(element.CheckHitTo(e)) {
								if(elementHitListener!=null) {
									elementHitListener.onHit(element, e);
								}
							}
						}
					}
					//Ԫ����ײ
					if(true) {
						//================================================
					}
					//��仺��֡
					g2d.drawImage(element.getImage(), element.x, element.y, null);
				}
			}
		}
		//���ٻ���
		g2d.dispose();
		//��仭��
		g.drawImage(bufferedImage, 0, 0, null);
	}
	public JFrame getContext() {
		return context;
	}
	public void setContext(JFrame context) {
		this.context = context;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
