package Manager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import BaseCanvas.BaseCanvas;
import Element.BaseElement;
import Element.GravityElement;

public class GravityElementManager extends BaseElementManager{
	
	
	private 				float 			gravity;//����ϵ��
	private				int 				maxSpeed;
	private				Toolkit			tool;
	
	
	
	
	private void initialize() {
		gravity=(float)0.06;
		maxSpeed=100;
		tool=Toolkit.getDefaultToolkit();
	}
	
	public GravityElementManager() {
		initialize();
	}
	
	
	public GravityElementManager(BaseCanvas context) {
		super(context);
		initialize();
	}
	
	
	public GravityElementManager(ArrayList<BaseElement> elements,BaseCanvas context){
		super(elements,context);
		initialize();
	}
	
	
	
	
	
	/**
	 * ��������ϵ��
	 * @param g ����ϵ����0.1��10.0֮��ĸ�����
	 */
	public void setGravityValue(float g) {
		if(g>0.05&&g<10.0){
			gravity=g;
		}
	}
	
	
	
	
	
	/**
	 * ����������Ӱ���Ԫ�صĸ߶�
	 */
	@Override
	public void update() {
		if(getElements()==null){
			return;
		}
		if(!isEnable()) {
			return;
		}
		for(BaseElement e : getElements()){
			GravityElement element=(GravityElement)e;
			if(getContext().isMouseListening()&& element.isCanMouseMove()&&e.isPrese) {
				Point p=MouseInfo.getPointerInfo().getLocation();
				e.x=p.x-e.getXrelative();
				e.y=p.y-e.getYrelative();
				element.setDownTime(0);
			}else {
				if(element.isGravity()&&!element.isOnFloor()){
					int h=0;
					if((element.getGravitySpeed())>=maxSpeed){
						element.setGravitySpeed(maxSpeed);
					}else{
						element.setGravitySpeed(gravity*element.getDownTime());
						element.downTimeAdd(getProcessTime());
					}
					h=(int)(element.getGravitySpeed()+(gravity*element.getDownTime()));
					element.y+=h;
				}
			}
			
		}
	}
	

}
