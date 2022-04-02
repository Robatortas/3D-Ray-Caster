package robatortas.code.files;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import robatortas.code.files.entity.mob.Player;
import robatortas.code.files.input.KeyInput;
import robatortas.code.files.level.GameLevel;
import robatortas.code.files.level.Level;
import robatortas.code.files.render.Screen;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 167;
	public static final int SCALE = 3;
	
	public static final String TITLE = "MFGJ";
	
	public Thread thread;
	public boolean running = false;
	
	public JFrame frame = new JFrame();
	
	public static int xScroll;
	public static int yScroll;
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	@SuppressWarnings("unused")
	private Display display;
	private Screen screen;
	
	public KeyInput key;
	
	public Player player;
	
	public static Level level;
//	private Level level = new Level("/textures/level/level.png");
	
	public Game() {
		level = Level.level;
		
		display = new Display(WIDTH*SCALE, HEIGHT*SCALE, this);
		screen = new Screen(WIDTH, HEIGHT);
		
		addKeyListener(Level.key);
	}
	
	public synchronized void start() {
		thread = new Thread(this, "GAME");
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		double ns = 1000000000.0/60.0;
		int frames = 0;
		int ticks = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
				System.out.println("tps: " + ticks + "  || " + "fps: " + frames);
				frame.setTitle(TITLE + "  ||  tps: " + ticks + "  " + "fps: " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	int tickTime = 0;
	public void tick() {
		tickTime++;
		level.tick();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		requestFocus();
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
//			pixels[i] = i + tickTime;
		}
		
		screen.clear();
		
		xScroll = GameLevel.player.x-WIDTH/2+10;
		yScroll = GameLevel.player.y-HEIGHT/2+15;
		level.render(xScroll, yScroll, screen);
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		Font font = new Font("Arial", 1, 1);
		g.setColor(Color.white);
		g.setFont(font.deriveFont(25f));
		g.drawString("X: " + (GameLevel.player.x >> 3) + " Y: " + (GameLevel.player.y>> 3), 10, 20);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}