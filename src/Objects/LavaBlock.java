package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Game;

import java.awt.*;
import java.util.Objects;

public class LavaBlock extends GameObject {
    Texture tex = Game.getInstance();
    private String type;        //"static" or "animated"
    private Animation lavaMoving;
    public LavaBlock(float x, float y,String type,ObjectID id)
    {
        super(x,y,id);
        this.type = type;
        if(type.compareTo("animated")==0)
            lavaMoving = new Animation(5,tex.lavaBlock[0],tex.lavaBlock[1],tex.lavaBlock[2]);
        else if (type.compareTo("static")!=0)
        {
            System.out.println("parametru la lavaBlock dat prost");
        }
    }

    @Override
    public void tick()
    {
        if(type == "animated")
            lavaMoving.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        if(type.equals("animated"))
            lavaMoving.drawAnimation(g,(int)x,(int)y,32,32);
        else
            g.drawImage(tex.lavaBlock[4],(int)x,(int)y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
