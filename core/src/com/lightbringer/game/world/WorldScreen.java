package com.lightbringer.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldScreen implements Screen {

	ElementManager elemManager;
	
	OrthogonalTiledMapRenderer mapRenderer;
	TiledMap map;
	String worldName;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Stage stage;
	World world;
	
	public WorldScreen(String worldName){
		this.worldName = worldName;
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0, 0), true);
		
		elemManager = new ElementManager();
		elemManager.show(world);
		
		map = new TmxMapLoader().load(worldName);
		float unitScale = 1 / 16f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		mapRenderer.setView(camera);
		
		Viewport viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		stage.setViewport(viewport);
		
//		TiledMapTileLayer blockLayer = (TiledMapTileLayer) map.getLayers().get("Blocks");
//		blockLayer.getCell(1, 1).getTile().getProperties().
	}

	public void update(){
		
	}
	
	@Override
	public void render(float delta) {
		mapRenderer.render();
		
		world.step(1/60f, 6, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		elemManager.dispose();
		batch.dispose();
		mapRenderer.dispose();
	}

}
