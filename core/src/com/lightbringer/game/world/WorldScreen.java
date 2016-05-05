package com.lightbringer.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldScreen implements Screen {

	private ElementManager elemManager;
	
	String worldName;
	
	private OrthographicCamera camera;
	private Rectangle camArea;
	private SpriteBatch batch;
	
	private Stage worldUI;
	private World world;
	
	private Skin skin;
	private Label loading;
	
	static final int LOAD_STATE = 0, PLAY_STATE = 1;
	private int state = 0;

	public WorldScreen(String worldName){
		this.worldName = worldName;
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0, 0), true);
		
		elemManager = new ElementManager();
		elemManager.show(world, this);
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camArea = new Rectangle(camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
		
		Viewport viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		
		worldUI = new Stage();
		Gdx.input.setInputProcessor(worldUI);
		//worldUI.setViewport(viewport);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Table table = new Table();
		table.setFillParent(true);
		
		loading = new Label("Loading.", skin);
		table.add(loading).center();
		worldUI.addActor(table);
		
	}

	private int loadCount = 0;
	
	@Override
	public void render(float delta) {

		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		switch(state){
		case LOAD_STATE:
				if(loadCount == 30){
				if(loading.textEquals("Loading."))
					loading.setText("Loading..");
				else if(loading.textEquals("Loading.."))
					loading.setText("Loading...");
				else if(loading.textEquals("Loading..."))
					loading.setText("Loading.");
				loadCount = 0;
			}else{
				loadCount++;
			}
			
			worldUI.act();
			worldUI.draw();
			break;
		case PLAY_STATE:
			updateCamArea();
			
			elemManager.render(camArea, batch);
			worldUI.draw();
			
			worldUI.act();
			world.step(1/60f, 6, 2);
			break;
		}

		batch.end();
	}

	private void updateCamArea(){		
		camArea.x = camera.position.x;
		camArea.y = camera.position.y;
		camArea.height = camera.viewportHeight;
		camArea.width = camera.viewportWidth;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		worldUI.dispose();
		world.dispose();
		elemManager.dispose();
		batch.dispose();
	}

}
