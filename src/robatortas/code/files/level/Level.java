package robatortas.code.files.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import robatortas.code.files.entity.Entity;
import robatortas.code.files.entity.mob.Player;
import robatortas.code.files.input.KeyInput;
import robatortas.code.files.render.Screen;

public class Level {

	public int WIDTH, HEIGHT;
	
	public Tile tile;
	
	public static KeyInput key = new KeyInput();
	
	public int[] tiles;
	
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Entity>[] entitiesInTiles;
	
	public Random random = new Random();
	
	public static Level level = new GameLevel("/textures/level/level.png");
	
	private Comparator<Entity> entitySpriteSorter = new Comparator<Entity>() {
		public int compare(Entity e0, Entity e1) {
			if(e1.y < e0.y) return +1;
			if(e1.y > e0.y) return -1;
			return 0;
		}
	};
	
	public Player player;
	
	@SuppressWarnings("unchecked")
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void loadLevel(String path) {
		
	}
	
	protected void generateLevel() {
		
	}

	public void tick() {
		for (int i = 0; i < WIDTH * HEIGHT / 50; i++) {
			int xt = random.nextInt(WIDTH);
			int yt = random.nextInt(WIDTH);
			getTile(xt, yt).tick(this, xt, yt);
		}
		
		GameLevel.key.tick();
		
		for(int i=0;i<entities.size();i++) {
			Entity e = entities.get(i);
			int xto = e.x >> 3;
			int yto = e.y >> 3;
			
			e.tick();
			
			if(e.removed) {
				entities.remove(i--);
				removeEntity(xto, yto, e);
			} else {
				int xt = e.x >> 3;
				int yt = e.y >> 3;
				
				if(xto!=xt||yto!=yt||xto==xt||yto==yt) {
					removeEntity(xto,yto,e);
					insertEntity(xt,yt,e);
				}
			}
		}
	}

	private List<Entity> entityRowSprites = new ArrayList<Entity>();
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffsets(xScroll, yScroll);
		int x0 = xScroll >> 3;
		int x1 = (xScroll+screen.width+16) >> 3;
		int y0 = yScroll >> 3;
		int y1 = (yScroll+screen.height+16) >> 3;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= this.WIDTH || y >= this.HEIGHT) continue;
				getTile(x, y).render(x, y, screen, this);
			}
		}
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x<0||y<0||x>=WIDTH||y>=HEIGHT) continue;
				entityRowSprites.addAll(entitiesInTiles[x+y*WIDTH]);
				
				if(entityRowSprites.size()>0) {
					sortAndRenderEntities(entityRowSprites, screen);
				}
				entityRowSprites.clear();
			}
		}
	}
	
	public void sortAndRenderEntities(List<Entity> list, Screen screen) {
		Collections.sort(list, entitySpriteSorter);
		for(int i=0;i<list.size();i++) {
			list.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		if(e instanceof Player) {
			player = (Player)e;
		}
		e.removed = false;
		entities.add(e);
		e.init(this);
		insertEntity(e.x,e.y,e);
	}
	
	public void remove(Entity e) {
		entities.remove(e);
		e.removed = true;
		e.remove();
		int xto = e.x >> 3;
		int yto = e.y >> 3;
		removeEntity(xto, yto, e);
	}
	
	public void insertEntity(int x, int y, Entity e) {
		if(x<0||y<0||x>=WIDTH||y>=HEIGHT) return;
		entitiesInTiles[x+y*WIDTH].add(e);
	}
	
	public void removeEntity(int x, int y, Entity e) {
		if(x<0||y<0||x>=WIDTH||y>=HEIGHT) return;
		entitiesInTiles[x+y*WIDTH].remove(e);
	}
	
	public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
		List<Entity> result = new ArrayList<Entity>();
		int xt0 = (x0 >> 3) - 1;
		int yt0 = (y0 >> 3) - 1;
		int xt1 = (x1 >> 3) + 1;
		int yt1 = (y1 >> 3) + 1;
		
		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) continue;
				List<Entity> entities = entitiesInTiles[x+y*WIDTH];
				for(int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if(e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= this.WIDTH || y >= this.HEIGHT) return Tile.nullTile;
		if(tiles[x+y*WIDTH] == 0xff3CC15B) return Tile.grass;
		if(tiles[x+y*WIDTH] == 0xffADADAD) return Tile.rock;
		return Tile.nullTile;
	}
}