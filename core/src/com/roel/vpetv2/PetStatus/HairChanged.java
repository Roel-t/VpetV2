package com.roel.vpetv2.PetStatus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.roel.vpetv2.NativePlatform;
import com.roel.vpetv2.Screens.GameScreen;
import com.roel.vpetv2.Screens.WardrobeScreen;
import com.roel.vpetv2.VirtualPet;

/**
 * Created by Steph on 4/22/2017.
 */

public class HairChanged extends Actor
{
    final VirtualPet game;
    private Texture hair;
    public int hairNum;

    public HairChanged(int hairNum, final VirtualPet gam, Stage stage, Button HairOpt)
    {
        int screenwidth = Gdx.graphics.getWidth();
        int screenheight = Gdx.graphics.getHeight();
        //Button newHair;
        this.game = gam;
        if (hairNum == 0) {
            Button Hair = null;
            NativePlatform nativep = null;
            game.setScreen(new GameScreen(game, nativep, Hair));
        }
        else if (hairNum == 1)
        {
            Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/Hair1.png"));
            TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
            TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
            Button newHair = new Button(hairTexRegionDrawable);
            newHair.setPosition((screenwidth/2) - (HairOpt.getWidth()/2), ((screenheight/5)+ ((int) HairOpt.getHeight()))+((3*newHair.getHeight())/4));

            stage.addActor(newHair);
        }
        else if (hairNum == 2)
        {
            Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair2.png"));
            TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
            TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
            Button newHair = new Button(hairTexRegionDrawable);
            newHair.setPosition((screenwidth/2) - (HairOpt.getWidth()/2), ((screenheight/5)+ ((int) HairOpt.getHeight()))+((3*newHair.getHeight())/4));

            stage.addActor(newHair);
        }
        else if (hairNum == 3)
        {
            Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair3.png"));
            TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
            TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
            Button newHair = new Button(hairTexRegionDrawable);
            newHair.setPosition((screenwidth/2) - (HairOpt.getWidth()/2), ((screenheight/5)+ ((int) HairOpt.getHeight()))+((3*newHair.getHeight())/4));

            stage.addActor(newHair);
        }
        else if (hairNum == 4)
        {
            Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair4.png"));
            TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
            TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
            Button newHair = new Button(hairTexRegionDrawable);
            newHair.setPosition((screenwidth/2) - (HairOpt.getWidth()/2), ((screenheight/5)+ ((int) HairOpt.getHeight()))+((3*newHair.getHeight())/4));

            stage.addActor(newHair);
        }
        else if (hairNum == 5)
        {
            Texture hairTexture = new Texture(Gdx.files.internal("wardrobe/hair5.png"));
            TextureRegion hairTextureRegion = new TextureRegion(hairTexture);
            TextureRegionDrawable hairTexRegionDrawable = new TextureRegionDrawable(hairTextureRegion);
            Button newHair = new Button(hairTexRegionDrawable);
            newHair.setPosition((screenwidth/2) - (HairOpt.getWidth()/2), ((screenheight/5)+ ((int) HairOpt.getHeight()))+((3*newHair.getHeight())/4));

            stage.addActor(newHair);
        }

    }
}