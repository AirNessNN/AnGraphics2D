package Element;

import java.awt.image.BufferedImage;

import Manager.GravityElementManager;

public class GravityElement extends BaseElement{
	
	
	/*
	 * 
	 * ��Ա����
	 */
	//�������
	private boolean 								isGravity=false;//�Ƿ������������
	private boolean 								isDown=false;//�Ƿ�����׹
	private double 								downTime=0;//��׹�Ѿ�������ʱ��
	private boolean 								isOnFloor=false;//�Ƿ��ڵذ���
	private double 								gravitySpeed=0;//�����ٶ�
	
	private GravityElementManager		context;
	
	
	
	/*
	 * 
	 * ����
	 */
	//��������
	public boolean isGravity() {
		return isGravity;
	}
	public void setGravity(boolean isGravity) {
		this.isGravity = isGravity;
	}

	//��������
	public boolean isDown() {
		return isDown;
	}

	//����ʱ��
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
	
	
	
	//����ʱ�䴦��
	public void downTimeAdd(long time) {
		if(time<1) {
			downTime+=0.1;
		}else {
			downTime+=time;
		}
	}
	

}
