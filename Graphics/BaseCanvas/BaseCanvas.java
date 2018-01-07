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
	 * 成员
	 */
	private ArrayList<BaseElementManager>			elementManagers;//元素管理器
	private ArrayList<BaseElement>							wall;//属于背景墙的元素
	//属性
	private JFrame														context;
	private BufferedImage 										backgroundImage;//背景
	private Color 														defaultColor;//背景颜色
	private Dimension 												backgroundSize;//背景大小
	private Dimension												canvasSize;//控件大小
	private double													unit=1f;
	private ArrayList<BaseElement>							totalElement;//用于渲染总和的元素
	//任务相关
	private boolean 													isEnable;
	private boolean 													isRunning;
	//FPS相关
	private long 														exceptFPS=60;//预期FPS
	private float 														fps=0;//当前FPS
	private volatile long 											startTime=0;
	private volatile long 											endTime=0;
	private long 														sleepTime=0;
	private Object														timeLocker;
	
	//碰撞相关
	private boolean													isborderHit;//是否打开边界碰撞检测
	private boolean													isElementHit;//是否打开元素碰撞检测
	//相机相关
	private Point														cameraLocation;//相机位置
	private boolean													isSmoothMove;//相机平滑移动开关
	private BaseElement											cameraLockObject;//相机锁定的对象
	//鼠标相关
	private boolean													isMouseListening=false;
	
	private int															mouseKey;
	private int															isMousePress=-1;
	
	private int															lastMouseState=-1;
	public static final int											MOUSE_LEFT_BUTTON=1;
	public static final int 											MOUSE_WHEEL=2;
	public static final int											MOUSE_RIGHT_BUTTON=3;
	private int 															wheelMoveValue;
	private boolean													mouseVisable=true;
	
	//监听事件
	public MouseActionListener									mouseActionListener;
	public BorderHitListener 										borderhitListener;
	public ElementHitListener									elementHitListener;
	public ElementControl											elementControl;
	
	
	private QuadTreeManager									quadTreeManager;//四叉树管理器
	
	
	public interface ElementControl{
		public void addElement(BaseElement element);
		public void removeElement(BaseElement element);
	}
	
	
	
	
	
	
	
	
	//窗口的鼠标监听事件
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
	
	//键盘监听
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
	 * 属性
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
	
	
	
	//让管理器获取到当前的刷新时间
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
	 * 私有方法
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
		 * 元素控制接口用于为画板类的监听遍历提供图层支持，元素的点击事件和绘制是有顺序的
		 * 因此在Canvas中用一个Arraylist托管所有BaseElement，维护一个从小到大的数组，
		 * 在元素的绘制和焦点中起到很大的作用
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
	
	//每个设置了鼠标事件的元素遍历
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
	 * 构造方法
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
	 * 公有方法：元素增删改查
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
	 * 帧计算线程
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
					//检测FPS
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
					//检查鼠标事件
					if(isMouseListening) {
						if(isMousePress==1) {
							//鼠标按下
							
						}
						if(isMousePress==0) {
							//鼠标放开
						}
						
					}
					//update元素
					for(BaseElementManager m:elementManagers) {
						m.update();
					}
					//重绘屏幕
					repaint();
					
					
					//动态设置FPS
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
	
	
	
	
	
	//设置主窗口的鼠标监听事件
	public void setMouseListener() {
		//System.out.println(isMouseListening);
		if(!isMouseListening) {
			this.addMouseListener(mouseListener);
		}
		isMouseListening=true;
		
	}
	//移除窗口的鼠标监听
	public void removeMouseListener() {
		if(!isMouseListening) {
			return;
		}
		isMouseListening=false;
		if(context!=null) {
			this.removeMouseListener(mouseListener);
		}
	}
	
	
	
	
	
	//设置主窗口的键盘监听事件
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
	 * 绘制：重写方法
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	
	@Override
	public void paint(Graphics g) {
		// 绘制背景
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
		
		//绘画元素
		if(elementManagers!=null&&elementManagers.size()>0) {
			for(BaseElementManager manager:elementManagers) {
				for(BaseElement element:manager.getElements()) {
					//边框碰撞
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
						//边框元素碰撞
						for(BaseElement e:wall){
							if(element.CheckHitTo(e)) {
								if(elementHitListener!=null) {
									elementHitListener.onHit(element, e);
								}
							}
						}
					}
					//元素碰撞
					if(true) {
						//================================================
					}
					//填充缓冲帧
					g2d.drawImage(element.getImage(), element.x, element.y, null);
				}
			}
		}
		//销毁画笔
		g2d.dispose();
		//填充画布
		g.drawImage(bufferedImage, 0, 0, null);
	}
	public JFrame getContext() {
		return context;
	}
	public void setContext(JFrame context) {
		this.context = context;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
