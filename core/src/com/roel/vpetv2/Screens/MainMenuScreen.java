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
 * Created by roel on 4/5/17.
 */

public class MainMenuScreen implements Screen {


    final VirtualPet game;
    OrthographicCamera camera;
    Texture start;

    private Stage stage;
    private Table table;
    private Skin skin;

    NativePlatform nativep;
    private Preferences SharedPref;


    public MainMenuScreen(final VirtualPet game, NativePlatform np)
    {
        nativep = np;
        this.game = game;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(.7f,.7f);

        stage = new Stage();
        table = new Table();

        table.setWidth(stage.getWidth());
        table.align(Align.center);
        table.setPosition(0,Gdx.graphics.getHeight()/1.5f);
        SharedPref = Gdx.app.getPreferences("General");
        String Cont=" ";
        if(!SharedPref.getBoolean("ContinueGame"))
            Cont = "New Game";
        else
            Cont = "Continue";

        TextButton button = new TextButton(Cont,skin,"default");
        button.setColor(Color.CYAN);
        TextButton setting = new TextButton("Settings",skin,"default");
        button.setColor(Color.CYAN);


        Label WelcomeLabel = new Label("Welcome To VPet!", skin);

        WelcomeLabel.setAlignment(Align.center);
        WelcomeLabel.setPosition(0,Gdx.graphics.getHeight()-200);
        WelcomeLabel.setColor(Color.BLACK);


        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!SharedPref.getBoolean("ContinueGame"))
                    game.setScreen(new NewGameScreen(game,nativep));
                else
                {
                    if(updateStatus())
                    {
                        game.setScreen(new DeathScreen(game,nativep));
                    }
                    else
                        game.setScreen(new GameScreen(game));
                }
            }
        });

        table.add(WelcomeLabel).padBottom(stage.getHeight()/4);
        table.row();
        table.add(button);
        table.row();
        table.add(setting).pad(200,0,0,0);


        stage.addActor(table);


        start = new Texture("back.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


    }
    public boolean updateStatus()
    {
        // UPDATE THE STATUS OF Hunger BAR HERE AFTER
        SharedPref = Gdx.app.getPreferences("General");
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");

            System.out.println("Last Time login time: "+ SharedPref.getString("LastTime"));
            Date previous = format.parse(SharedPref.getString("LastTime"));

            Date curDate = new Date();
            String time = format.format(curDate);
            Date current = format.parse(time);
            long diff = current.getTime() - previous.getTime();
            long MinutesPassed= TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println ("Minutes: " + MinutesPassed);

            SharedPref = Gdx.app.getPreferences("Status");

            float tempHunger= SharedPref.getFloat("HungerStatus");
            System.out.println("TempHunger: "+ tempHunger);
            float secs= MinutesPassed*60;
            System.out.print("Seconds out: "+secs);
            float hungerDeletion = (secs/10) * 0.1f;
            System.out.println("Hunger Deletion: "+ hungerDeletion);
            tempHunger -= hungerDeletion;
            System.out.println("TempHunger after update: "+ tempHunger);
            float tempHealth = SharedPref.getFloat("HealthStatus");

            if(tempHunger<0)
            {
                float ab=Math.abs(tempHunger);
                System.out.println("absolute value of hunger negative: "+ ab);
                ab = (ab/0.1f)*10;      //ab is converted to seconds
                System.out.println("Seconds being converted from temp hunger abs value:"+ab);
                ab = (ab/10) * 0.5f;    // health subtraction
                tempHealth -= ab;
                System.out.println("Health: "+tempHealth);
                if(tempHealth<=0)        //if Pet died during offline
                    return true;
                tempHunger=0;
            }

            SharedPref.putFloat("HealthStatus",tempHealth);
            SharedPref.putFloat("HungerStatus",tempHunger);
            SharedPref.flush();


        } catch(ParseException e){
            e.printStackTrace();
        }
        return false;
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
        start.dispose();
        stage.dispose();
        game.dispose();
        skin.dispose();
    }
}
