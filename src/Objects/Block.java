package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Game;

import java.awt.*;

public class Block extends GameObject {
    Texture tex = Game.getInstance();
    private String type;
    public Block(float x, float y, String type ,ObjectID id)            //type are rolul de a determina ce tip de bloc e
    {                                                                   //avem o singura clasa de block pt ca au comportament exact la fel, difera doar texutra
        super(x,y,id);
        this.type = type;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g)                                          //desenez din texture, imaginea nu este salvata decat o singura data
    {
        if(type.compareTo("dirt")==0) //dirt
        {
            g.drawImage(tex.block[0],(int)x,(int)y,null);
        }
        if(type.compareTo("grass")==0) //grass
        {
            g.drawImage(tex.block[1],(int)x,(int)y,null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
