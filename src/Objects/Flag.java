package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Game;

import java.awt.*;

public class Flag extends GameObject {
    Texture tex = Game.getInstance();
    private Animation flagMoving = null;
    public Flag(float x, float y,ObjectID id)
    {
        super(x,y,id);
        flagMoving = new Animation(5,tex.flagMoving[0],tex.flagMoving[1],tex.flagMoving[2],tex.flagMoving[3],tex.flagMoving[4]);
    }

    @Override
    public void tick()
    {
        flagMoving.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        flagMoving.drawAnimation(g,(int)x,(int)y-32,2*32,2*32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
