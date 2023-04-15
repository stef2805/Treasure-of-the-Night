package window;

import Objects.Block;
import Objects.Coin;
import Objects.Flag;
import Objects.Player;
import window.Camera;
import framework.GameObject;
import framework.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Object.*;

public class Handler
{
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private Camera cam;
    public BufferedImage level2 = null;

    public Handler(Camera cam)
    {
        this.cam = cam;
    }

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
    public void LoadImageLevel(BufferedImage image)
    {
        if(image==null)
        {
            System.out.println("imagine vida");
        }
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx=0;xx<h;xx++)
        {
            for(int yy=0;yy<w;yy++)
            {
                int pixel = image.getRGB(xx,yy);
                int red = (pixel>>16) & 0xff;
                int green = (pixel>>8) & 0xff;;
                int blue = (pixel) & 0xff;;

                if(red<=10 && green<=10 && blue <=10)
                {
                    addObject(new Block(xx*32,yy*32,0,ObjectID.Block));
                }
                if(red<=10 && green<=10 && blue >=200)
                {
                    addObject(new Player(xx*32,yy*32, this, cam ,ObjectID.Player));
                }
                if(red==50 && green==150 && blue ==50)
                {
                    addObject(new Block(xx*32,yy*32, 1,ObjectID.Block));
                }
                if(red>=200 && green>=200 && blue <=10)
                {
                    addObject(new Coin(xx*32,yy*32,ObjectID.Coin));
                }
                if(red>=220 && green<=10 && blue <=10)
                {
                    addObject(new Flag(xx*32,yy*32,ObjectID.Flag));
                }

            }
        }
    }
    private void clearLevel()
    {
        object.clear();
    }

    public void switchLevel()
    {
         clearLevel();
         cam.setX(0);
         switch(Game.LEVEL)
         {
             case 1:
                 BufferedImageLoader loader = new BufferedImageLoader();
                 level2 = loader.loadImage("/hartalvl2.png");
                 LoadImageLevel(level2);
                 Game.LEVEL ++;
                 break;
         }
    }

}
