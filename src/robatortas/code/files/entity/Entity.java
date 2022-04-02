package robatortas.code.files.entity;

import robatortas.code.files.entity.mob.Mob;
import robatortas.code.files.level.Level;
import robatortas.code.files.render.Screen;
import robatortas.code.files.render.Sprite;

public class Entity {
	
	public int x,y;
	public Level level;
	public Sprite sprite;
	
	public int xp = 8;
	public int yp = 8;
	
	public boolean removed = false;
	
	public Entity(int x, int y, Sprite sprite) {
		this.x=x;
		this.y=y;
		this.sprite=sprite;
	}
	
	public Entity() {
	}
	
	public void tick() {
		
	}
	
	public void render(Screen screen) {
	}
	
	protected void move(int xa, int ya) {
		
	}
	
	public void getHurt(Mob mob, int damage, int attackDir) {
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void remove() {
		removed=true;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void init(Level level) {
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return (x+xp<x0||y+yp<y0||x-yp>x1||y-yp>y1);
	}
}
