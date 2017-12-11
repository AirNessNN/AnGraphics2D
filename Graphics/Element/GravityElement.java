package Element;

import java.awt.image.BufferedImage;

public class GravityElement extends BaseElement{
	
	
	/*
	 * 
	 * ��Ա����
	 */
	//�������
	private boolean 								isGravity=false;//�Ƿ������������
	private boolean 								isDown=false;//�Ƿ�����׹
	private int 										downTime=0;//��׹�Ѿ�������ʱ��
	private boolean 								isOnFloor=false;//�Ƿ��ڵذ���
	private float 									gravitySpeed=0;//�����ٶ�
	
	
	
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
