package window;

import framework.GameObject;

public class Camera
{
    float x,y;

    public Camera(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void tick(GameObject player)
    {
        x = -player.getX() + Game.WIDTH/2;
        y = -player.getY() + Game.HEIGHT/3;
    }
}
