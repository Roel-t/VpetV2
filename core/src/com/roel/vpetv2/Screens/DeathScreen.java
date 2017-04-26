package com.roel.vpetv2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.VirtualPet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by roel on 4/21/17.
 */

public class DeathScreen implements Screen {

    final VirtualPet game;
    OrthographicCamera camera;
    Texture back,dead;

    Stage stage;
    private int Days;


    private Preferences SharedPref;
    NativePlatform nativep;

    public DeathScreen(final VirtualPet game, NativePlatform np)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        this.game = game;
        nativep = np;
        stage = new Stage();


        SharedPref = Gdx.app.getPreferences("General");
        SharedPref.putBoolean("ContinueGame",false);
        SharedPref.flush();

        Days=0;

        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");

            Date previous = format.parse(SharedPref.getString("DaysSurvived"));
            Date curDate = new Date();
            String time = format.format(curDate);
            Date current = format.parse(time);
            long diff = current.getTime() - previous.getTime();
            long HoursPassed= TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            Days = (int)HoursPassed / 24;
        } catch(ParseException e){
            e.printStackTrace();
        }



        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(.7f,.7f);


        TextButton button = new TextButton("Go Back",skin,"default");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game,nativep));
            }
        });
        button.setColor(Color.CYAN);
        button.setPosition(button.getWidth()/10,button.getHeight()/4);
        String temp = SharedPref.getString("Name");

        Label nameLabel = new Label("Oh no "+temp+" Died!", skin);
        String day=" days";
        if(Days==1)
            day= " day";
        Label TimeSurvive = new Label(temp+" Survived "+Long.toString(Days)+day,skin);
        TimeSurvive.setColor(Color.BLACK);
        nameLabel.setColor(Color.BLACK);

        nameLabel.setPosition(Gdx.graphics.getWidth()/7,Gdx.graphics.getHeight()/1.5f);
        TimeSurvive.setPosition(Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/1.2f);

        stage.addActor(button);
        stage.addActor(nameLabel);
        stage.addActor(TimeSurvive);
        dead = new Texture("ded.png");
        back = new Texture("back.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.draw(dead,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/10);
        game.batch.end();
        stage.act(delta);
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
        back.dispose();
        stage.dispose();
        game.dispose();
    }
}
