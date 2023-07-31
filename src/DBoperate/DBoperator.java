package DBoperate;

import java.sql.*;
public class DBoperator                             //este clasa responsabila de operatiunile cu baza de date
{
    private Connection con;
    private Statement stmt;
    public DBoperator(Connection c)
    {
        this.con = c;
        try
        {
            con.setAutoCommit(false);
            this.stmt = c.createStatement();
        }
        catch(Exception e)
        {
          System.out.println("Problema la constructor obiect DBoperator");
        }
    }
    public boolean isEmpty()
    {
        try
        {
            ResultSet rs = stmt.executeQuery( "select count(*) from GAME_INFO" );
            int rez = rs.getInt(1);
            if(rez>0)
                return false;
            if(rez==0)
                return true;
            con.commit();
        }
        catch(Exception e)
        {
            System.out.println("Problema la metoda  isEmpty");
        }
        return false;
    }
    public void addCheckpoint(Checkpoint cp)                //metoda care parseaza de la obiect checkpoint la string stocat in db
    {
        try
        {
            String comanda = "INSERT INTO GAME_INFO (LVL,COLECTED_COINS,X,Y)" +
                    "VALUES (" + Integer.toString(cp.lvl) + "," + Integer.toString(cp.colectedCoins) + "," + Integer.toString(cp.X) + "," + Integer.toString(cp.Y) + ")";
            stmt.executeUpdate(comanda);
            //stmt.close();
            con.commit();
        }
        catch(Exception e)
        {
            System.out.println("Problema la metoda de adaugare checkpoint");
        }
    }
    public Checkpoint getCheckpoint()                           //metoda care converteste sampurile savate in tabel la obiect checkpoint
    {
        try
        {
            ResultSet rs = stmt.executeQuery( "SELECT * FROM GAME_INFO;" );
            int lvl = rs.getInt("LVL");
            int  colectedCoins = rs.getInt("COLECTED_COINS");
            int X = rs.getInt("X");
            int Y = rs.getInt("Y");

            Checkpoint c = new Checkpoint(lvl,colectedCoins,X,Y);
            return c;
        }
        catch(Exception e)
        {
            System.out.println("Nu s-a putut face GET din baza de date -> void DB");
            return null;
        }
    }
    public void deleteCheckpoint()                                  //deoarece nu are rost sa stocam mai mult de un checkpoint, atuci cand stergem stergem tot din db
    {
        try
        {
            String comanda = "DELETE from GAME_INFO ";
            stmt.executeUpdate(comanda);
            con.commit();
        }
        catch(Exception e)
        {
            System.out.println("Problema la metoda de stergere checkpoint");
        }
    }
}
