import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Element.GravityElement;
import Manager.GravityElementManager;

public class Player extends GravityElement{
	
	public boolean isJumping;

	public Player(int x, int y, int width, int height,GravityElementManager context) {
		super(x, y, width, height,context);
		// TODO Auto-generated constructor stub
		BufferedImage img=new BufferedImage(100, 100,BufferedImage.TYPE_INT_RGB );
		Graphics g=img.createGraphics();
		g.setColor(Color.magenta);
		g.fillOval(0, 0, 100, 100);
		this.setImage(img);
	}

}
