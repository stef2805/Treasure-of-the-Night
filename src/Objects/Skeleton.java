package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Game;

import java.awt.*;

public class Skeleton extends GameObject
{
    private Animation SkeletonWalking = null;
    private boolean alive = true;
    private int facing;
    private float oldX;
    private int coveredDistance;
    private int initX, initY;


    Texture tex = Game.getInstance();

    public Skeleton(float x, float y, int covDist, ObjectID id)
    {
        super(x,y-32,id);
        SkeletonWalking = new Animation(7,tex.skeleton_Walking[1],tex.skeleton_Walking[2],tex.skeleton_Walking[3],tex.skeleton_Walking[4],tex.skeleton_Walking[5],tex.skeleton_Walking[6]);
        facing = 1;
        coveredDistance = covDist;
        initX = (int)x;
        initY = (int)y;
        velX = 2;
        velY = 0;
    }
    @Override
    public void tick()
    {
        if((int)x-initX > coveredDistance / 2)
        {
            velX = -1;
            facing = -1;
        }
        if(initX - (int)x > coveredDistance / 2)
        {
            velX = 1;
            facing = 1;
        }
        x += velX;
        SkeletonWalking.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        if(facing==1)
            SkeletonWalking.drawAnimation(g,(int)x,(int)y,64,96);
        else
            SkeletonWalking.drawAnimation(g,(int)x+96,(int)y,-64,96);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x+10,(int)y,32,64);
    }

}
