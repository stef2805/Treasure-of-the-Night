package DBoperate;

public class Checkpoint {
    public int lvl;
    public int colectedCoins;
    public int X;
    public int Y;
    public Checkpoint(int lvl, int collectedCoins, int x, int y)
    {
        this.lvl = lvl;
        this.colectedCoins =collectedCoins;
        this.X = x;
        this.Y = y;
    }

    public void printCheckpoint()
    {
        System.out.println("Nivel: "+lvl+", monede colectate: "+colectedCoins+", X: "+X+", Y: "+Y);
    }
}
