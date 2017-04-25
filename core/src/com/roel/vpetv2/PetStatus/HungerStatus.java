package com.roel.vpetv2.PetStatus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.roel.vpetv2.Screens.MainMenuScreen;

/**
 * Created by roel on 4/18/17.
 */

public class HungerStatus extends Actor {

    public float foodStat;
    private Texture hunger;
    private Preferences update;

    public HungerStatus()
    {
        update = Gdx.app.getPreferences("Status");
        foodStat = update.getFloat("HungerStatus");


        System.out.println("FoodStat: "+foodStat);
        if(foodStat>75)
            hunger = new Texture("hungerSprites/h1_redo.png");
        else if(foodStat>50)
            hunger = new Texture(("hungerSprites/h2.png"));
        else if(foodStat>25)
            hunger = new Texture(("hungerSprites/h3.png"));
        else
            hunger = new Texture(("hungerSprites/h4.png"));
    }
    public void updateStats()
    {
        foodStat -= 0.1;
        if(foodStat<0)
            foodStat=0;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {

        int hungery = (Gdx.graphics.getHeight()/2)+(hunger.getHeight()*7);
        int hungerx = (Gdx.graphics.getWidth()/3)+(hunger.getWidth()*5);
        batch.draw(hunger,hungerx,hungery,Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/10);

    }

    @Override
    public void act(float delta) {

        if(foodStat>75)
            hunger = new Texture(("hungerSprites/h1_redo.png"));
        else if(foodStat>50)
            hunger = new Texture(("hungerSprites/h2.png"));
        else if(foodStat>25)
            hunger = new Texture(("hungerSprites/h3.png"));
        else
            hunger = new Texture(("hungerSprites/h4.png"));
        super.act(delta);
    }

}
