package AcitonListener;

import Element.BaseElement;
import Enum.MouseState;

public interface MouseActionListener {
	public void mouseAction(BaseElement element,int mouseKey,MouseState state);
	
	public void mouseWheelMove(BaseElement element,int value);

}
