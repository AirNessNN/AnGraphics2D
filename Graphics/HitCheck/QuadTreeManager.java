package HitCheck;

import java.awt.Rectangle;
import java.util.ArrayList;
import Element.BaseElement;

public class QuadTreeManager {
	
	public static int maxLevel=5;
	public static int maxChildCount=20;
	
	private QuadNode rootNode;
	private Rectangle rootRectangle;
	
	
	
	
	
	
	
	
	
	private void build(QuadNode node) {
		if(node.elements.size()>maxChildCount) {
			node.subdivide();
			for(QuadNode n:node.childNode) {
				build(n);
			}
		}
	}
	
	
	
	
	
	
	public QuadTreeManager(Rectangle rectangle,ArrayList<BaseElement> elements) {
		this.rootRectangle=rectangle;
		this.rootNode=new QuadNode(0, rootRectangle);
		rootNode.elements=elements;
	}
	
	
	
	
	
	public void buildTree() {
		build(rootNode);
	}
	
	
	
	
	public void insert(BaseElement element) {
		
	}
	
	
	
	
	public void clear() {
		rootNode.clear();
	}
	
	
	

}
