package BaseCanvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ActionPacket.BorderHitListener;
import ActionPacket.ElementHitListener;
import ActionPacket.UpdateListener;
import Element.BaseElement;
import Element.GravityElement;
import Manager.BaseElementManager;

public class BaseCanvas extends Canvas{
	
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	/*
	 * ��Ա
	 */
	private ArrayList<BaseElementManager>			elementManagers;//Ԫ�ع�����
	private ArrayList<BaseElement>							wall;//���ڱ���ǽ��Ԫ��
	//����
	private BufferedImage 										backgroundImage;//����
	private Color 														defaultColor;//������ɫ
	private Dimension 												backgroundSize;//������С
	private Dimension												canvasSize;//�ؼ���С
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
	//���
	private Point														cameraLocation;//���λ��
	private boolean													isSmoothMove;//���ƽ���ƶ�����
	private BaseElement											cameraLockObject;//��������Ķ���
	
	
	
	public BorderHitListener 										borderhitListener;
	public ElementHitListener									elementHitListener;
	
	
	
	/*
	 * ����
	 */
	public boolean isElementHit() {
		return isElementHit;
	}
	public void setElementHit(boolean isElementHit) {
		this.isElementHit = isElementHit;
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
			System.out.println("starttime:"+startTime);
			startTime=time;
		}
	}
	private void setEndTime(long time) {
		synchronized (timeLocker) {
			System.out.println("endtime:"+endTime);
			endTime=time;
		}
	}
	public long getProcessTime() {
		synchronized (timeLocker) {
			long time=System.currentTimeMillis();
			System.out.println(time+" "+startTime);
			System.out.println(time-startTime);
			return time-startTime;
		}
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
	}
	
	
	
	
	
	
	/*
	 * ���췽��
	 */
	public BaseCanvas(Dimension size) {
		// TODO Auto-generated constructor stub
		if(size==null) {
			size=new Dimension(500, 500);
		}
		this.setSize(size);
		this.canvasSize=size;
		initialize();
	}
	
	public BaseCanvas(int width,int height) {
		if(width<0) {
			width=0;
		}
		if(height<0) {
			height=0;
		}
		this.setSize(width, height);
		this.canvasSize=new Dimension(width, height);
		initialize();
	}
	
	
	
	
	
	
	
	
	
	/*
	 * ���з�����Ԫ����ɾ�Ĳ�
	 */
	
	public void addManager(BaseElementManager manager) {
		if(manager!=null) {
			this.elementManagers.add(manager);
		}
	}
	
	
	
	
	public void removeManager(BaseElementManager manager) {
		if(manager!=null) {
			this.elementManagers.remove(manager);
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
	 * �����߳̿���
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
				while(isRunning) {
					if(!isEnable) {
						continue;
					}
					if(!isRunning) {
						break;
					}
					//���FPS
					setStartTime(System.currentTimeMillis());
					if(I!=null) {
						I.updatePerformed();
					}
					//updateԪ��
					for(BaseElementManager m:elementManagers) {
						m.update();
					}
					//�ػ���Ļ
					repaint();
					
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				for(BaseElement element:manager.getElementList()) {
					//�߿���ײ
					if(isborderHit) {
						if(element.x<0) {
							borderhitListener.onHit(element,2);
						}
						if(element.x+element.getWidth()>this.getWidth()) {
							borderhitListener.onHit(element,4);
						}
						if(element.y<0) {
							borderhitListener.onHit(element,1);
						}
						if(element.y+element.getHeight()>this.getHeight()) {
							borderhitListener.onHit(element,3);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
