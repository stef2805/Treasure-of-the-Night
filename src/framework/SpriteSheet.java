package framework;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage image;
    public SpriteSheet(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage grabImage(int column, int row, int width, int height)
    {
        BufferedImage img = image.getSubimage((column*width)-width, (row*height)-height,width,height);
        return img;
    }
    public BufferedImage grabImageWithOffsetLeft(int column, int row, int width, int height, int leftOffset)
    {
        BufferedImage img = image.getSubimage((column*width)-width+leftOffset, (row*height)-height,width,height);
        return img;
    }

}
