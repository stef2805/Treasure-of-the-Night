package framework;

import window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture
{
    SpriteSheet bs, ps, fs, es;          //bs = block sheet;  ps = player sheet; fs = flag sheet; es = enemies sheet
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage flag_sheet = null;
    private BufferedImage enemies_sheet = null;
    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] lavaBlock = new BufferedImage[6];
    public BufferedImage[] player = new BufferedImage[16];
    public BufferedImage[] player_jump = new BufferedImage[8];
    public BufferedImage[] player_die = new BufferedImage[8];
    public BufferedImage[] player_atack = new BufferedImage[8];


    public BufferedImage[] player_idle = new BufferedImage[4];
    public BufferedImage[] coin = new BufferedImage[9];
    public BufferedImage[] flagMoving = new BufferedImage[5];
    public BufferedImage[] batFlying = new BufferedImage[8];

    public Texture()
    {
        BufferedImageLoader loader= new BufferedImageLoader();

        player_sheet = loader.loadImage("/erouu.png");
        block_sheet = loader.loadImage("/block_sheet.png");
        flag_sheet = loader.loadImage("/flagg.png");
        enemies_sheet = loader.loadImage("/enemies.png");

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        fs = new SpriteSheet(flag_sheet);
        es = new SpriteSheet(enemies_sheet);
        
        getTextures();
    }
    
    private void getTextures()
    {
        block[0] = bs.grabImage(1,1,32,32);     //dirt
        block[1] = bs.grabImage(2,1,32,32);     //grass

        lavaBlock[0] = bs.grabImage(3,1,32,32); //lava anim 1
        lavaBlock[1] = bs.grabImage(4,1,32,32); //lava anim 2
        lavaBlock[2] = bs.grabImage(5,1,32,32); //lava anim 3
        lavaBlock[3] = bs.grabImage(6,1,32,32); //lava static 1
        lavaBlock[4] = bs.grabImage(7,1,32,32); //lava static 2
        lavaBlock[5] = bs.grabImage(8,1,32,32); //lava static 3

        player[0] = ps.grabImage(1,4,32,64);    //player walking 1
        player[1] = ps.grabImage(2,4,32,64);    //player walking 2
        player[2] = ps.grabImage(3,4,32,64);    //player walking 3
        player[3] = ps.grabImage(4,4,32,64);    //player walking 4
        player[4] = ps.grabImage(5,4,32,64);    //player walking 5
        player[5] = ps.grabImage(6,4,32,64);    //player walking 6
        player[6] = ps.grabImage(7,4,32,64);    //player walking 7
        player[7] = ps.grabImage(8,4,32,64);    //player walking 8

        player_jump[0] = ps.grabImage(1,6,32,64);    //player jumping 1
        player_jump[1] = ps.grabImage(2,6,32,64);    //player jumping 2
        player_jump[2] = ps.grabImage(3,6,32,64);    //player jumping 3
        player_jump[3] = ps.grabImage(4,6,32,64);    //player jumping 4
        player_jump[4] = ps.grabImage(5,6,32,64);    //player jumping 5
        player_jump[5] = ps.grabImage(6,6,32,64);    //player jumping 6
        player_jump[6] = ps.grabImage(7,6,32,64);    //player jumping 7
        player_jump[7] = ps.grabImage(8,6,32,64);    //player jumping 8

        player_die[0] = ps.grabImage(1,8,32,64);     //player dead
        player_die[1] = ps.grabImage(2,8,32,64);
        player_die[2] = ps.grabImage(3,8,32,64);
        player_die[3] = ps.grabImage(4,8,32,64);
        player_die[4] = ps.grabImage(5,8,32,64);
        player_die[5] = ps.grabImage(6,8,32,64);
        player_die[6] = ps.grabImage(7,8,32,64);
        player_die[7] = ps.grabImage(8,8,32,64);

        player_atack[0] = ps.grabImage(1,9,32,64);     //player atack
        player_atack[1] = ps.grabImage(2,9,32,64);
        player_atack[2] = ps.grabImage(3,9,32,64);
        player_atack[3] = ps.grabImage(4,9,32,64);
        player_atack[4] = ps.grabImage(5,9,32,64);
        player_atack[5] = ps.grabImage(6,9,32,64);
        player_atack[6] = ps.grabImage(7,9,32,64);
        player_atack[7] = ps.grabImage(8,9,32,64);

        player_idle[0] = ps.grabImage(1,1,32,64);
        player_idle[1] = ps.grabImage(2,1,32,64);
        player_idle[2] = ps.grabImage(1,2,32,64);
        player_idle[3] = ps.grabImage(2,2,32,64);



        coin[0] = bs.grabImage(1,2,32,32);          //coin animation
        coin[1] = bs.grabImage(2,2,32,32);
        coin[2] = bs.grabImage(3,2,32,32);
        coin[3] = bs.grabImage(4,2,32,32);
        coin[4] = bs.grabImage(5,2,32,32);
        coin[5] = bs.grabImage(6,2,32,32);
        coin[6] = bs.grabImage(1,3,32,32);
        coin[7] = bs.grabImage(2,3,32,32);
        coin[8] = bs.grabImage(3,3,32,32);

        flagMoving[0] = fs.grabImage(1,1,32,32);
        flagMoving[1] = fs.grabImage(2,1,32,32);
        flagMoving[2] = fs.grabImage(3,1,32,32);
        flagMoving[3] = fs.grabImage(4,1,32,32);
        flagMoving[4] = fs.grabImage(5,1,32,32);

        batFlying[3] = es.grabImage(4,1,26,26);
        batFlying[4] = es.grabImage(5,1,26,26);
        batFlying[5] = es.grabImage(6,1,26,26);
        batFlying[6] = es.grabImage(7,1,26,26);
        batFlying[7] = es.grabImage(8,1,26,26);


    }


}
