package com.lightbringer.game.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.lightbringer.game.GameMain;

public class StartScreen implements Screen{
	
	GameMain gameMain;
	
	SpriteBatch batch;
	Skin skin;
	Stage stage;
	
	
	public StartScreen(GameMain gameMain){
		this.gameMain = gameMain;
	}
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		table.center().left();
		
		TextButton start, quit;
		start = new TextButton("Start", skin);
		start.addAction(new LoadWorldAction(start));
		table.add(start).size(100, 50).pad(20).row();
		
		quit = new TextButton("Quit", skin);
		quit.addAction(new QuitAction(quit));
		table.add(quit).size(100, 50).pad(20);
		
		stage.addActor(table);
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	private class LoadWorldAction extends Action {

		TextButton button;
		
		public LoadWorldAction(TextButton button){
			this.button = button;
		}
		
		@Override
		public boolean act(float delta) {
			if(button.isChecked()){
				gameMain.switchState(GameMain.toWorld);
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
				gameMain.switchState(GameMain.end);
			}
			return false;
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		
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
		
	}

}
