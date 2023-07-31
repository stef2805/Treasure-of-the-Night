package Objects;

import framework.GameObject;
import framework.ObjectID;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.Game;
import window.Handler;

import java.awt.*;

public class Player extends GameObject
{

    private final float MAX_SPEED = 10;
    private float gravity = 0.3f;
    private float width = 32;
    private float height = 64;
    static public int collectedCoins = 0;
    private int MAX_COINS = 2;
    static private int lives = 3;
    static public boolean isAtacking = false;
    private boolean alive = true;
    private long deadTime = 0;
    private long atackTime = 0;


    private Handler handler;
    private Camera cam;
    private Animation playerWalk;
    private Animation playerJump;
    private Animation playerDie;
    private Animation playerIdle;
    private Animation playerAtack;
    private Boolean dangerPassed = true;
    private Font infoFont = new Font("Century Gothic", Font.ITALIC, 20);


    Texture tex = Game.getInstance();

    public Player(float x, float y, Handler handler, Camera cam, ObjectID id)
    {
        super(x,y,id);
        this.cam = cam;
        this.handler = handler;
        ///////initializare animatii//////////
        playerWalk = new Animation(5,tex.player[0],tex.player[1],tex.player[2],tex.player[3],tex.player[4],tex.player[5],tex.player[6],tex.player[7]);
        playerJump = new Animation(5,tex.player_jump[0],tex.player_jump[1],tex.player_jump[2],tex.player_jump[3],tex.player_jump[4],tex.player_jump[5],tex.player_jump[6],tex.player_jump[7]);
        playerDie = new Animation(12,tex.player_die[0],tex.player_die[1],tex.player_die[2],tex.player_die[3],tex.player_die[4],tex.player_die[5],tex.player_die[6],tex.player_die[7]);
        playerAtack = new Animation(9, tex.player_atack[0],tex.player_atack[1],tex.player_atack[2],tex.player_atack[3],tex.player_atack[4],tex.player_atack[5],tex.player_atack[6],tex.player_atack[7]);
        playerIdle = new Animation(15,tex.player_idle[0],tex.player_idle[1],tex.player_idle[2],tex.player_idle[3]);

    }
    @Override
    public void tick()
    {
        int oldX;
        oldX = (int)x;

        x+=velX;
        y+=velY;

        Game.Xbg = Game.Xbg + ((int)x - oldX)/2;        //mutare fundal cu jumatate din viteza din care se deplaseaza jucatorul

        if(falling || jumping)                          //daca sare sau este in picaj se ajuseaza viteza pe y
        {
            velY +=gravity;
            if(velY>MAX_SPEED)
            {
                velY = MAX_SPEED;                   // viteza pe y nu poate depasi pragul MAX_SPEED
            }
        }
        if(y>1000)
        {
            if(dangerPassed)                        //verific daca a trecut pericolul si scad o viata. Are rolul de a scadea o singura data o viata atunci cand se ruleaza animatia
            {
                lives--;
                dangerPassed = false;
            }
            alive = false;
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
        playerAtack.runAnimatin();
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(infoFont);
        g.drawString("Coins: "+ collectedCoins+"/2", (int)x-10, (int)y-140);
        g.drawString("Lives: "+ lives,(int)x-10, (int)y-120 );
        if(!alive)
        {
            Handler.DBop.deleteCheckpoint();
            Player.collectedCoins = 0;
            velX = 0;
            velY = 0;
            playerDie.drawAnimation(g, (int) x, (int) y, 48, 72);
            if(deadTime==0)                                                       //calcul moment initial al mortii
                deadTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();                        //calcul timp curent - timpul mortii
            if(currentTime - deadTime>1500)                                       //desenez o animatie de moarte timp de 1.5 sec
            {
                alive = true;                                                     //revine la viata
                if(lives<=0)
                    die();                                                        //daca a consumat toate vietile moare
                else
                    handler.switchLevel();                                        //altfel reia nivelul
                dangerPassed = true;                                              //se reseteaza dangerPassed si deadTime
                deadTime = 0;
            }
        }

        else if(isAtacking)                                                      //animatie de atac
        {
            if(facing == 1)
                playerAtack.drawAnimation(g, (int) x, (int) y, 48, 72);
            else
                playerAtack.drawAnimation(g, (int) x + 48, (int) y, -48, 72);
            if(atackTime==0)
                atackTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();                        //calculate the time needed to draw player atack animation
            if(currentTime - atackTime>1000)                                     //daca a trecut timpul de atac nu mai desenez animatia de atac
            {
                isAtacking = false;
                atackTime = 0;
            }
        }
        else
        {                                                                       //determinare si afisare animatie in fuctie de stare
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
                if(getBoundsTop().intersects(tempObject.getBounds()))      //TOP
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
                    if(dangerPassed)                            //daca atinge lava ii scad nr de viet si spun ca este in pericol, pentru a nu scadea vieti de mai multe ori
                    {
                        lives--;
                        dangerPassed = false;
                        alive = false;
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
                        Game.LEVEL++;                       //trec la nivelul urmator
                        handler.switchLevel();
                        collectedCoins = 0;                 //reset nr de monede pe niv urmator
                    }

                }
            }
            if(tempObject.getID() == ObjectID.Skeleton)         //verific daca a atins un flag de final de nivel
            {
                if(getBoundsRight().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    if(isAtacking)
                    {
                        handler.object.remove(tempObject);
                    }
                    else if(dangerPassed)
                    {
                        lives--;
                        dangerPassed = false;
                        alive = false;
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
                        if(isAtacking)
                        {
                            handler.object.remove(tempObject);
                        }
                        else if(dangerPassed)
                        {
                            lives--;
                            dangerPassed = false;
                            alive = false;
                        }

                    }
                }
            }
        }
    }

    public void die()                           //functie de terminare joc atunci cand moare jucatorul
    {
        Handler.DBop.deleteCheckpoint();
        System.out.println("A murit ");
        System.exit(1);
    }

}
