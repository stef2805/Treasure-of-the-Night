package framework;

import java.awt.*;

public abstract class GameObject
{
    protected ObjectID id;
    protected float x,y;
    protected float velX = 0;
    protected float velY = 0;

    protected boolean falling = true;

    public boolean isFalling()
    {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    protected boolean jumping = false;

    public GameObject(float x, float y, ObjectID id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public float getX(){return x;}
    public float getY(){return y;}
    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}

    public float getVelX(){return velX;}
    public float getVelY(){return velY;}
    public void setVelX(float x){this.velX = x;}
    public void setVelY(float y){this.velY = y;}

    public ObjectID getID(){return this.id;}

}
