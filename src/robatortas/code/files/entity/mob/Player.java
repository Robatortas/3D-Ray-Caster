package robatortas.code.files.entity.mob;

import java.util.List;

import robatortas.code.files.Game;
import robatortas.code.files.entity.Entity;
import robatortas.code.files.input.KeyInput;
import robatortas.code.files.render.Screen;
import robatortas.code.files.render.Sprite;
import robatortas.code.files.render.SpriteSheet;

public class Player extends Mob {
	
	public KeyInput key;
	
	public Player(int x, int y, KeyInput key) {
		this.key = key;
		this.x = x;
		this.y = y;
		//sprite = player_down;
	}
	
	public static int velX=1;
	public static int velY=1;
	
	public boolean punch = false;
	public int attackTime;
	
	public int ticks=0;
	
	public void tick() {
		super.tick();
		
		ticks++;
		
		if(animate <= 1000) animate++;
		else animate=0;
		
		int xa = 0, ya = 0;
		if(key.up) ya-=velY;
		if(key.down) ya+=velY;
		if(key.left) xa-=velX;
		if(key.right) xa+=velX;
		
		if(key.f || key.space) {
			if(punch==false) {
				punch = true;
				attack();
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
//		int speed = ticks & 1;
		
		if(xa != 0 || ya != 0) {
			move(xa,ya);
			walking = true;
		} else walking = false;
	}
	
	public void attack() {
		attackTime = 5;
		
		if(dir == 0) hurt(x+8, y-4, x+4, y-8);
		if(dir == 1) hurt(x-8, y+8, x+8, y-8);
		if(dir == 2) hurt(x+8, y+8, x+12, y);
		if(dir == 3) hurt(x, y-8, x-8, y+8);
		
//		System.out.println("X: " + (x+8) + " Y: " + y);
//		System.out.println(level.getEntities(x, y, x, y));
	}
	
	public void hurt(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(e!=this) {
				e.getHurt(this, 1, dir);
			}
		}
	}
	
	public void render(Screen screen) {
		int flip = 0;
		
		if(dir == 0) {
			if(walking) {
				if (animate % 20 > 10) {
					sprite = player_up_walk;
				} else {
					flip = 1;
					sprite = player_up_walk;
				}
			} else {
				if(attackTime < 1) sprite = player_up; 
				if(attackTime > 0) {
					if (animate % 20 > 10) {
						sprite = player_up_punch;
					} else {
						flip = 1;
						sprite = player_up_punch;
					}
				}
			}
		}
		
		if(dir == 1) {
			if(walking) {
				if (animate % 15 > 5) {
					sprite = player_side_walk;
				} else {
					sprite = player_side;
				}
			} else {
				sprite = player_side;
				if(attackTime > 0) {
					sprite = player_side_punch;
				}
			}
		}
		
		if(dir == 2 ) {
			if(walking) {
				if (animate % 20 > 10) {
					sprite = player_down_walk;
				} else {
					flip = 1;
					sprite = player_down_walk;
				}
			} else {
				sprite = player_down;
				if(attackTime > 0) {
					if (animate % 20 > 10) {
						sprite = player_down_punch;
					} else {
						flip = 1;
						sprite = player_down_punch;
					}
				}
			}
		}
		
		if(dir == 3) {
			if(walking) {
				if (animate % 15 > 5) {
					sprite = player_side_walk;
				} else {
					sprite = player_side;
				}
			} else {
				sprite = player_side;
				if(attackTime > 0) {
					sprite = player_side_punch;
				}
			}
		}
		
		if(dir==3) flip = 1;
		
		screen.renderMob(x, y, this, sprite, flip);
		
//		screen.renderRays(x, y);
		
		screen.renderRay(0, 0, x<<3, y<<3);
		
		
		for(int i = 0; i < 40; i++)screen.renderRay(0, 0, x + random.nextInt(20), y);
		
		if (attackTime > 0) {
			if (dir == 0) {
				screen.renderSprite(x + 1, y - 3, punchfx_up, 0);
				screen.renderSprite(x + 7, y - 3, punchfx_up, 1);
			}
			if (dir == 1) {
				screen.renderSprite(x + 12, y, punchfx_side, 0);
				screen.renderSprite(x + 12, y + 7, punchfx_side, 2);
			}
			if (dir == 2) {
				screen.renderSprite(x + 1, y + 8, punchfx_up, 2);
				screen.renderSprite(x + 7, y + 8, punchfx_up, 3);
			}
			if (dir == 3) {
				screen.renderSprite(x - 5, y, punchfx_side, 1);
				screen.renderSprite(x - 5, y + 7, punchfx_side, 3);
			}
		}
	}
	
	public SpriteSheet player = new SpriteSheet("/textures/spritesheets/mob/player/player.png", 64);
	
	public static Sprite player_up = new Sprite(16, 1, 0, SpriteSheet.mob);
	public static Sprite player_up_walk = new Sprite(16, 1, 1, SpriteSheet.mob);
	
	public static Sprite player_down = new Sprite(16, 0, 0, SpriteSheet.mob);
	public static Sprite player_down_walk = new Sprite(16, 0, 1, SpriteSheet.mob);
	
	public static Sprite player_side = new Sprite(16, 2, 0, SpriteSheet.mob);
	public static Sprite player_side_walk = new Sprite(16, 2, 1, SpriteSheet.mob);
	public static Sprite player_side_walk2 = new Sprite(16, 2, 2, SpriteSheet.mob);
	
	public static Sprite player_up_punch = new Sprite(16, 1, 2, SpriteSheet.mob);
	public static Sprite player_down_punch = new Sprite(16, 0, 2, SpriteSheet.mob);
	public static Sprite player_side_punch = new Sprite(16, 2, 2, SpriteSheet.mob);
	
	public static Sprite punchfx_side = new Sprite(8, 7, 7, SpriteSheet.mob);
	public static Sprite punchfx_up = new Sprite(8, 6, 7, SpriteSheet.mob);
}