package robatortas.code.files.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import robatortas.code.files.entity.Entity;
import robatortas.code.files.entity.mob.Player;

public class GameLevel extends Level {
	
	public static Player player;
	
	public GameLevel(String path) {
		super(path);
	}
	
	@SuppressWarnings("unchecked")
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(GameLevel.class.getResource(path));
			int w =WIDTH=image.getWidth();
			int h =HEIGHT=image.getHeight();
			tiles = new int[w*h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		entitiesInTiles = new ArrayList[WIDTH*HEIGHT];
		for(int i=0;i<WIDTH*HEIGHT;i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
		}
		
		player = new Player(5<<3,5<<3, key);
		add(player);
	}
}
