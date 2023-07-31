package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Game;

import java.awt.*;

public class Bat extends GameObject
{
    private Animation BatFlying = null;
    private boolean alive = true;
    private int facing;
    private float oldX;


    Texture tex = Game.getInstance();

    public Bat(float x, float y, ObjectID id)
    {
        super(x,y,id);
        BatFlying = new Animation(5,tex.batFlying[3],tex.batFlying[4],tex.batFlying[5],tex.batFlying[6],tex.batFlying[7]);
        facing = 1;
    }
    @Override
    public void tick()
    {
        if(oldX>x)
            facing = -1;
        else if(oldX<x) facing = 1;

        oldX = x;

        BatFlying.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        if(facing==1)
            BatFlying.drawAnimation(g,(int)x,(int)y,64,64);
        else
            BatFlying.drawAnimation(g,(int)x+64,(int)y,-64,64);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x-256,(int)y-256,512,512);
    }

}
