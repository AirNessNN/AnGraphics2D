package Manager;
import java.util.ArrayList;

import Element.BaseElement;
import Element.GravityElement;

public class GravityElementManager extends BaseElementManager{
	
	
	private 				float 			gravity;//����ϵ��
	private				int 				maxSpeed;
	
	
	
	
	private void initialize() {
		gravity=(float)0.4;
		maxSpeed=100;
	}
	
	
	public GravityElementManager() {
		initialize();
	}
	
	public GravityElementManager(ArrayList<BaseElement> elements){
		super(elements);
		initialize();
	}
	
	
	
	
	
	/**
	 * ��������ϵ��
	 * @param g ����ϵ����0.1��10.0֮��ĸ�����
	 */
	public void setGravityValue(float g) {
		if(g>0.1&&g<10.0){
			gravity=g;
		}
	}
	
	
	
	
	
	/**
	 * ����������Ӱ���Ԫ�صĸ߶�
	 */
	@Override
	public void update() {
		if(getElementList()==null){
			return;
		}
		if(!isEnable()) {
			return;
		}
		for(BaseElement e : getElementList()){
			GravityElement element=(GravityElement)e;
			if(element.isGravity()&&!element.isOnFloor()){
				int h=0;
				if((element.getGravitySpeed())>=maxSpeed){
					element.setGravitySpeed(maxSpeed);
				}else{
					element.setGravitySpeed(gravity*element.getDownTime());
					element.downTimeAdd();
				}
				h=(int)(element.getGravitySpeed()+(gravity*element.getDownTime()));
				element.y+=h;
			}
		}
	}
	

}
