package com.lightbringer.game.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lightbringer.game.GameMain;

public class WorldGenScreen implements Screen{

	Stage create, load, view;
	Skin skin;
	SpriteBatch batch;
	
	Thread genThread;
	
	@Override
	public void show() {
		create = new Stage();
		Gdx.input.setInputProcessor(create);

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
		
		create.addActor(table);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		create.dispose();
		
	}
	
	private void createWorld(int size){
		
	}

	private class WorldGen implements Runnable{

		@Override
		public void run() {
			
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
				createWorld();				
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
				//gameMain.switchState(GameMain.end);
			}
			return false;
		}
		
	}
	
}
