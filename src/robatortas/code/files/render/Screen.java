package robatortas.code.files.render;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import robatortas.code.files.entity.mob.Mob;
import robatortas.code.files.level.Tile;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	
	public int xOffset, yOffset;
	
	public int tileSize = 64;
	
	public int[] tiles = new int[tileSize*tileSize];
	
	public Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		map();
	}
	
	public void map() {
		for(int i = 0; i < tileSize*tileSize; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderPixel(int xOffset, int yOffset) {
		for(int y = 0; y < height; y++) {
			int yy = y+yOffset;
			if(yy < 0 || yy >= height) continue;
			for(int x = 0; x < width; x++) {
				int xx = x+xOffset;
				if(xx < 0 || xx >= width) continue;
				int i = ((xx >> 3) & 7) + ((yy >> 3) & 7) * 8;
				pixels[x+y*width] = tiles[i];
			}
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for(int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = tile.sprite.pixels[x+y*tile.sprite.SIZE];
				pixels[xa+ya*width] = color;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			if(flip == 2 || flip == 3) ya=sprite.SIZE-y+yp;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if(flip == 1 || flip == 3) xa=sprite.SIZE-x+xp;
				if(xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[x+y*sprite.SIZE];
				if(color != 0xffff00ff)pixels[xa+ya*width] = sprite.pixels[x+y*sprite.SIZE];
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y=0;y<mob.getSprite().SIZE;y++) {
			int ya = y + yp;
			if(flip == 2 || flip == 3) ya=sprite.SIZE-y+yp;
			for(int x=0;x<mob.getSprite().SIZE;x++) {
				int xa = x + xp;
				if(flip == 1 || flip == 3) xa=sprite.SIZE-x+xp;
				if(xa<-mob.getSprite().SIZE||xa>=width||ya<0||ya>=height) break;
				if(xa < 0) xa = 0;
				int color = mob.getSprite().pixels[x+y*mob.getSprite().SIZE];
				if(color != 0xffff00ff) {
					pixels[xa+ya*width] = color;
					if(mob.hurtTime > 0) {
						pixels[xa+ya*width] = 0xffffffff;
					}
				}
			}
		}
	}
	
	public void setOffsets(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}