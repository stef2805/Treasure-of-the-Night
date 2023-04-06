package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Game;

import java.awt.*;

public class Block extends GameObject {
    Texture tex = Game.getInstance();
    private int type;
    public Block(float x, float y, int type ,ObjectID id)
    {
        super(x,y,id);
        this.type = type;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g)
    {
        if(type==0) //dirt
        {
            g.drawImage(tex.block[0],(int)x,(int)y,null);
        }
        if(type==1) //grass
        {
            g.drawImage(tex.block[1],(int)x,(int)y,null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
