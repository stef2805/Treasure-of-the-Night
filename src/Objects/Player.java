package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.Game;
import window.Handler;

import java.awt.*;

import static jdk.nashorn.internal.objects.NativeMath.abs;

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
    private Animation playerIdle;


    Texture tex = Game.getInstance();

    public Player(float x, float y, Handler handler, Camera cam, ObjectID id)
    {
        super(x,y,id);
        this.cam = cam;
        this.handler = handler;
        playerWalk = new Animation(5,tex.player[0],tex.player[1],tex.player[2],tex.player[3],tex.player[4],tex.player[5],tex.player[6],tex.player[7]);
        playerJump = new Animation(5,tex.player_jump[0],tex.player_jump[1],tex.player_jump[2],tex.player_jump[3],tex.player_jump[4],tex.player_jump[5],tex.player_jump[6],tex.player_jump[7]);
        playerDie = new Animation(12,tex.player_die[0],tex.player_die[1],tex.player_die[2],tex.player_die[3],tex.player_die[4],tex.player_die[5],tex.player_die[6],tex.player_die[7]);
        playerIdle = new Animation(15,tex.player_idle[0],tex.player_idle[1],tex.player_idle[2],tex.player_idle[3]);
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
        if (velX > 0)
            facing = 1;
        if (velX < 0)
            facing = -1;

        Collision();

        playerWalk.runAnimatin();
        playerJump.runAnimatin();
        playerDie.runAnimatin();
        playerIdle.runAnimatin();
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
            if (jumping && facing == 1) {
                playerJump.drawAnimation(g, (int) x, (int) y, 48, 72);
            } else if (jumping && facing == -1) {
                playerJump.drawAnimation(g, (int) x + 48, (int) y, -48, 72);

            } else {
                if (velX > 0) {
                    playerWalk.drawAnimation(g, (int) x, (int) y, 48, 72);
                } else if (velX < 0)
                    playerWalk.drawAnimation(g, (int) x + 48, (int) y, -48, 72);
                else {
                    if (facing == 1)
                        playerIdle.drawAnimation(g,(int)x,(int)y,48,72);
                    else
                        playerIdle.drawAnimation(g,(int)x+48,(int)y,-48,72);
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
            if(tempObject.getID()==ObjectID.LavaBlock)           //verific daca a atins lava
            {
                if(getBounds().intersects(tempObject.getBounds()))
                {
                    alive = false;
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

            if(tempObject.getID() == ObjectID.Bat)                          //daca am un liliac
            {
                if(getBoundsRight().intersects(tempObject.getBounds()))     //verific daca a intrat in zona liliacului
                {
                    if(x >= tempObject.getX())                              //mut liliacul spre jucator
                    {
                        tempObject.setFacing(1);
                        tempObject.setX(tempObject.getX()+1);
                        //System.out.println("X+5");
                    }
                    else {
                        tempObject.setFacing(-1);
                        tempObject.setX(tempObject.getX() - 1);
                    }

                    if(y >= tempObject.getY())
                    {
                        tempObject.setY(tempObject.getY()+1);
                        //System.out.println("Y+5");
                    }
                    else
                        tempObject.setY(tempObject.getY()-1);

                    if(Math.abs(x- tempObject.getX())<20 && Math.abs(y- tempObject.getY())<20)      //daca am contact de aproape cu liliacul
                    {
                       alive = false;
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
