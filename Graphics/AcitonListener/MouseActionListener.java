package AcitonListener;

import Enum.MouseState;

public interface MouseActionListener {
	public void mouseAction(int mouseKeyi,MouseState state);
	
	public void mouseWheelMove(int value);

}
