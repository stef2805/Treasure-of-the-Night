package framework;

import window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture
{
    SpriteSheet bs,ps;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[16];
    public BufferedImage[] player_jump = new BufferedImage[8];
    public BufferedImage[] player_die = new BufferedImage[8];
    public BufferedImage[] coin = new BufferedImage[9];

    public Texture()
    {
        BufferedImageLoader loader= new BufferedImageLoader();

        player_sheet = loader.loadImage("/erouu.png");
        block_sheet = loader.loadImage("/block_sheet.png");

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        
        getTextures();
    }
    
    private void getTextures()
    {
        block[0] = bs.grabImage(1,1,32,32);     //dirt
        block[1] = bs.grabImage(2,1,32,32);     //grass

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

        coin[0] = bs.grabImage(1,2,32,32);          //coin animation
        coin[1] = bs.grabImage(2,2,32,32);
        coin[2] = bs.grabImage(3,2,32,32);
        coin[3] = bs.grabImage(4,2,32,32);
        coin[4] = bs.grabImage(5,2,32,32);
        coin[5] = bs.grabImage(6,2,32,32);
        coin[6] = bs.grabImage(1,3,32,32);
        coin[7] = bs.grabImage(2,3,32,32);
        coin[8] = bs.grabImage(3,3,32,32);
    }


}
