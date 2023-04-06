package window;

import Objects.Block;
import framework.GameObject;
import framework.ObjectID;

import java.awt.*;
import java.util.LinkedList;

public class Handler
{
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;

    public void tick()
    {
         for(int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g)
    {
        for(int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject go)
    {
        this.object.add(go);
    }
    public void removeObject(GameObject go)
    {
        this.object.remove(go);
    }


    public void createLevel()
    {
        for(int xx = 0; xx < Game.WIDTH+32; xx += 32)
            addObject(new Block(xx, Game.HEIGHT-32, 0,ObjectID.Block));

        for(int yy = 0; yy < Game.WIDTH+32; yy += 32)
            addObject(new Block(Game.HEIGHT+166, yy, 0,ObjectID.Block));

        for(int zz = 0; zz < Game.WIDTH+32; zz += 32)
            addObject(new Block(Game.HEIGHT-609, zz, 0,ObjectID.Block));

    }

}
