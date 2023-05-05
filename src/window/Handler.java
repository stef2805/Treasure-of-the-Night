package window;

import DBoperate.Checkpoint;
import DBoperate.DBoperator;
import Objects.Block;
import Objects.Bat;
import Objects.Coin;
import Objects.Flag;
import Objects.Player;
import Objects.LavaBlock;
import framework.GameObject;
import framework.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class Handler
{
    public int reqCoindPerLVL  = 2;
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private Camera cam;
    public BufferedImage level1 = null;
    public BufferedImage level2 = null;
    private Connection c = null;
    static public DBoperator DBop = null;                 //Realizeaza conexiunea cu baza de date. Are nevoie pt a incarca harta.

    public Handler(Camera cam)
    {
        this.cam = cam;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GAME_SAVED_DATA.db");
            DBop = new DBoperator(c);
        } catch ( Exception e ) {
            System.out.println("Eroare la stabilirea conexiunii cu baza de date");
        }
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

    public void LoadImageLevel(BufferedImage image)
    {
        Checkpoint checkpoint = DBop.getCheckpoint();
        if(checkpoint != null)
            Player.collectedCoins = checkpoint.colectedCoins;
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
                    if(checkpoint == null)
                        addObject(new Player(xx*32,yy*32, this, cam ,ObjectID.Player));
                    else
                    {
                        addObject(new Player(checkpoint.X, checkpoint.Y,this, cam, ObjectID.Player));
                    }
                }
                if(red==50 && green==150 && blue ==50)
                {
                    addObject(new Block(xx*32,yy*32, 1,ObjectID.Block));
                }
                if(red>=200 && green>=200 && blue <=10)
                {
                    if(checkpoint == null)
                        addObject(new Coin(xx*32,yy*32,ObjectID.Coin));
                    else
                    {
                        if(checkpoint.colectedCoins  > 0)
                        {
                            checkpoint.colectedCoins --;
                        }
                        else
                            addObject(new Coin(xx*32,yy*32,ObjectID.Coin));
                    }
                }
                if(red>=220 && green<=10 && blue <=10)
                {
                    addObject(new LavaBlock(xx*32,yy*32,"animated",ObjectID.LavaBlock));
                }
                if(red==250 && green==100 && blue ==100)
                {
                    addObject(new LavaBlock(xx*32,yy*32,"static",ObjectID.LavaBlock));
                }
                if(red==0 && green==255 && blue ==255)
                {
                    addObject(new Flag(xx*32,yy*32,ObjectID.Flag));
                }
                if(red==200 && green==100 && blue ==0)
                {
                    addObject(new Bat(xx*32,yy*32,ObjectID.Bat));
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
        Checkpoint checkpoint = DBop.getCheckpoint();
        if(checkpoint!=null)
        {
            Game.LEVEL = checkpoint.lvl;
            Player.collectedCoins = checkpoint.colectedCoins;
        }
        clearLevel();
        cam.setX(0);
        Game.Xbg = -400;
        BufferedImageLoader loader = new BufferedImageLoader();
        switch(Game.LEVEL)
        {
            case 0:
                level1 = loader.loadImage("/hartalvl1-2.png");
                LoadImageLevel(level1);
                break;
            case 1:
                level2 = loader.loadImage("/hartalvl2.png");
                LoadImageLevel(level2);
                break;
        }
        DBop.deleteCheckpoint();
    }

}
