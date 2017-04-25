package com.roel.vpetv2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.PetStatus.HealthStatus;
import com.roel.vpetv2.PetStatus.HungerStatus;
import com.roel.vpetv2.PetCharacter;
import com.roel.vpetv2.VirtualPet;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by roel on 4/5/17.
 */

public class GameScreen implements Screen {
    public static long startTime = System.currentTimeMillis();

    final VirtualPet game;

    private Preferences mSharedPreferences; //working on this

    OrthographicCamera camera;
    private int screenwidth = Gdx.graphics.getWidth();
    private int screenheight = Gdx.graphics.getHeight();

    private Stage stage;

    private boolean eat; //WORK IN PROGESS ANIMATION NEEDS TWEAK

    private Table table;

    HungerStatus Hstat = new HungerStatus();
    HealthStatus HEstat = new HealthStatus();
    Skin skin;

    PetCharacter pt = new PetCharacter();
    private String name;
    private Texture background;
    private NativePlatform nativep;

    public long elapsedtime;

    public GameScreen (final VirtualPet gam, NativePlatform np)
    {
        this.game = gam;
        nativep = np;
        mSharedPreferences = Gdx.app.getPreferences("General");
        name = mSharedPreferences.getString("Name");                //Get Pet Name

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


        Texture myTexture = new Texture(Gdx.files.internal("food/chcken.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        Button b1 = new Button(myTexRegionDrawable);
        b1.setPosition(screenwidth/5-((int)b1.getWidth()),screenheight-(int)b1.getHeight()*.9f);
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               eat = true; //WORK in PROGRESS
                Hstat.foodStat+=10;
                System.out.println(Hstat.foodStat);
            }
        });

        background = new Texture("back.png");

        stage.addActor(pt);
        stage.addActor(Hstat);
        stage.addActor(HEstat);
        stage.addActor(b1);


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

        elapsedtime = (System.currentTimeMillis() - startTime)/1000;

        if(elapsedtime>10)
        {
            startTime=System.currentTimeMillis();
            Hstat.updateStats();
            if(HEstat.updateStats(Hstat.foodStat))
                game.setScreen(new DeathScreen(game,nativep));  //Pet Died


        }

        game.batch.begin();
        game.batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();


        stage.act(Gdx.graphics.getDeltaTime());//
        stage.draw();//

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
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

    }
}
