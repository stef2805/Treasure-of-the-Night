package window;


import framework.KeyInput;
import framework.ObjectID;
import framework.Texture;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
    static public int GAMEstate = 0; //0 = meniu, 1 = joc nou
    static public int currentChoice = 0;
    public static int WIDTH;
    public static int HEIGHT;
    public static int Xbg = -400;  //coordonata de unde incepe bg

    Handler handler;
    Camera cam;
    public static Texture tex;

    public BufferedImage menuIMG = null;

    private BufferedImage padure = null;
    Random rand = new Random();
    private boolean running = false;
    private Thread thread;

    public static int LEVEL = 0;
    static public String[] options = {"Start again", "Continue", "Quit"};               //optiunile meniului
    private Color titleColor = new Color(250, 250, 250);;
    private Font titleFont = new Font("Century Gothic", Font.BOLD, 40);
    private Font font = new Font("Arial", Font.BOLD, 30);

    private void init()
    {
        BufferedImageLoader loader = new BufferedImageLoader();

        menuIMG = loader.loadImage("/menuBG.png");       //background meniu
        padure = loader.loadImage("/fundal.png");       //background joc

        cam = new Camera(0, 0);                         //camera
        WIDTH = getWidth();
        HEIGHT = getHeight();
        handler = new Handler(cam);
        tex = new Texture();

        handler.switchLevel();                               //initializare nivel 1

        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start()
    {
        if(running)
        {
            return;
        }
        running = true;
        thread = new Thread(this);              //lansare thread nou
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
        while(running)
        {
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
        if(GAMEstate==1)
        {
            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getID() == ObjectID.Player) {
                    cam.tick(handler.object.get(i));
                }
            }
            handler.tick();
        }
    }

    private void render()
    {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        /////////////////////////////
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        //////////Stare meniu///////////
        if (GAMEstate == 0)
        {
            g.drawImage(menuIMG, 0, 0, getWidth() , getHeight(), null);
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.drawString("Treasure of the night", 200, 250);            //titlu

            // draw menu options
            g.setFont(font);
            for(int i = 0; i < options.length; i++) {                           //selectare optiune
                if(i == currentChoice) {
                    g.setColor(Color.RED);
                }
                else {
                    g.setColor(Color.WHITE);
                }
                g.drawString(options[i], 330, 300 + i * 35);
            }
        }
        else
        {
            g2d.translate(cam.getX(), cam.getY());    //begin of cam

            g.drawImage(padure, Xbg, -650, WIDTH * 4, HEIGHT*3, null);
            g.drawImage(padure, Xbg +  WIDTH * 4, -650, WIDTH * 4, HEIGHT*3, null);

            handler.render(g);

            g2d.translate(-cam.getX(), -cam.getY());    //end of cam
        }
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
