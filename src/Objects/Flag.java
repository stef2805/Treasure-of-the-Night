package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Game;

import java.awt.*;

public class Flag extends GameObject {
    Texture tex = Game.getInstance();
    private int type;
    public Flag(float x, float y,ObjectID id)
    {
        super(x,y,id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
