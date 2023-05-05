package framework;

import DBoperate.Checkpoint;
import Objects.Player;
import window.Game;
import window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;

public class KeyInput extends KeyAdapter
{
    Handler handler;
    public KeyInput(Handler handler)
    {
        this.handler = handler;
    }
    private int LastXPlayer = 0;
    private int LastYPlayer = 0;
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            Handler.DBop.addCheckpoint(new Checkpoint(Game.LEVEL, Player.collectedCoins,LastXPlayer,LastYPlayer));
            System.exit(1);
        }
        if (key == KeyEvent.VK_M) {
            Game.GAMEstate = 0;
        }
        if(Game.GAMEstate==1)
        {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player) {
                    LastXPlayer = (int)tempObject.getX();
                    LastYPlayer = (int)tempObject.getY();
                    if (key == KeyEvent.VK_D) {
                        tempObject.setVelX(5);
                    }
                    if (key == KeyEvent.VK_A) {
                        tempObject.setVelX(-5);
                    }
                    if (key == KeyEvent.VK_W && !tempObject.isJumping()) {
                        tempObject.setJumping(true);
                        tempObject.setVelY(-10);
                    }

                }
            }
        }
        else
        {
            if(key == KeyEvent.VK_SPACE){
                if(Game.currentChoice == 0) {
                    Game.GAMEstate = 1;
                    Game.LEVEL = 0;
                    Player.collectedCoins = 0;
                    Handler.DBop.deleteCheckpoint();
                    handler.switchLevel();
                }
                if(Game.currentChoice == 1) {
                    Game.GAMEstate = 1;
                }
                if(Game.currentChoice == 2) {
                    System.exit(0);
                }
            }
            if(key == KeyEvent.VK_W) {
                Game.currentChoice--;
                if(Game.currentChoice == -1) {
                    Game.currentChoice = Game.options.length - 1;
                }
            }
            if(key == KeyEvent.VK_S) {
                Game.currentChoice++;
                if(Game.currentChoice == Game.options.length) {
                    Game.currentChoice = 0;
                }
            }
        }

    }
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        for(int i=0;i<handler.object.size();i++)
        {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getID()==ObjectID.Player)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObject.setVelX(0);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObject.setVelX(0);
                }

            }
        }
    }
}
