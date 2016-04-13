package com.lightbringer.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lightbringer.game.start.StartScreen;

public class GameMain extends Game {
	
	StartScreen startScreen;
	public static final int toStart = 0, toWorld = 1, end = 2;
	
	@Override
	public void create () {
		startScreen = new StartScreen(this);
		startScreen.show();
		setScreen(startScreen);
	}

	@Override
	public void render () {
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	public void switchState(int state){
		switch(state){
		case toStart:
			setScreen(startScreen);
			break;
		case toWorld:
			
			break;
		case end:
			break;
		default:
			break;
		}
	}
}
