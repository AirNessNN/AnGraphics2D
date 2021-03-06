package Graphics;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * ˮƽ���壬�Դ�ˮƽ���
 * @author AN
 *
 */
public class HorizontalCanvas extends BaseCanvas{

	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//����
	private boolean													landingCheck;//��ײ����״̬
	
	
	
	
	
	
	
	
	
	
	//���Զ�д
	public boolean isLandingCheck() {
		return landingCheck;
	}
	public void setLandingCheck(boolean isHitOpen) {
		this.landingCheck = isHitOpen;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public HorizontalCanvas(JFrame context,Dimension size) {
		super(context,size);
		// TODO Auto-generated constructor stub
	}
	public HorizontalCanvas(JFrame context,int width, int height) {
		super(context,width, height);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		//super.update(g);
		
		
		
		
		
		//�滭ͼ��
		//repaint();
		paint(g);
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		//�Զ����õ���ͣ��
		/*if(autoSetElementOnFloor) {
			if(element instanceof GravityElement) {
				GravityElement ge=(GravityElement) element;
				ge.setOnFloor(false);
			}	
		}*/
		
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
			
			for(BaseElement element : getTotalElements()) {
				//�߿���ײ
				if(isIsborderHit()) {
					if(element.x<0) {
						borderhitListener.onHit(element,BorderHitlState.LEFT);
					}
					if(element.x+element.getWidth()>this.getWidth()) {
						borderhitListener.onHit(element,BorderHitlState.RIGHT);
					}
					if(element.y<0) {
						borderhitListener.onHit(element,BorderHitlState.TOP);
					}
					if(element.y+element.getHeight()>this.getHeight()) {
						borderhitListener.onHit(element,BorderHitlState.BOTTOM);
					}
					//�߿�Ԫ����ײ
					for(BaseElement e:getWall()){
						if(element.CheckHitTo(e)) {
							if(elementHitListener!=null) {
								elementHitListener.onHit(element, e);
							}
						}
					}
				}
				//Ԫ����ײ
				if(true) {
					//================================================
				}
				//��仺��֡
				g2d.drawImage(element.getImage(), element.x, element.y, null);
			}
		}
		//���ٻ���
		g2d.dispose();
		//��仭��
		g.drawImage(bufferedImage, 0, 0, null);
	}
	
	
	
}
