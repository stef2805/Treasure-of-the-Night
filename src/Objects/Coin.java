package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Game;

import java.awt.*;

public class Coin extends GameObject {
    Texture tex = Game.getInstance();
    private Animation coinSpinning;
    public Coin(float x, float y ,ObjectID id)
    {
        super(x,y,id);
        coinSpinning = new Animation(5,tex.coin[0],tex.coin[1],tex.coin[2],tex.coin[3],tex.coin[4],tex.coin[5],tex.coin[6],tex.coin[7],tex.coin[8]);
    }

    @Override
    public void tick()
    {
        coinSpinning.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        coinSpinning.drawAnimation(g,(int)x,(int)y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
