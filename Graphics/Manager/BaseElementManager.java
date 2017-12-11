package Manager;
import java.util.ArrayList;

import Element.BaseElement;

public abstract class BaseElementManager {
	
	//����
	private 		ArrayList<BaseElement>									elementList;				//Ԫ���б�
	private 		boolean 													isEnable;					//����״̬
	
	
	
	//����isEnable����д
	public void setEnable(boolean b) {
		isEnable=b;
	}
	public boolean isEnable() {
		return isEnable;
	}
	
	
	
	
	//����elementList��ֻ��
	public ArrayList<BaseElement>getElements(){
		if(getElementList()!=null) {
			return getElementList();
		}
		return null;
	}
	
	
	
	
	
	//����Size��ֻ��
	public int getSize() {
		if(getElementList()!=null) {
			return getElementList().size();
		}
		return 0;
	}
	
	
	
	
	//��ʼ��
	protected void initialize(ArrayList<BaseElement>elements) {
		if(elements==null) {
			setElementList(new ArrayList<>());
		}else {
			setElementList(elements);
		}
		
		isEnable=true;
	}
	
	
	
	
	
	//����һ��������Manager���󣬹���ElementԪ��
	public BaseElementManager() {
		// TODO Auto-generated constructor stub
		initialize(null);
	}
	
	public BaseElementManager(ArrayList<BaseElement>elements) {
		initialize(elements);
	}
	
	
	//���Ԫ��
	public void addElement(BaseElement element) {
		if(element!=null) {
			getElementList().add(element);
		}
	}
	
	//ɾ��Ԫ��
	public void remove(BaseElement element) {
		if(element!=null) {
			getElementList().remove(element);
		}
	}
	
	//����Ԫ��
	public void removeAt(int index) {
		if(index<getSize()||index>=0) {
			getElementList().remove(index);
		}
	}
	
	//���Ԫ��
	public void clear() {
		if(getSize()!=0) {
			getElementList().clear();
		}
	}
	
	
	
	//����Ԫ�صķ���
	public abstract void update();
	
	public ArrayList<BaseElement> getElementList() {
		return elementList;
	}
	public void setElementList(ArrayList<BaseElement> elementList) {
		this.elementList = elementList;
	}
	
	

}
