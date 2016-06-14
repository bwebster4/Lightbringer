package com.lightbringer.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WorldUI {
	
	private Stage stage;

	private Skin skin;
	private Label loading;
	private int loadCount = 0;

	public WorldUI(){
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
//		worldUI.setViewport(viewport);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Table table = new Table();
		table.setFillParent(true);
		
		loading = new Label("Loading.", skin);
		table.add(loading).center();
		stage.addActor(table);
	}
	
	public void render(int state){
		switch(state){
		case WorldScreen.LOAD_STATE:
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
			
			stage.act();
			stage.draw();
			break;
		case WorldScreen.PLAY_STATE:
			
			stage.act();
			stage.draw();
		
			break;
		}
	}
	
	public void clear(){
		stage.clear();
	}
	
	public void dispose(){
		stage.dispose();
	}
}
