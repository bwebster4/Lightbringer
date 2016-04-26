package com.lightbringer.game.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldScreen implements Screen {

	ElementManager elemManager;
	
	OrthogonalTiledMapRenderer mapRenderer;
	TiledMap map;
	String worldName;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	public WorldScreen(String worldName){
		this.worldName = worldName;
	}
	
	@Override
	public void show() {
		elemManager = new ElementManager();
		elemManager.show();
		
		map = new TmxMapLoader().load(worldName);
		float unitScale = 1 / 16f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		mapRenderer.setView(camera);
	}
	@Override
	public void render(float delta) {
		mapRenderer.render();
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
