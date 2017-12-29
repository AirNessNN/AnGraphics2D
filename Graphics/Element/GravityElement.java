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
	private double 								downTime=0;//下坠已经持续的时间
	private boolean 								isOnFloor=false;//是否在地板上
	private double 								gravitySpeed=0;//重力速度
	
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
	public double getDownTime() {
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
		if(!b) {
			downTime=0;
		}
	}


	public double getGravitySpeed() {
		return gravitySpeed;
	}
	public void setGravitySpeed(double value) {
		gravitySpeed=value/100;
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
			downTime+=0.1;
		}else {
			downTime+=time;
		}
	}
	

}
