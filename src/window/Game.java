package window;

import Objects.Block;
import Objects.Player;
import Objects.Coin;
import framework.KeyInput;
import framework.ObjectID;
import framework.Texture;
import Objects.Flag;


import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
    public static int WIDTH;
    public static int HEIGHT;

    Handler handler;
    Camera cam;
    public static Texture tex;

    public BufferedImage level1 = null;

    private BufferedImage padure = null;
    Random rand = new Random();
    private boolean running = false;
    private Thread thread;

    public static int LEVEL = 1;

    private void init()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage("/hartalvl1-2.png");
        padure = loader.loadImage("/fundal.png");       //background

        cam = new Camera(0,0);
        WIDTH = getWidth();
        HEIGHT = getHeight();
        handler = new Handler(cam);
        tex = new Texture();

        handler.LoadImageLevel(level1);

        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start()
    {
        if(running)
        {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public void run()
    {
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick()
    {

        for(int i=0;i<handler.object.size();i++)
        {
            if(handler.object.get(i).getID()==ObjectID.Player)
            {
                cam.tick(handler.object.get(i));
            }
        }
        handler.tick();
    }
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        /////////////////////////////
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight() );

        g2d.translate(cam.getX(), cam.getY());    //begin of cam

        g.drawImage(padure,-400,0,WIDTH*3,HEIGHT,null);
        g.drawImage(padure,-400+2*WIDTH*3,0,-WIDTH*3,HEIGHT,null);

        handler.render(g);

        g2d.translate(-cam.getX(), -cam.getY());    //end of cam
        ////////////////////////////
        g.dispose();
        bs.show();
    }

    public static void main(String[] args)
    {
        new Window(800,600, "Treasure of the Night", new Game());
    }


    public static Texture getInstance()
    {
        return tex;
    }
}
