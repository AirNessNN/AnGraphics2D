package Manager;
import java.util.ArrayList;

import BaseCanvas.BaseCanvas;
import Element.BaseElement;

public abstract class BaseElementManager {
	
	//属性
	private 		ArrayList<BaseElement>							elementList;				//元素列表
	private 		boolean 													isEnable;					//可用状态
	private 		int 															maxCoverage=1;//最大图层数
	
	private 		BaseCanvas	 											context;
	
	
	
	
	
	
	
	//属性maxCoverage：读写
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
	
	
	
	
	
	//属性isEnable：读写
	public void setEnable(boolean b) {
		isEnable=b;
	}
	public boolean isEnable() {
		return isEnable;
	}
	
	
	
	
	//属性Context：只读
	public BaseCanvas getContext() {
		return context;
	}
	/*public void setContext(BaseCanvas context) {
		this.context = context;
	}*/
	
	
	
	
	//属性elementList：只读
	public ArrayList<BaseElement>getElements(){
		if(elementList!=null) {
			return elementList;
		}
		return null;
	}
	
	
	
	
	
	//属性Size：只读
	public int getSize() {
		if(getElements()!=null) {
			return getElements().size();
		}
		return 0;
	}
	
	
	
	
	//属性ProcessTime：只读
	public long getProcessTime() {
		if(context!=null) {
			return context.getProcessTime();
		}
		return -1;                              
	}
	
	
	
	
	//属性Context.FPS：只读
	public double getCanvasFPS() {
		if(context!=null) {
			return context.getFPS();
		}
		return -1;
	}
	
	
	
	
	//初始化
	protected void initialize(ArrayList<BaseElement>elements,BaseCanvas context) {
		if(elements==null) {                                                     
			setElementList(new ArrayList<>());
		}else {
			setElementList(elements);
		}
		this.context=context;
		isEnable=true;
	}
	
	
	
	
	//构造函数
	public BaseElementManager() {
		initialize(null, null);
	}
	//创建一个基础的Manager对象，管理Element元素
	public BaseElementManager(BaseCanvas context) {
		// TODO Auto-generated constructor stub
		initialize(null,context);
	}
	
	public BaseElementManager(ArrayList<BaseElement>elements,BaseCanvas context) {
		initialize(elements,context);
	}
	
	
	
	
	
	
	
	//公用方法
	//添加元素
	public void addElement(BaseElement element) {
		if(element!=null) {
			getElements().add(element);
		}
	}
	
	//删除元素
	public void remove(BaseElement element) {
		if(element!=null) {
			getElements().remove(element);
		}
	}
	
	//查找元素
	public void removeAt(int index) {
		if(index<getSize()||index>=0) {
			getElements().remove(index);
		}
	}
	
	//清空元素
	public void clear() {
		if(getSize()!=0) {
			getElements().clear();
		}
	}
	
	
	
	//更新元素的方法
	public abstract void update();
	
	public void setElementList(ArrayList<BaseElement> elementList) {
		this.elementList = elementList;
	}
	
	

}
