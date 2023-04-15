package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.Game;
import window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject
{
    private final float MAX_SPEED = 10;
    private float gravity = 0.3f;
    private float width = 32;
    private float height = 64;
    private int collectedCoins = 0;
    private int MAX_COINS = 2;
    private int lives = 2;
    private boolean alive = true;
    private long deadTime = 0;

    private Handler handler;
    private Camera cam;
    private Animation playerWalk;
    private Animation playerJump;
    private Animation playerDie;
    private int facing;


    Texture tex = Game.getInstance();

    public Player(float x, float y, Handler handler, Camera cam, ObjectID id)
    {
        super(x,y,id);
        this.cam = cam;
        this.handler = handler;
        playerWalk = new Animation(5,tex.player[0],tex.player[1],tex.player[2],tex.player[3],tex.player[4],tex.player[5],tex.player[6],tex.player[7]);
        playerJump = new Animation(5,tex.player_jump[0],tex.player_jump[1],tex.player_jump[2],tex.player_jump[3],tex.player_jump[4],tex.player_jump[5],tex.player_jump[6],tex.player_jump[7]);
        playerDie = new Animation(12,tex.player_die[0],tex.player_die[1],tex.player_die[2],tex.player_die[3],tex.player_die[4],tex.player_die[5],tex.player_die[6],tex.player_die[7]);

    }
    @Override
    public void tick()
    {
        x+=velX;
        y+=velY;

        if(falling || jumping)
        {
            velY +=gravity;
            if(velY>MAX_SPEED)
            {
                velY = MAX_SPEED;
            }
        }
        if(y>1000)
        {
            alive = false;
            die();
        }
        Collision();
        playerWalk.runAnimatin();
        playerJump.runAnimatin();
        playerDie.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        if(!alive)
        {
            velX = 0;
            velY = 0;
            playerDie.drawAnimation(g, (int) x, (int) y, 48, 72);
            if(deadTime==0)
                deadTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();                        //calculate the time needed to draw PLayerDie animation
            if(currentTime - deadTime>1500)
            {
                die();
            }
        }
        else
        {
            if (velX > 0)
                facing = 1;
            if (velX < 0)
                facing = -1;
            if (jumping && facing == 1) {
                playerJump.drawAnimation(g, (int) x, (int) y, 48, 72);
            } else if (jumping && facing == -1) {
                playerJump.drawAnimation(g, (int) x + 32, (int) y, -48, 72);

            } else {
                if (velX > 0) {
                    playerWalk.drawAnimation(g, (int) x, (int) y, 48, 72);
                } else if (velX < 0)
                    playerWalk.drawAnimation(g, (int) x + 32, (int) y, -48, 72);
                else {
                    if (facing == 1)
                        g.drawImage(tex.player[0], (int) x, (int) y, 48, 72, null);
                    else
                        g.drawImage(tex.player[0], (int) x + 32, (int) y, -48, 72, null);
                }

            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x+(int)width/2-(int)width/4,(int)y+(int)height/2,(int)width/2 ,(int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)(x+width-5),(int)y+5,(int)5,(int)height-15);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x,(int)y+5,(int)5,(int)height-15);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int)x+(int)width/2-(int)width/4,(int)y,(int)width/2,(int)height/2);
    }

    public void Collision()
    {
        for(int i=0;i<this.handler.object.size();i++)
        {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getID()==ObjectID.Block)
            {
                if(getBoundsTop().intersects(tempObject.getBounds()))       //TOP
                {
                    y = tempObject.getY() + 32;
                    velY = 0;
                }
                else
                {
                    falling = true;
                }
                if(getBoundsRight().intersects(tempObject.getBounds()))  //RIGHT
                {
                    x = tempObject.getX() - 32;
                    velX = 0;
                }
                if(getBoundsLeft().intersects(tempObject.getBounds()))  //LEFT
                {
                    x = tempObject.getX() + 32;
                    velX = 0;
                }
                if(getBounds().intersects(tempObject.getBounds()))  //BOTTOM
                {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                }
                else
                {
                    falling = true;
                }
            }
            if(tempObject.getID()==ObjectID.Coin)           //verific daca a atins o moneda
            {
                if(getBoundsRight().intersects(tempObject.getBounds()) ||getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    handler.object.remove(i);               //se colecteaza moneda si nu mai apare pe ecran deoarece e scoasa din handler
                    collectedCoins++;                       //se creste nr de monede colectate
                    System.out.println("coin collected");
                    if(collectedCoins == MAX_COINS)
                    {
                        System.out.println("Has collected all coins");
                    }
                }
            }
            if(tempObject.getID() == ObjectID.Flag)         //verific daca a atins un flag de final de nivel
            {
                if(getBoundsRight().intersects(tempObject.getBounds()) ||getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    //switch lvl
                    if(collectedCoins==MAX_COINS)           //trecerea la nivelul urmator se realizeaza doar daca a gasit toate monedele
                    {
                        handler.switchLevel();
                        collectedCoins = 0;
                    }

                }
            }

        }
    }
    public void die()                           //functie de terminare joc atunci cand moare jucatorul
    {
        System.out.println("A murit ");
        System.exit(1);
    }

}
