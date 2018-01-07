package BaseCanvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import Element.BaseElement;
import Manager.BaseElementManager;
/**
 * 顶视图画板，内建相机为顶视图（从上往下看）
 * @author AN
 *
 */
public class TopViewCanvas extends BaseCanvas{

	









	








	/**
	 * 
	 */
	private static final long 										serialVersionUID = 1L;
	private Point														cameraPosition;//相机位置
	
	private BaseElementManager								floorElementManager;
	
	
	
	
	
	public Point getCameraPosition() {
		return cameraPosition;
	}
	public void setCameraPosition(Point cameraPosition) {
		if(cameraPosition==null)
			return;
		this.cameraPosition = cameraPosition;
	}
	
	
	
	
	
	private void initialize() {
	}
	
	
	
	
	
	public TopViewCanvas(JFrame context,int width, int height) {
		super(context,width, height);
		
		// TODO Auto-generated constructor stub
	}
	public TopViewCanvas(JFrame context,Dimension size) {
		super(context,size);
		// TODO Auto-generated constructor stub
		this.cameraPosition=new Point(0, 0);
	}
	
	
	
	/*
	 * 相机控制
	 */
	public void cameraMoveTo(int x,int y) {
		if(x<0||y<0) {
			return;
		}
		if(x+this.getWidth()>this.getBackgroundImage().getWidth()&&y+this.getHeight()>this.getBackgroundImage().getHeight()) {
			return;
		}
		this.cameraPosition.setLocation(x, y);
	}
	
	
	
	
	
	@Override
	public void paint(Graphics g) {
		// 绘制背景
		BufferedImage bufferedImage =(BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		Graphics2D g2d=null;
		if(getBackgroundImage()!=null) {
			g2d=(Graphics2D) bufferedImage.getGraphics();
			g2d.drawImage(getBackgroundImage(), 0, 0, null);
		}else {
			g2d=(Graphics2D) bufferedImage.getGraphics();
			g2d.setColor(getDefaultColor());
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		//绘画元素
		if(getElementManagers()!=null&&getElementManagers().size()>0) {
			for(BaseElementManager manager:getElementManagers()) {
				for(BaseElement element:manager.getElements()) {
					//地面停留检测
					
					//元素碰撞
					if(true) {
						//================================================
					}
					//填充缓冲帧
					g2d.drawImage(element.getImage(), element.x, element.y, null);
				}
			}
		}
		//销毁画笔
		g2d.dispose();
		//填充画布
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
