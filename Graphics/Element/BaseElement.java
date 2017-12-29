package Element;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
/**
 * 
 * 
 * 
 * @author AN
 *
 */
import java.awt.image.BufferedImage;

import AcitonListener.MouseActionListener;
public abstract class BaseElement {
	
	public static final int NO_MODEL=0;
	public static final int RECTANGLE=1;
	public static final int OVAL=2;
	
	
	
	//Ԫ������
	/**��ǰԪ��X����*/
	public int 										x=0;
	/**��ǰԪ��Y����*/
	public int 										y=0;
	/**Ԫ�ؿ��*/
	private int 										width=30;
	/**Ԫ�ظ߶�*/
	private int 										height=30;
	/**�Ƿ񱳾�Ԫ��*/
	private boolean 								isBackgroundElement=false;
	
	private Color									color=Color.DARK_GRAY;
	
	private int										coveage=1;
	
	private String									tag;
	//ͼ��
	private BufferedImage						currentImage;//��ǰ֡��ͼ��
	private BufferedImage						finalImage;
	private BufferedImage						backgroundImage;
	private Graphics2D							graphics2d;
	
	//��ײ����
	public int 										hitModel=0;
	
	//�ƶ�
	private int 										speed=0;//1�����ƶ��ľ���
	private boolean 								canMouseMove=false;
	public boolean								isPrese=false;
	private int 										xRelative=0;
	private int										yRelative=0;
	
	//�¼�
	private int										mouseEnter=-1;
	
	public MouseActionListener				actionListener=null;
	
	
	
	
	
	
	//���Ա�����ƶ�
	public boolean isCanMouseMove() {
		return canMouseMove;
	}
	public void setCanMouseMove(boolean canMouseMove) {
		this.canMouseMove = canMouseMove;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	//������
	public int getMouseEnter() {
		return mouseEnter;
	}
	public void setMouseEnter(int mouseEnter) {
		this.mouseEnter = mouseEnter;
	}
	
	//ͼ��
	public int getCoveage() {
		return coveage;
	}
	public void setCoveage(int coveage) {
		if(coveage<1) {
			this.coveage=1;
			return;
		}
		if(coveage>100) {
			this.coveage=100;
			return;
		}
		this.coveage = coveage;
	}
	
	//�ٶ�����
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//X����
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	//Y����
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	//Width����
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		if(width>0) {
			this.width = width;
		}
	}

	//Height����
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		if(height>0) {
			this.height = height;
		}
	}

	//��������
	public boolean isBackgroundElement() {
		return isBackgroundElement;
	}
	public void setBackgroundElement(boolean isBackgroundElement) {
		this.isBackgroundElement = isBackgroundElement;
	}

	//ͼ������
	public BufferedImage getImage() {
		if(backgroundImage==null) {
			backgroundImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);	
		}
		graphics2d=backgroundImage.createGraphics();
		graphics2d.setColor(getColor());
		graphics2d.fillRect(0, 0, width, height);
		
		return backgroundImage;
	}
	public void setImage(BufferedImage image) {
		if(image!=null) {
			this.backgroundImage = image;
			this.height=image.getHeight();
			this.width=image.getWidth();
		}
	}
	
	//TAG��Ϣ
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		if(tag!=null) {
			this.tag=tag;
		}
	}
	
	

	

	/*
	 * ����
	 */
	public BaseElement() {
	}
	
	
	
	
	public BaseElement(int x,int y,int width,int height) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		if(width>0)
			this.width=width;
		if(height>0)
			this.height=height;
		//��ʼ��
		backgroundImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics2d=backgroundImage.createGraphics();
		graphics2d.setColor(Color.darkGray);
		graphics2d.fillRect(0, 0, width, height);
	}
	
	public BaseElement(BufferedImage image) {
		// TODO Auto-generated constructor stub
		if(image!=null) {
			this.width=image.getWidth();
			this.height=image.getHeight();
			this.backgroundImage=image;
		}else {
			throw new NullPointerException("ͼ����Ϊnull");
		}
	}
	
	
	
	
	
	
	/**
	 * ��øö���ľ���
	 * @return
	 */
	public Rectangle getRectangle(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	
	
	/**
	 * ͨ��X��Y������жϸ�������Ƿ���Ԫ����
	 * @param x x����
	 * @param y y����
	 * @return ���ڷ���true
	 */
	public boolean isInElement(int x, int y) {
		return (x > this.getX() && x < this.getX() + this.getWidth() && y > this.getY()
				&& y < this.getY() + this.height);
	}
	
	
	
	
	
	public void setMouseRelativeLocation() {
		Point p=MouseInfo.getPointerInfo().getLocation();
		xRelative=p.x-x;
		yRelative=p.y-y;
	}
	public void clearMouseRelativeLocation() {
		xRelative=yRelative=0;
	}
	
	public int getXrelative() {
		return xRelative;
	}
	public int getYrelative() {
		return yRelative;
	}
	
	
	
	
	
	/**
	 * ��ײ���
	 * @param rectangle Ҫ��˼���ͼ��
	 * @return ��ײ����true
	 */
	public boolean CheckHitTo(BaseElement element){
		if(element.hitModel==1&&this.hitModel==1) {
			//���κ;���
			return getRectangle().intersects(element.getRectangle());
		}
		if(element.hitModel==2&&this.hitModel==2) {
			//Բ��Բ
			Rectangle rectangle=element.getRectangle();
			int tx=(x+width)/2;
			int ty=(y+height)/2;
			int r1=width/2;
			int rx=(rectangle.x+rectangle.width)/2;
			int ry=(rectangle.y+rectangle.height)/2;
			int r2=rectangle.width/2;
			double d=Math.sqrt(Math.pow(Math.abs(rx-tx), 2)+Math.pow(Math.abs(ry-ty), 2));
			return d<(r1+r2);
		}
		if(element.hitModel==1&&this.hitModel==2) {
			//���κ�Բ
			return false;
		}
		if(element.hitModel==2&&this.hitModel==1) {
			//Բ�;���
			return false;
		}
		return false;
	}
	

}
