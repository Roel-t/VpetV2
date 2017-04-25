package com.roel.vpetv2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.VirtualPet;

/**
 * Created by roel on 4/21/17.
 */

public class DeathScreen implements Screen {

    final VirtualPet game;
    OrthographicCamera camera;
    Texture back;

    Stage stage;


    private Preferences SharedPref;
    NativePlatform nativep;

    public DeathScreen(final VirtualPet game, NativePlatform np)
    {
        this.game = game;
        nativep = np;


        SharedPref = Gdx.app.getPreferences("General");
        SharedPref.putBoolean("ContinueGame",false);
        SharedPref.flush();

        stage = new Stage();
        //  table = new Table();




//        Label nameLabel = new Label("Name:", skin); //look online how to use skin and set table they are in my reading list
//        TextField nameText = new TextField("", skin);
//        Label addressLabel = new Label("Address:", skin);
//        TextField addressText = new TextField("", skin);

        back = new Texture("back.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "YOU DED",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        game.batch.draw(back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        game.batch.end();

        if(Gdx.input.isTouched())
        {
            game.setScreen(new MainMenuScreen(game,nativep));
        }
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
        back.dispose();
        stage.dispose();
        game.dispose();
    }
}
