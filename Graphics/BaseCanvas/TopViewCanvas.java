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
 * ����ͼ���壬�ڽ����Ϊ����ͼ���������¿���
 * @author AN
 *
 */
public class TopViewCanvas extends BaseCanvas{

	









	








	/**
	 * 
	 */
	private static final long 										serialVersionUID = 1L;
	private Point														cameraPosition;//���λ��
	
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
	 * �������
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
		// ���Ʊ���
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
		
		//�滭Ԫ��
		if(getElementManagers()!=null&&getElementManagers().size()>0) {
			for(BaseElementManager manager:getElementManagers()) {
				for(BaseElement element:manager.getElements()) {
					//����ͣ�����
					
					//Ԫ����ײ
					if(true) {
						//================================================
					}
					//��仺��֡
					g2d.drawImage(element.getImage(), element.x, element.y, null);
				}
			}
		}
		//���ٻ���
		g2d.dispose();
		//��仭��
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
