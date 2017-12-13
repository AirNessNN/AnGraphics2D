package Manager;
import java.util.ArrayList;

import BaseCanvas.BaseCanvas;
import Element.BaseElement;

public abstract class BaseElementManager {
	
	//����
	private 		ArrayList<BaseElement>							elementList;				//Ԫ���б�
	private 		boolean 													isEnable;					//����״̬
	private 		int 															maxCoverage=1;//���ͼ����
	
	private 		BaseCanvas	 											context;
	
	
	
	
	
	
	
	//����maxCoverage����д
	public int getMaxCoverage() {
		return maxCoverage;
	}
	public void setMaxCoverage(int maxCoverage) {
		if(maxCoverage<1) {
			this.maxCoverage=1;
			return;
		}
		if(maxCoverage>100) {
			this.maxCoverage=100;
			return;
		}
		this.maxCoverage = maxCoverage;
	}
	
	
	
	
	
	//����isEnable����д
	public void setEnable(boolean b) {
		isEnable=b;
	}
	public boolean isEnable() {
		return isEnable;
	}
	
	
	
	
	//����Context��ֻ��
	public BaseCanvas getContext() {
		return context;
	}
	/*public void setContext(BaseCanvas context) {
		this.context = context;
	}*/
	
	
	
	
	//����elementList��ֻ��
	public ArrayList<BaseElement>getElements(){
		if(elementList!=null) {
			return elementList;
		}
		return null;
	}
	
	
	
	
	
	//����Size��ֻ��
	public int getSize() {
		if(getElements()!=null) {
			return getElements().size();
		}
		return 0;
	}
	
	
	
	
	//����ProcessTime��ֻ��
	public long getProcessTime() {
		if(context!=null) {
			return context.getProcessTime();
		}
		return -1;                              
	}
	
	
	
	
	//����Context.FPS��ֻ��
	public double getCanvasFPS() {
		if(context!=null) {
			return context.getFPS();
		}
		return -1;
	}
	
	
	
	
	//��ʼ��
	protected void initialize(ArrayList<BaseElement>elements,BaseCanvas context) {
		if(elements==null) {                                                     
			setElementList(new ArrayList<>());
		}else {
			setElementList(elements);
		}
		this.context=context;
		isEnable=true;
	}
	
	
	
	
	//���캯��
	public BaseElementManager() {
		initialize(null, null);
	}
	//����һ��������Manager���󣬹���ElementԪ��
	public BaseElementManager(BaseCanvas context) {
		// TODO Auto-generated constructor stub
		initialize(null,context);
	}
	
	public BaseElementManager(ArrayList<BaseElement>elements,BaseCanvas context) {
		initialize(elements,context);
	}
	
	
	
	
	
	
	
	//���÷���
	//���Ԫ��
	public void addElement(BaseElement element) {
		if(element!=null) {
			getElements().add(element);
		}
	}
	
	//ɾ��Ԫ��
	public void remove(BaseElement element) {
		if(element!=null) {
			getElements().remove(element);
		}
	}
	
	//����Ԫ��
	public void removeAt(int index) {
		if(index<getSize()||index>=0) {
			getElements().remove(index);
		}
	}
	
	//���Ԫ��
	public void clear() {
		if(getSize()!=0) {
			getElements().clear();
		}
	}
	
	
	
	//����Ԫ�صķ���
	public abstract void update();
	
	public void setElementList(ArrayList<BaseElement> elementList) {
		this.elementList = elementList;
	}
	
	

}
