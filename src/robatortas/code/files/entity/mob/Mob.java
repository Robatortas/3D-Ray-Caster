package robatortas.code.files.entity.mob;

import java.util.Random;

import robatortas.code.files.entity.Entity;
import robatortas.code.files.render.Screen;

public class Mob extends Entity {
	
	public int health = 10;
	
	public int xKnockback, yKnockback;
	
	public boolean walking = false;
	public int animate = 0;
	
	public int hurtTime = 0;
	
	public int dir=2;
	public int xa, ya;
	
	public Random random = new Random();
	
	public int mobTicks = 0;
	
	public void move(int xa, int ya) {
		this.xa = xa;
		this.ya = ya;
		if(xa!=0&&ya!=0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if(xa>0)dir=1;
		if(ya>0)dir=2;
		if(xa<0)dir=3;
		if(ya<0)dir=0;
		
		move2(xa, ya);
	}
	
	public boolean move2(int xa, int ya) {
		if(!collision(xa, ya)) {
			x+=xa;
			y+=ya;
		}
		return false;
	}
	
	public void getHurt(Mob mob, int damage, int attackDir) {
		doHurt(random.nextInt(2)+1, attackDir);
	}
	
	public void doHurt(int damage, int attackDir) {
		if(hurtTime > 0) return;
		
		health-=damage;
		
		if(attackDir == 0) yKnockback = -4;
		if(attackDir == 1) xKnockback = 4;
		if(attackDir == 2) yKnockback = 4;
		if(attackDir == 3) xKnockback = -4;
		hurtTime = 10;
	}
	
	public void tick() {
		mobTicks++;
		
		if(hurtTime > 0) hurtTime--;
		
		if(xKnockback > 0) {
			move(1, 0);
			dir = 3;
			xKnockback--;
		}
		if(xKnockback < 0) {
			move(-1, 0);
			dir = 1;
			xKnockback++;
		}
		if(yKnockback > 0) {
			move(0, 1);
			dir = 0;
			yKnockback--;
		}
		if(yKnockback < 0) {
			move(0, -1);
			dir = 2;
			yKnockback++;
		}
		
		borders();
	}
	
	public void borders() {
		if((x+5)>>3 < 0) x++;
		if((y+5)>>3 < 0) y++;
		if((x-5)>>3 > level.WIDTH) x--;
		if((y-5)>>3 > level.HEIGHT) y--;
	}
	
	public void render(Screen screen) {
		
	}
	
	public boolean collision(int xa, int ya) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			//x + xa is the position of the player in x, same goes to y + ya but for y;
			int xt = ((x + xa) + c % 2 * -1 + 8) >> 3;
			int yt = ((y + ya) + c / 2 * -1 + 14) >> 3;
		if(level.getTile(xt, yt).solid(level, xt, yt, this)) solid = true;
		}
		return solid;
	}
	
}
