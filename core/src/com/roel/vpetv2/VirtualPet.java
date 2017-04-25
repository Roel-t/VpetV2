package com.roel.vpetv2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VirtualPet extends Game {
	public SpriteBatch batch;
	public BitmapFont font;


	NativePlatform nplatform;

	public VirtualPet(NativePlatform nplatform)
	{
		this.nplatform = nplatform;
	}


	@Override
	public void create () {


		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("testfont.fnt"),false);

		this.setScreen(new com.roel.vpetv2.Screens.MainMenuScreen(this,nplatform));  //STATUSES GET UPDATED HERE
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();


	}
}
