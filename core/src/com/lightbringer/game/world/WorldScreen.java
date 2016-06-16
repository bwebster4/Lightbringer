package com.lightbringer.game.world;

import box2dLight.Light;
import box2dLight.RayHandler;

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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lightbringer.game.world.entities.ElementManager;

public class WorldScreen implements Screen {

	private ElementManager elemManager;
	
	String worldName;
	
	float WIDTH, HEIGHT;
	int worldSize;
	
	private OrthographicCamera camera;
	private Rectangle camArea;
	
	private SpriteBatch batch;
	
	private InputHandler input;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private RayHandler rayHandler;

	WorldUI worldUI;
	
	public static final int LOAD_STATE = 0, PLAY_STATE = 1;
	private int state = 0;

	public WorldScreen(String worldName){
		this.worldName = worldName;
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		worldSize = 64;
		
		input = new InputHandler(WIDTH, HEIGHT, this);
		
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0f, 0f,0f, 1f);
		rayHandler.setBlurNum(3);
		rayHandler.setShadows(true);
		
		elemManager = new ElementManager();
		elemManager.show(world, worldSize, rayHandler, this, input);
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(1f, 1f * HEIGHT / WIDTH);
		camera.setToOrtho(false, 30f, 30f * (float) HEIGHT / WIDTH);
		camera.update();
		camArea = new Rectangle(camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
		Gdx.app.log("WS", "CamArea: " + camArea.x + " " + camArea.y + " " + camArea.width + " " + camArea.height);
		
//		Viewport viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
//		viewport.setCamera(camera);
		
		worldUI = new WorldUI();
		
	}

	
	@Override
	public void render(float delta) {

		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		
		switch(state){
		case LOAD_STATE:

			break;
		case PLAY_STATE:
			updateCam(delta);
			
			batch.begin();

			elemManager.render(camArea, batch);
			
			batch.end();
			
			rayHandler.setCombinedMatrix(camera);
			rayHandler.updateAndRender();
			
//			debugRenderer.render(world, camera.combined);
			
			input.update();
			elemManager.update(delta);
			world.step(1/60f, 6, 2);

			break;
		}
		
		worldUI.render(state);

	}
	
	private void updateCam(float delta){
		
		camera.position.set(elemManager.getPlayerPos(), 0);
		
		camera.update();
		camArea.x = camera.position.x - camera.viewportWidth / 2;
		camArea.y = camera.position.y - camera.viewportHeight / 2;
		camArea.height = camera.viewportHeight;
		camArea.width = camera.viewportWidth;
		
		if(camArea.x < -0.5 ){
			camera.position.x = camera.viewportWidth / 2 - 0.5f;
			camera.update();
			camArea.x = camera.position.x - camera.viewportWidth / 2;
		}else if(camArea.x + camArea.width > worldSize - 0.5){
			camera.position.x = worldSize - camArea.width / 2 - 0.5f;
			camera.update();
			camArea.x = camera.position.x - camera.viewportWidth / 2;
		}
		
		if(camArea.y < -0.5 ){
			camera.position.y = camera.viewportHeight / 2 - 0.5f;
			camera.update();
			camArea.y = camera.position.y - camera.viewportHeight / 2;
		}else if(camArea.y + camArea.height > worldSize - 0.5){
			camera.position.y = worldSize - camArea.height / 2 - 0.5f;
			camera.update();
			camArea.y = camera.position.y - camera.viewportHeight / 2;
		}
		
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

	Vector3 vec3 = new Vector3();
	public Vector2 unproject(Vector2 vec){
		vec3.set(vec, 0);
		vec3 = camera.unproject(vec3);
		return new Vector2(vec3.x, vec3.y);
	}
	public Vector2 unproject(float x, float y){
		vec3.set(x, y, 0);
		vec3 = camera.unproject(vec3);
		return new Vector2(vec3.x, vec3.y);
	}
	
	public void setState(int state) {
		if(this.state == WorldScreen.LOAD_STATE && state == PLAY_STATE){
			worldUI.clear();
		}
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
