package Element;
import java.awt.Color;
import java.awt.Graphics2D;
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
	
	
	
	//元素属性
	/**当前元素X坐标*/
	public int 										x=0;
	/**当前元素Y坐标*/
	public int 										y=0;
	/**元素宽度*/
	private int 										width=30;
	/**元素高度*/
	private int 										height=30;
	/**是否背景元素*/
	private boolean 								isBackgroundElement=false;
	
	private int										coveage=1;
	
	private String									tag;
	
	private BufferedImage						image;
	private Graphics2D							graphics2d;
	
	//碰撞方面
	public int 										hitModel=0;
	
	//移动
	private int 										speed=0;//1秒钟移动的距离
	
	//事件
	private int										mouseEnter=-1;
	
	public MouseActionListener				actionListener=null;
	
	
	
	
	
	
	
	//鼠标进入
	public int getMouseEnter() {
		return mouseEnter;
	}
	public void setMouseEnter(int mouseEnter) {
		this.mouseEnter = mouseEnter;
	}
	
	//图层
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
	
	//速度属性
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//X属性
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	//Y属性
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	//Width属性
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		if(width>0) {
			this.width = width;
		}
	}

	//Height属性
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		if(height>0) {
			this.height = height;
		}
	}

	//背景属性
	public boolean isBackgroundElement() {
		return isBackgroundElement;
	}
	public void setBackgroundElement(boolean isBackgroundElement) {
		this.isBackgroundElement = isBackgroundElement;
	}

	//图像属性
	public BufferedImage getImage() {
		if(image==null) {
			image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		return image;
	}
	public void setImage(BufferedImage image) {
		if(image!=null) {
			this.image = image;
			this.height=image.getHeight();
			this.width=image.getWidth();
		}
	}
	
	//TAG信息
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		if(tag!=null) {
			this.tag=tag;
		}
	}
	
	

	

	/*
	 * 构造
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
		//初始化
		image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics2d=image.createGraphics();
		graphics2d.setColor(Color.darkGray);
		graphics2d.fillRect(0, 0, width, height);
	}
	
	public BaseElement(BufferedImage image) {
		// TODO Auto-generated constructor stub
		if(image!=null) {
			this.width=image.getWidth();
			this.height=image.getHeight();
			this.image=image;
		}else {
			throw new NullPointerException("图像不能为null");
		}
	}
	
	
	
	
	
	
	/**
	 * 获得该对象的矩形
	 * @return
	 */
	public Rectangle getRectangle(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	
	
	
	
	/**
	 * 碰撞检测
	 * @param rectangle 要与此检测的图形
	 * @return 碰撞返回true
	 */
	public boolean CheckHitTo(BaseElement element){
		if(element.hitModel==1&&this.hitModel==1) {
			//矩形和矩形
			return getRectangle().intersects(element.getRectangle());
		}
		if(element.hitModel==2&&this.hitModel==2) {
			//圆和圆
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
			//矩形和圆
			return false;
		}
		if(element.hitModel==2&&this.hitModel==1) {
			//圆和矩形
			return false;
		}
		return false;
	}
	

}
