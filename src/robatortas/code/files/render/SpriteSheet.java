package robatortas.code.files.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public int[] pixels;
	public final int WIDTH, HEIGHT;
	public final int SIZE;
	public String path;
	
	public static SpriteSheet nature = new SpriteSheet("/textures/spritesheets/nature/nature.png", 80);
	public static SpriteSheet mob = new SpriteSheet("/textures/spritesheets/mob/player/player.png", 64);
	public static SpriteSheet nullSheet = new SpriteSheet("/textures/spritesheets/null_texture.png", 8);
	
	public SpriteSheet(String path, int SIZE) {
		this.path = path;
		this.WIDTH = SIZE;
		this.HEIGHT = SIZE;
		this.SIZE = SIZE;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public void load() {
		try {
			System.out.println("Loading " + path + " ------>");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e)  {
			System.err.println(" error!");
		}
	}
}
