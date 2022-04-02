/**
 * 
 */
package robatortas.code.files.level;

import robatortas.code.files.render.Screen;
import robatortas.code.files.render.Sprite;

public class nullTile extends Tile {
	
	public nullTile(Sprite sprite, int id) {
		super(sprite, id);
	}
	
	public void render(Screen screen, int x, int y) {
		screen.renderTile(x << 3, y << 3, this);
	}
}
