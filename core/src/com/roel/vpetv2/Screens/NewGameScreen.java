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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.VirtualPet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by roel on 4/21/17.
 */

public class NewGameScreen implements Screen{

    final VirtualPet game;
    OrthographicCamera camera;

    Texture start,PetTemp;

    Stage stage;
    Table table;
    Skin skin;

    NativePlatform nativep;

    private Preferences SharedPref;


    public NewGameScreen(final VirtualPet game,NativePlatform np)
    {
        this.game = game;
        nativep = np;

        SharedPref = Gdx.app.getPreferences("Status");      //Start the Hunger status at 100
        SharedPref.putFloat("HungerStatus",6);
        SharedPref.putFloat("HealthStatus",40);
        SharedPref.flush();


        SharedPref = Gdx.app.getPreferences("General");
        SharedPref.putBoolean("ContinueGame",true);        //So we skip this screen after creating a new game
        SharedPref.putBoolean("FirstTime",true);


        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");
        String time = format.format(curDate);
        SharedPref.putString("DaysSurvived",time);
        SharedPref.flush();



        stage = new Stage();
        table = new Table();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(.5f,.5f);



        table.setWidth(stage.getWidth());
        table.align(Align.center);
        table.setPosition(0,Gdx.graphics.getHeight()/3);


        Label nameLabel = new Label("Pet Name:", skin);
        final TextField nameText = new TextField("", skin);



        nameText.setColor(Color.WHITE);


        TextButton button = new TextButton("Continue",skin,"default");
        button.setColor(Color.CYAN);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(nameText.getText().equals("") || nameText.getText().equals(" ") || nameText.getText().length() > 6)
                {
                    if(nameText.getText().length() > 6)
                        nativep.showWarning(false);

                    else
                        nativep.showWarning(true);

                }
                else    // GO TO GAME SCREEN AND CHANGE THE VALUE OF THE RESET GAME IN SHARED PREFERENCES
                {
                    String name = nameText.getText();
                    SharedPref.putString("Name",name);
                    SharedPref.flush();
                    Button Hair = null;
                    game.setScreen(new GameScreen(game,nativep, Hair));
                }
            }
        });

        table.add(nameLabel);
        table.add(nameText).width(stage.getWidth()/2);
        table.row();
        table.add(button).pad(stage.getHeight()/5,0,0,0).width(400).height(200).colspan((int)table.getWidth());
        table.row();

        stage.addActor(table);




        PetTemp = new Texture("idle/OG.png");
        start = new Texture("solidGreen.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

        game.batch.draw(start,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.draw(PetTemp,Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getHeight()/1.7f,PetTemp.getWidth()/1.3f,PetTemp.getHeight()/1.3f);

        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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
        start.dispose();
        PetTemp.dispose();
        stage.dispose();
        game.dispose();
        skin.dispose();




    }
}
