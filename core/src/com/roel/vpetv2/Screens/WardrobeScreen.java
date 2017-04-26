package com.roel.vpetv2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.roel.vpetv2.VirtualPet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steph on 4/22/2017.
 */

public class WardrobeScreen implements Screen
{
    final VirtualPet game;
    OrthographicCamera camera;
    private int screenwidth = Gdx.graphics.getWidth();
    private int screenheight = Gdx.graphics.getHeight();
    private Texture background;
    private Stage stage;
    //private Button changeHair;
    //private Button changeClothes;
    public WardrobeScreen(final VirtualPet gam)
    {

        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("wardrobe/clipboard.png");

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Texture changeHairTexture = new Texture(Gdx.files.internal("wardrobe/hair-icon.png"));
        TextureRegion changeHairTextureRegion = new TextureRegion(changeHairTexture);
        TextureRegionDrawable changeHairTexRegionDrawable = new TextureRegionDrawable(changeHairTextureRegion);
        Button changeHair = new Button(changeHairTexRegionDrawable);
        changeHair.setPosition(screenwidth / 2 - ((int) changeHair.getWidth()), screenheight / 2 + ((int) changeHair.getHeight()));
        changeHair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChangeHairScreen(game));
            }
        });
        stage.addActor(changeHair);

        Texture changeClothesTexture = new Texture(Gdx.files.internal("wardrobe/clothes-icon.png"));
        TextureRegion changeClothesTextureRegion = new TextureRegion(changeClothesTexture);
        TextureRegionDrawable changeClothesTexRegionDrawable = new TextureRegionDrawable(changeClothesTextureRegion);
        Button changeClothes = new Button(changeClothesTexRegionDrawable);
        changeClothes.setPosition(screenwidth/2-((int)changeHair.getWidth()),(screenheight/2 -(int)changeHair.getHeight())-275);
        changeClothes.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                game.setScreen(new WardrobeScreen(game));   //working on this
            }
        });
        stage.addActor(changeClothes);

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
        game.batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

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
        background.dispose();
        game.dispose();
        stage.dispose();

    }


}
