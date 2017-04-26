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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.PetCharacter;
import com.roel.vpetv2.PetStatus.HairChanged;
import com.roel.vpetv2.VirtualPet;

/**
 * Created by Steph on 4/22/2017.
 */

public class ChangeHairScreen implements Screen
{
    final VirtualPet game;
    OrthographicCamera camera;
    private int screenwidth = Gdx.graphics.getWidth();
    private int screenheight = Gdx.graphics.getHeight();
    private Texture background;
    private Stage stage;
    private Button HairNo;
    private Button Hair1;
    private Button Hair2;
    private Button Hair3;
    private Button Hair4;
    private Button Hair5;
    private Button ConfirmHair;
    private Button newHair = null;
    private int xval = ((screenwidth-10)/3);
    private int yval = screenheight-(screenheight/2);
    public ChangeHairScreen(final VirtualPet gam)
    {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("solidGreen.png");

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Texture noHairTexture = new Texture(Gdx.files.internal("wardrobe/circle.png"));
        TextureRegion noHairTextureRegion = new TextureRegion(noHairTexture);
        TextureRegionDrawable noHairTexRegionDrawable = new TextureRegionDrawable(noHairTextureRegion);
        HairNo = new Button(noHairTexRegionDrawable);
        HairNo.setPosition(HairNo.getWidth(), yval+ ((int) HairNo.getHeight())+(screenheight/4));
        HairNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
                if (newHair != null)
                {
                    newHair.remove();
                }
                newHair = null;
            }
        });
        stage.addActor(HairNo);

        Texture Hair1Texture = new Texture(Gdx.files.internal("wardrobe/Hair1.png"));
        TextureRegion Hair1TextureRegion = new TextureRegion(Hair1Texture);
        TextureRegionDrawable Hair1TexRegionDrawable = new TextureRegionDrawable(Hair1TextureRegion);
        Hair1 = new Button(Hair1TexRegionDrawable);
        Hair1.setSize((2*Hair1.getWidth())/3,(2*Hair1.getHeight())/3);
        Hair1.setPosition(xval, yval+ ((int) HairNo.getHeight())+(screenheight/4));
        Hair1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (newHair != null)
                {
                    newHair.remove();
                }
                Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/Hair1.png"));
                TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
                TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
                newHair = new Button(hairTexRegionDrawable);
                newHair.setPosition((int) ConfirmHair.getX()-60, ConfirmHair.getY()+ 280);

                stage.addActor(newHair);

            }
        });
        stage.addActor(Hair1);

        Texture Hair2Texture = new Texture(Gdx.files.internal("wardrobe/hair2.png"));
        TextureRegion Hair2TextureRegion = new TextureRegion(Hair2Texture);
        TextureRegionDrawable Hair2TexRegionDrawable = new TextureRegionDrawable(Hair2TextureRegion);
        Hair2 = new Button(Hair2TexRegionDrawable);
        Hair2.setSize((2*Hair2.getWidth())/3,(2*Hair2.getHeight())/3);
        Hair2.setPosition(xval*2, yval+ ((int) HairNo.getHeight())+(screenheight/4));
        Hair2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (newHair != null)
                {
                    newHair.remove();
                }
                Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair2.png"));
                TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
                TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
                newHair = new Button(hairTexRegionDrawable);
                newHair.setPosition((int) ConfirmHair.getX()-70, ConfirmHair.getY()+ 300);

                stage.addActor(newHair);
                stage.draw();
            }
        });
        stage.addActor(Hair2);

        Texture Hair3Texture = new Texture(Gdx.files.internal("wardrobe/hair3.png"));
        TextureRegion Hair3TextureRegion = new TextureRegion(Hair3Texture);
        TextureRegionDrawable Hair3TexRegionDrawable = new TextureRegionDrawable(Hair3TextureRegion);
        Hair3 = new Button(Hair3TexRegionDrawable);
        Hair3.setSize((2*Hair3.getWidth())/3,(2*Hair3.getHeight())/3);
        Hair3.setPosition(HairNo.getWidth(), yval+ ((int) HairNo.getHeight()));
        Hair3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (newHair != null)
                {
                    newHair.remove();
                }
                Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair3.png"));
                TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
                TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
                newHair = new Button(hairTexRegionDrawable);
                newHair.setPosition((int) ConfirmHair.getX()-60, ConfirmHair.getY()+ 300);

                stage.addActor(newHair);

            }
        });
        stage.addActor(Hair3);

        Texture Hair4Texture = new Texture(Gdx.files.internal("wardrobe/hair4.png"));
        TextureRegion Hair4TextureRegion = new TextureRegion(Hair4Texture);
        TextureRegionDrawable Hair4TexRegionDrawable = new TextureRegionDrawable(Hair4TextureRegion);
        Hair4 = new Button(Hair4TexRegionDrawable);
        Hair4.setSize((2*Hair4.getWidth())/3,(2*Hair4.getHeight())/3);
        Hair4.setPosition(xval, yval+ ((int) HairNo.getHeight()));
        Hair4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (newHair != null)
                {
                    newHair.remove();
                }
                Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair4.png"));
                TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
                TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
                newHair = new Button(hairTexRegionDrawable);
                newHair.setPosition((int) ConfirmHair.getX()-60, ConfirmHair.getY()+ 300);

                stage.addActor(newHair);

            }
        });
        stage.addActor(Hair4);

        Texture Hair5Texture = new Texture(Gdx.files.internal("wardrobe/hair5.png"));
        TextureRegion Hair5TextureRegion = new TextureRegion(Hair5Texture);
        TextureRegionDrawable Hair5TexRegionDrawable = new TextureRegionDrawable(Hair5TextureRegion);
        Hair5 = new Button(Hair5TexRegionDrawable);
        Hair5.setSize((2*Hair5.getWidth())/3,(2*Hair5.getHeight())/3);
        Hair5.setPosition(xval*2, yval+ ((int) HairNo.getHeight()));
        Hair5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (newHair != null)
                {
                    newHair.remove();
                }
                Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair5.png"));
                TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
                TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
                newHair = new Button(hairTexRegionDrawable);
                newHair.setPosition((int) ConfirmHair.getX()-70, ConfirmHair.getY()+ 110);

                stage.addActor(newHair);

            }
        });
        stage.addActor(Hair5);

        Texture ConfirmHairTexture = new Texture(Gdx.files.internal("idle/OG.png"));
        TextureRegion ConfirmHairTextureRegion = new TextureRegion(ConfirmHairTexture);
        TextureRegionDrawable ConfirmHairTexRegionDrawable = new TextureRegionDrawable(ConfirmHairTextureRegion);
        ConfirmHair = new Button(ConfirmHairTexRegionDrawable);
        ConfirmHair.setPosition((screenwidth/2) - (ConfirmHair.getWidth()/2), (screenheight/5)+ ((int) HairNo.getHeight()));
        ConfirmHair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(newHair != null)
                {
                    newHair.setPosition((screenwidth/3)-70,(screenheight/10)+300);
                    if (newHair.getHeight() > ConfirmHair.getHeight()/2)
                        newHair.setPosition((screenwidth/3)-70,(screenheight/10)+100);
                }
                NativePlatform nativep = null;
                game.setScreen(new GameScreen(game, nativep, newHair));
            }
        });
        stage.addActor(ConfirmHair);
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