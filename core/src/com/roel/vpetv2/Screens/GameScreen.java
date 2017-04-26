package com.roel.vpetv2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.PetStatus.HealthStatus;
import com.roel.vpetv2.PetStatus.HungerStatus;
import com.roel.vpetv2.PetCharacter;
import com.roel.vpetv2.VirtualPet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by roel on 4/5/17.
 */

public class GameScreen implements Screen {
    public static long startTime = System.currentTimeMillis();

    final VirtualPet game;

    private Preferences mSharedPreferences;

    OrthographicCamera camera;
    private int screenwidth = Gdx.graphics.getWidth();
    private int screenheight = Gdx.graphics.getHeight();

    private Stage stage;

    private boolean eat; //WORK IN PROGESS ANIMATION NEEDS TWEAK

    private Table table;

    HungerStatus Hstat;
    HealthStatus HEstat;
    Skin skin;

    PetCharacter pt = new PetCharacter();
    private String name;
    private Texture background,myTexture,wardrobeTexture;
    private NativePlatform nativep;

    private Boolean dead=false;
    public long elapsedtime;

    public GameScreen (final VirtualPet gam, NativePlatform np, final Button Hair)
    {
        this.game = gam;
        mSharedPreferences = Gdx.app.getPreferences("General");
        if(mSharedPreferences.getBoolean("FirstTime"))
        {
            mSharedPreferences.putBoolean("FirstTime",false);
            mSharedPreferences.flush();
        }
        else
        {
            if(updateStatus()) {
                dead = true;
            }
        }
        name = mSharedPreferences.getString("Name");                //Get Pet Name


        Hstat = new HungerStatus();
        HEstat = new HealthStatus();
        nativep = np;


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(.6f,.6f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        table = new Table();


        Label PetName = new Label(name, skin);
        PetName.setColor(Color.BLACK);


        table.setWidth(stage.getWidth());
        table.setPosition(0,Gdx.graphics.getHeight());
        table.align(Align.center);
        table.add(PetName).padTop((skin.getFont("default-font").getSpaceWidth())*4);
        table.row();

        stage.addActor(table);


        myTexture = new Texture(Gdx.files.internal("food/chcken.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        Button b1 = new Button(myTexRegionDrawable);
        b1.setPosition(screenwidth/5-((int)b1.getWidth()),screenheight-(int)b1.getHeight()*.9f);
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eat = true; //WORK in PROGRESS
                Hstat.foodStat+=10;
                if(Hstat.foodStat >100)
                    Hstat.foodStat=100;
                System.out.println(Hstat.foodStat);
            }
        });
        wardrobeTexture = new Texture(Gdx.files.internal("wardrobe/wardrobe.png"));
        TextureRegion wardrobeTextureRegion = new TextureRegion(wardrobeTexture);
        TextureRegionDrawable wardrobeTexRegionDrawable = new TextureRegionDrawable(wardrobeTextureRegion);
        Button b2 = new Button(wardrobeTexRegionDrawable);
        b2.setPosition(screenwidth/5-((int)b1.getWidth()),(screenheight-(int)b1.getHeight())-275);
        b2.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                saveStats();
                game.setScreen(new WardrobeScreen(game));
            }
        });

        background = new Texture("back.png");

        stage.addActor(pt);
        stage.addActor(Hstat);
        stage.addActor(HEstat);
        stage.addActor(b1);
        stage.addActor(b2);
        if (Hair != null) {
            stage.addActor(Hair);
        }


    }

    public boolean updateStatus()
    {
        // UPDATE THE STATUS OF Hunger BAR HERE AFTER
        mSharedPreferences = Gdx.app.getPreferences("General");
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");

            System.out.println("Last Time login time: "+ mSharedPreferences.getString("LastTime"));
            Date previous = format.parse(mSharedPreferences.getString("LastTime"));

            Date curDate = new Date();
            String time = format.format(curDate);
            System.out.println("Current time: "+time);
            Date current = format.parse(time);
            long diff = current.getTime() - previous.getTime();
            long MinutesPassed= TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println ("Minutes: " + MinutesPassed);

            mSharedPreferences = Gdx.app.getPreferences("Status");

            float tempHunger= mSharedPreferences.getFloat("HungerStatus");
            System.out.println("TempHunger: "+ tempHunger);
            float secs= MinutesPassed*60;
            System.out.println("Seconds out: "+secs);
            float hungerDeletion = (secs/10) * 2;        //0.1f
            System.out.println("Hunger Deletion: "+ hungerDeletion);
            tempHunger -= hungerDeletion;
            System.out.println("TempHunger after update: "+ tempHunger);
            mSharedPreferences.putFloat("HungerStatus",tempHunger);
            mSharedPreferences.flush();


            if(tempHunger<0)
            {
                float tempHealth = mSharedPreferences.getFloat("HealthStatus");
                float ab=Math.abs(tempHunger);
                System.out.println("absolute value of hunger negative: "+ ab);
                ab = (ab/2)*10;      //ab is converted to seconds  //0.1f
                System.out.println("Seconds being converted from temp hunger abs value:"+ab);
                ab = (ab/10) * 10;    // health subtraction     //0.5f
                tempHealth -= ab;
                System.out.println("Health: "+tempHealth);
                mSharedPreferences.putFloat("HealthStatus",tempHealth);
                mSharedPreferences.flush();
                if(tempHealth<=0)        //if Pet died during offline
                {
                    System.out.println("Here in if stat");
                    return true;
                }
            }
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


        pt.updateAnimationStatus(Hstat.foodStat,eat);

        if(eat)
            eat=false;
        if(dead)
            game.setScreen(new DeathScreen(game,nativep));  //Pet Died

        elapsedtime = (System.currentTimeMillis() - startTime)/1000;

        if(elapsedtime>2)
        {
            startTime=System.currentTimeMillis();
            Hstat.updateStats();
            if(HEstat.updateStats(Hstat.foodStat))
                game.setScreen(new DeathScreen(game,nativep));  //Pet Died
        }

        game.batch.begin();
        game.batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.font.draw(game.batch,Float.toString(Hstat.foodStat),Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight()/2);
        game.font.draw(game.batch,Float.toString(HEstat.HealthStat),Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight()/3);
        game.batch.end();


        stage.act(Gdx.graphics.getDeltaTime());//
        stage.draw();//


    }

    @Override
    public void resize(int width, int height) {

    }
    public void saveStats()
    {
        mSharedPreferences = Gdx.app.getPreferences("Status");
        mSharedPreferences.putFloat("HungerStatus",Hstat.foodStat);  //Store the current Foodstat
        mSharedPreferences.putFloat("HealthStatus",HEstat.HealthStat);
        mSharedPreferences.flush();

        mSharedPreferences = Gdx.app.getPreferences("General");   //Store the current day
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");
        String time = format.format(curDate);
        mSharedPreferences.putString("LastTime",time);
        mSharedPreferences.flush();
    }

    @Override
    public void pause() {
        saveStats();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        background.dispose();
        game.dispose();
        stage.dispose();
        skin.dispose();
        myTexture.dispose();
        wardrobeTexture.dispose();

    }
}
