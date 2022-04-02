package robatortas.code.files.level;

import robatortas.code.files.entity.mob.Mob;
import robatortas.code.files.level.nature.GrassTile;
import robatortas.code.files.level.nature.Rock;
import robatortas.code.files.render.Screen;
import robatortas.code.files.render.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile nullTile = new nullTile(Sprite.nullSprite, 0);
	public static Tile grass = new GrassTile(Sprite.grass, 1);
	public static Tile rock = new Rock(Sprite.rock, 2);
	
	public final byte id;
	
	public Tile(Sprite sprite, int id) {
		this.id = (byte) id;
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen, Level level) {
		
	}

	public void tick(Level level, int xt, int yt) {
		
	}

	public boolean solid(Level level, int xt, int yt, Mob mob) {
		return false;
	}
}
