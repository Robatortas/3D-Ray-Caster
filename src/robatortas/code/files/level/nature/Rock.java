package robatortas.code.files.level.nature;

import robatortas.code.files.entity.mob.Mob;
import robatortas.code.files.level.Level;
import robatortas.code.files.level.Tile;
import robatortas.code.files.render.Screen;
import robatortas.code.files.render.Sprite;

public class Rock extends Tile {

	public Rock(Sprite sprite, int id) {
		super(sprite, id);
	}
	
	public void render(int x, int y, Screen screen, Level level) {
		screen.renderTile(x << 3, y << 3, this);
	}
	
	public boolean solid(Level level, int xt, int yt, Mob mob) {
		return true;
	}
}
