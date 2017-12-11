package HitCheck;

import java.awt.Rectangle;
import java.util.ArrayList;
import Element.BaseElement;

public class QuadNode {
	
	public int level=0;
	
	
	//存在的象限
	public QuadNode[] childNode;
	
	public ArrayList<BaseElement> elements;//
	
	public Rectangle bound;
	
	
	
	
	
	
	public QuadNode(int level,Rectangle bound) {
		this.level=level;
		elements=new ArrayList<>();
		this.bound=bound;
	}
	
	
	
	
	
	public void clear() {
		elements.clear();
		if(childNode!=null) {
			for(int i=0;i<4;i++) {
				childNode[i].clear();
				childNode[i]=null;
			}
		}
	}
	
	
	
	
	public void subdivide() {
		int subWidth=(int) (bound.getWidth()/2);
		int subHeight=(int)(bound.getHeight()/2);
		int x=(int)bound.getX();
		int y=(int)bound.getY();
		
		childNode=new QuadNode[4];
		
		childNode[0]=new QuadNode(level+1, new Rectangle(x, y, subWidth, subHeight));
		childNode[1]=new QuadNode(level+1, new Rectangle(x+subWidth, y, subWidth, subHeight));
		childNode[2]=new QuadNode(level+1, new Rectangle(x, y+subHeight, subWidth, subHeight));
		childNode[3]=new QuadNode(level+1, new Rectangle(x+subWidth, y+subHeight, subWidth, subHeight));
		
		for(BaseElement e:elements) {
			for(int i=0;i<4;i++) {
				if(e.getRectangle().intersects(childNode[i].bound)) {
					childNode[i].elements.add(e);
				}
			}
		}
	}
	
	
	
	
	public int getIndex(BaseElement elements) {
		int index=-1;
		double verticalMidpoint = bound.getX() + (bound.getWidth() / 2);
		double horizontalMidpoint = bound.getY() + (bound.getHeight() / 2);

		
		Rectangle ele=elements.getRectangle();
		
		// 物体完全位于上面两个节点所在区域
		boolean topQuadrant = (ele.getY() < horizontalMidpoint && ele.getY() + ele.getHeight() < horizontalMidpoint);
		// 物体完全位于下面两个节点所在区域
		boolean bottomQuadrant = (ele.getY() > horizontalMidpoint);

		// 物体完全位于左面两个节点所在区域
		if (ele.getX() < verticalMidpoint && ele.getX() + ele.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1; // 处于左上节点
			} else if (bottomQuadrant) {
				index = 2; // 处于左下节点
			}
		}
		// 物体完全位于右面两个节点所在区域
		else if (ele.getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0; // 处于右上节点
			} else if (bottomQuadrant) {
				index = 3; // 处于右下节点
			}
		}


		return index;
	}
	
	
	
	
	public void insert(BaseElement element) {
		//已经分裂子节点
		if(childNode[1]!=null) {
			int index = getIndex(element);
			
			if(index!=-1) {
				this.childNode[index].insert(element);
				return;
			}
		}
		//没有分裂子节点
		elements.add(element);
		
		//超出容量但没有分裂
		if(elements.size()>QuadTreeManager.maxChildCount&&level<QuadTreeManager.maxLevel) {
			if(childNode[0]==null) {
				subdivide();
			}
			int i=0;
			while(i<elements.size()) {
				int index=getIndex(elements.get(i));
				if(index!=-1) {
					childNode[index].insert(elements.remove(i++));
				}else {
					i++;
				}
			}
		}
	}
	
	
	
	
	public ArrayList<BaseElement> retrieve(ArrayList<BaseElement> returnObjects, BaseElement pRect) {
		int index = getIndex(pRect);
		if (index != -1 && childNode[0] != null) {
			childNode[index].retrieve(returnObjects, pRect);
		}
		//添加全部的父节点元素
		returnObjects.addAll(elements);

		return returnObjects;
	}

	
	
	
	
}
