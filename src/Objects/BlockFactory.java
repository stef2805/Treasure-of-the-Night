package Objects;

import framework.GameObject;
import framework.ObjectID;

public class BlockFactory {
    public BlockFactory(){}
    public GameObject makeBlock(String BlockType, float x, float y, String type)
    {
        if(BlockType.compareTo("lava")==0)
        {
            return new LavaBlock(x,y,type,ObjectID.LavaBlock);
        } else if (BlockType.compareTo("normal")==0)
        {
            return new Block(x,y,type,ObjectID.Block);
        }
        return null;
    }

}
