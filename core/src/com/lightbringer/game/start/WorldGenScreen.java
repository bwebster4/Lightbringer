package com.lightbringer.game.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lightbringer.game.GameMain;
import com.lightbringer.game.world.MapLoc;

public class WorldGenScreen implements Screen{

	GameMain gameMain;
	
	Stage stage;
	Skin skin;
	SpriteBatch batch;
	
	static int small_world = 128;
	
	Thread genThread;
	
	int state;
	private static int create = 0, load = 1, view = 2;
	
	private int loadCount = 0;
	Label loading;
	
	public WorldGenScreen(GameMain gameMain){
		this.gameMain = gameMain;
	}
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Table table = new Table();
		table.setFillParent(true);
		table.center().left();
		
		TextButton generate, quit;
		generate = new TextButton("Start", skin);
		generate.addAction(new CreateWorldAction(generate));
		table.add(generate).size(100, 50).pad(20).row();
		
		quit = new TextButton("Quit", skin);
		quit.addAction(new QuitAction(quit));
		table.add(quit).size(100, 50).pad(20);
		
		stage.addActor(table);
		
		state = create;
	}

	@Override
	public void render(float delta) {
		
		if (state == load){
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
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		skin.dispose();
		
	}
	
	private void createWorld(int size){
		genThread = new Thread(new WorldGen(size));
		genThread.start();
		
		state = load;
		
		stage.clear();
		
		Table table = new Table();
		table.setFillParent(true);
		
		loading = new Label("Loading.", skin);
		table.add(loading).center();
		stage.addActor(table);
	}

	private class WorldGen implements Runnable{

		MapLoc locs[][];
		int size;
		
		public WorldGen(int size){
			this.size = size;
		}
		
		@Override
		public void run() {
//			locs = new MapLoc[size][size];
//			for (int x = 0; x < size; x++){
//				for(int y = 0; y < size; y++){
//					locs[x][y] = new MapLoc(x, y);
//					locs[x][y].addElement(element);
//				}
//			}
		}
		
		
	}
	
	private class CreateWorldAction extends Action {

		TextButton button;
		
		public CreateWorldAction(TextButton button){
			this.button = button;
		}
		
		@Override
		public boolean act(float delta) {
			if(button.isChecked()){
				createWorld(small_world);				
			}
			return false;
		}
		
	}
	private class QuitAction extends Action {

		TextButton button;
		
		public QuitAction(TextButton button){
			this.button = button;
		}
		
		@Override
		public boolean act(float delta) {
			if(button.isChecked()){
				gameMain.switchState(GameMain.toStart);
			}
			return false;
		}
		
	}
	
}
