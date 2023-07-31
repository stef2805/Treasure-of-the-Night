package window;

import DBoperate.Checkpoint;
import DBoperate.DBoperator;
import Objects.*;
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
    public BufferedImage level3 = null;
    private Connection c = null;
    static public DBoperator DBop = null;                 //Realizeaza conexiunea cu baza de date. Are nevoie pt a incarca harta.

    public Handler(Camera cam)
    {
        this.cam = cam;
        try                                               //se realizeaza conexiunea cu baza de date
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GAME_SAVED_DATA.db");
            DBop = new DBoperator(c);                   //se creaza un obiect operator pentru baza de date
        }
        catch ( Exception e )
        {
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

    public void LoadImageLevel(BufferedImage image) throws EmptyBufferedImageException
    {
        BlockFactory blockFactory = new BlockFactory();                         //creez o fabrica de blocuri
        Checkpoint checkpoint = DBop.getCheckpoint();                           //scot checkpoint-ul salvat din baza de date
        if(checkpoint != null)
            Player.collectedCoins = checkpoint.colectedCoins;                   //daca am checkpoint iau din el numarul de monede colectate
        if(image==null)
        {
            throw new EmptyBufferedImageException();
        }
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx=0;xx<h;xx++)                                            //nivelul este salvat ca o imagine cu pixeli
        {
            for(int yy=0;yy<w;yy++)                                         //in functie de culoare apixelilor imi dau seama de tipul blocului care va fi creat
            {
                int pixel = image.getRGB(xx,yy);
                int red = (pixel>>16) & 0xff;
                int green = (pixel>>8) & 0xff;;
                int blue = (pixel) & 0xff;;

                if(red<=10 && green<=10 && blue <=10)
                {
                    addObject(blockFactory.makeBlock("normal",xx*32,yy*32,"dirt"));
                }
                if(red<=10 && green<=10 && blue >=200)
                {
                    if(checkpoint == null)                              //daca nu am checkpoint salvat desenez jucatorul la coordonata initiala indicata pe harta
                        addObject(new Player(xx*32,yy*32, this, cam ,ObjectID.Player));
                    else                                                //altfel il pun la coordonata salvata
                    {
                        addObject(new Player(checkpoint.X, checkpoint.Y,this, cam, ObjectID.Player));
                    }
                }
                if(red==50 && green==150 && blue ==50)
                {
                    addObject(blockFactory.makeBlock("normal",xx*32,yy*32, "grass"));
                }
                if(red>=200 && green>=200 && blue <=10)
                {
                    if(checkpoint == null)                                              //daca nu este niciun checkpoint in baza de date atunci pun pe harta toate monedele
                        addObject(new Coin(xx*32,yy*32,ObjectID.Coin));
                    else                                                                //altfel pun doar monedele ramase
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
                    addObject(blockFactory.makeBlock("lava",xx*32,yy*32,"animated"));
                }
                if(red==250 && green==100 && blue ==100)
                {
                    addObject(blockFactory.makeBlock("lava",xx*32,yy*32,"static"));
                }
                if(red==0 && green==255 && blue ==255)
                {
                    addObject(new Flag(xx*32,yy*32,ObjectID.Flag));
                }
                if(red==200 && green==100 && blue ==0)
                {
                    addObject(new Bat(xx*32,yy*32,ObjectID.Bat));
                }
                if(red ==110 && green ==15 && blue==103 )
                    addObject(new Skeleton(xx*32,yy*32,200,ObjectID.Skeleton));

            }
        }
    }
    private void clearLevel()
    {
        object.clear();
    }

    public void switchLevel()
    {
        Checkpoint checkpoint = DBop.getCheckpoint();                       //vad daca am checkpoint
        if(checkpoint!=null)                                                //dac am nivelul este cel indicat in checkpoint
        {
            Game.LEVEL = checkpoint.lvl;
            Player.collectedCoins = checkpoint.colectedCoins;
        }
        clearLevel();                                                       //se curata nivelul vechi si se deseneaza cel nou
        cam.setX(0);
        Game.Xbg = -400;
        BufferedImageLoader loader = new BufferedImageLoader();
        switch(Game.LEVEL)
        {
            case 0:
                level1 = loader.loadImage("/hartaLVL1.png");
                try {
                    LoadImageLevel(level1);
                }
                catch(Exception e)
                {
                    System.out.println("Problema a aparut la imaginea nivelului 1");
                }
                break;
            case 1:
                level2 = loader.loadImage("/hartalvl2.png");
                try {
                    LoadImageLevel(level2);
                }
                catch(Exception e)
                {
                    System.out.println("Problema a aparut la imaginea nivelului 2");
                }
                break;
            case 2:
                level3 = loader.loadImage("/hartalvl3.png");
                try {
                    LoadImageLevel(level3);
                }
                catch(Exception e)
                {
                    System.out.println("Problema a aparut la imaginea nivelului 3");
                }
                break;

        }
        DBop.deleteCheckpoint();
    }

}
