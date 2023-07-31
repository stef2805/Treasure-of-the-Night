package window;

public class EmptyBufferedImageException extends Exception              //exceptie aruncata atunci cand fisierul de imagine nu este gasit
{
    public EmptyBufferedImageException()
    {
        System.out.println("INVALID IMAGE");
    }
}
