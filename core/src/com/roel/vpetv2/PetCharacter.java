package com.roel.vpetv2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


/**
 * Created by roel on 4/6/17.
 */


public class PetCharacter extends Image{
    private enum Status {
        idle,sad,supersad,eating;
    }
    private Animation animation = null;
    private float stateTime = 0;
    private float width,height;
    private Status status,previousStatus;

    private Texture slime;
    private boolean DoAnimation,reset;
    private TextureAtlas idle;
    private Animation PetAnimation;



    private static int startTime = (int)(System.currentTimeMillis()/1000);

    public PetCharacter() {
        status = status.idle;            //initialize to idle
        previousStatus = status.idle;
        DoAnimation = false;             //Whether animation happens or not
        width= Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();


        slime = new Texture("idle/OG.png");
        idle =new TextureAtlas(Gdx.files.internal("idle/slimeanimation.atlas"));
        PetAnimation = new Animation(1f/8f,idle.getRegions());
        this.animation = PetAnimation;

    }


    public void updateAnimationStatus(float hunger,boolean eat)
    {
            if (hunger > 75 && hunger <= 100) {
                previousStatus = status;
                status = status.idle;
            } else if (hunger > 50 && hunger <= 75) {
                previousStatus = status;
                status = status.sad;
            } else if (hunger <= 50) {
                previousStatus = status;
                status = status.supersad;
            }
    }



    @Override
    public void act(float delta)
    {
        if(!status.equals(previousStatus))
        {
            switch (status)
            {
                case idle:
                    slime = new Texture("idle/OG.png");
                    idle =new TextureAtlas(Gdx.files.internal("idle/slimeanimation.atlas"));
                    PetAnimation = new Animation(1f/8f,idle.getRegions());
                    this.animation = PetAnimation;
                    break;
                case sad:
                    slime = new Texture("sad/sad_idle.png");
                    idle =new TextureAtlas(Gdx.files.internal("sad/sadanimation.atlas"));
                    PetAnimation = new Animation(1f/8f,idle.getRegions());
                    this.animation = PetAnimation;
                    break;
                case supersad:
                    slime = new Texture("ultrasad/ultrasad_idle.png");
                    idle =new TextureAtlas(Gdx.files.internal("ultrasad/ultrasadanimation.atlas"));
                    PetAnimation = new Animation(1f/8f,idle.getRegions());
                    this.animation = PetAnimation;

                    break;
                case eating:            //WORK IN PROGRESS
                    slime = new Texture("eating/eating_idle.png");
                    idle =new TextureAtlas(Gdx.files.internal("eating/eatinganimation.atlas"));
                    PetAnimation = new Animation(1f/8f,idle.getRegions());
                    this.animation = PetAnimation;

                    break;
            }
        }


        if(((int)((System.currentTimeMillis()/1000)-startTime) ==10) )      //Does animation every 5 seconds
        {
            DoAnimation=true;
            startTime=(int)(System.currentTimeMillis()/1000);
        }


        stateTime+=delta;
        super.act(delta);

    }




    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(DoAnimation)
        {
            TextureRegion currentFrame = (TextureRegion) animation.getKeyFrame(stateTime);
            batch.draw((TextureRegion) animation.getKeyFrame(stateTime, true), width / 3, height / 10);
            if (animation.isAnimationFinished(stateTime))
            {
                DoAnimation = false;
                startTime = (int) (System.currentTimeMillis() / 1000);
            }
        }
        else
        {
            batch.draw(slime,width/3,height/10);
            stateTime=0;
        }
    }

    public void reset()
    {
        reset=true;
        stateTime = 0;
    }
}
