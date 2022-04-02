package robatortas.code.files.render;

public class Sprite {

	public int x, y;
	public int SIZE;
	public SpriteSheet sheet;
	public int[] pixels;
	
	public static Sprite grass = new Sprite(8, 0, 0, SpriteSheet.nature);
	public static Sprite rock = new Sprite(8, 1, 0, SpriteSheet.nature);
	
	public static Sprite nullSprite = new Sprite(8, 9, 9, SpriteSheet.nature);
	
	public Sprite(int SIZE, int x, int y, SpriteSheet sheet) {
		this.SIZE = SIZE;
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x+y*SIZE] = sheet.pixels[(x+this.x)+(y+this.y)*sheet.SIZE];
			}
		}
	}
}
