package robatortas.code.files.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean f, space;
	
	public void tick() {
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		
		f = keys[KeyEvent.VK_F];
		space = keys[KeyEvent.VK_SPACE];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}
	
	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
