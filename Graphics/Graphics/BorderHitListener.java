package Graphics;

/**
 * 
 * @author AN
 *
 */
public interface BorderHitListener {
	public static final int TOP=1;
	public static final int LEFT=2;
	public static final int BOTTOM=3;
	public static final int RIGHT=4;
	
	
	
	public void onHit(BaseElement element,BorderHitlState hitMode);

}
