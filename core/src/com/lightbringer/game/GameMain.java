package com.lightbringer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lightbringer.game.start.StartScreen;

public class GameMain extends ApplicationAdapter {
	
	StartScreen startScreen;
	
	
	@Override
	public void create () {
		startScreen = new StartScreen();
		startScreen.show();
	}

	@Override
	public void render () {
		startScreen.render(Gdx.graphics.getDeltaTime());
	}
}
