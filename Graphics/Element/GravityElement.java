package Element;

import java.awt.image.BufferedImage;

public class GravityElement extends BaseElement{
	
	
	/*
	 * 
	 * 成员变量
	 */
	//重力相关
	private boolean 								isGravity=false;//是否接受重力控制
	private boolean 								isDown=false;//是否在下坠
	private int 										downTime=0;//下坠已经持续的时间
	private boolean 								isOnFloor=false;//是否在地板上
	private float 									gravitySpeed=0;//重力速度
	
	
	
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
	public int getDownTime() {
		return downTime;
	}
	public void downTimeAdd() {
		downTime++;
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
	
	
	
	
	
	public GravityElement(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public GravityElement(BufferedImage image) {
		// TODO Auto-generated constructor stub
		super(image);
	}

}
