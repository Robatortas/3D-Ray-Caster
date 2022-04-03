package robatortas.code.files.entity.mob;

import java.util.Random;

import robatortas.code.files.input.KeyInput;
import robatortas.code.files.render.Screen;

public class Ray {

	public double x0, y0, x1, y1;
	private double rad = 0.0174533;
	private double angle = 0;
	private double pi = 3.14159265359;
	
	private double dx=0, dy=0;
	
	private KeyInput input;
	
	private Random random = new Random();
	
	public Ray(double x0, double y0, double x1, double y1, KeyInput input) {
		this.input = input;
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	int rValue = random.nextInt(30);
	
	public void tick() {
		
		if(input.right) {
			angle += 0.1;
			if(angle < 0) angle += 2*pi;
			dx=Math.cos(angle)*5;
			dy=Math.sin(angle)*5;
			x1-=dx;
			y1-=dy;
		}
		if(input.left) {
			angle -= 0.1;
			if(angle < 0) angle += 2*pi;
			dx=Math.cos(angle)*5;
			dy=Math.sin(angle)*5;
			x1+=dx;
			y1+=dy;
		}
	}
	
	public void render(Screen screen) {
		
		renderRay(x0, y0, x1, y1, screen);
	}
	
	public void renderRay(double x0, double y0, double x1, double y1, Screen screen) {
		int dx = (int) (x1 - x0);
		int dy = (int) (y1 - y0);
				
		for (int x = (int) x0; x < x1; x++){
			int y = (int) (y0 + dy * (x - x0) / dx);
			
			if(x < 0 || y < 0 || x >= screen.width || y >= screen.height) continue;
			
				screen.pixels[x+y*screen.width] = 0xff00ff;
		}
	}
}
