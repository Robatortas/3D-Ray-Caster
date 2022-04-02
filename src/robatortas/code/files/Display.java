package robatortas.code.files;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	
	public Display(int WIDTH, int HEIGHT, Game game) {
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setSize(size);
		game.frame.setResizable(true);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
		
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}