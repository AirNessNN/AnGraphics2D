package Element;

import java.awt.image.BufferedImage;

import Manager.GravityElementManager;

public class GravityElement extends BaseElement{
	
	
	/*
	 * 
	 * 成员变量
	 */
	//重力相关
	private boolean 								isGravity=false;//是否接受重力控制
	private boolean 								isDown=false;//是否在下坠
	private long 									downTime=0;//下坠已经持续的时间
	private boolean 								isOnFloor=false;//是否在地板上
	private float 									gravitySpeed=0;//重力速度
	
	private GravityElementManager		context;
	
	
	
	/*
	 * 
	 * 属性
	 */
	//重力属性
	public boolean isGravity() {
		return isGravity;
	}
	public void setGravity(boolean isGravity) {
		this.isGravity = isGravity;
	}

	//下落属性
	public boolean isDown() {
		return isDown;
	}

	//下落时间
	public long getDownTime() {
		return downTime;
	}
	public void setDownTime(int downTime) {
		this.downTime=downTime;
	}


	public boolean isOnFloor() {
		return isOnFloor;
	}
	public void setOnFloor(boolean b) {
		isOnFloor=b;
	}


	public float getGravitySpeed() {
		return gravitySpeed;
	}
	public void setGravitySpeed(float value) {
		gravitySpeed=value;
	}
	
	
	
	
	
	
	
	public GravityElement(int x, int y, int width, int height,GravityElementManager context) {
		super(x, y, width, height);
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	
	public GravityElement(BufferedImage image,GravityElementManager context) {
		// TODO Auto-generated constructor stub
		super(image);
		this.context=context;
	}
	
	
	
	//下落时间处理
	public void downTimeAdd(long time) {
		if(time<1) {
			downTime++;
		}else {
			downTime+=time;
		}
	}
	

}
