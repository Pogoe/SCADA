package crud;

import data.ErrorTypes;
import data.Meassure;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HDCRUD implements ISCADAControllerCRUD, IMESCRUD
{
    public static HDCRUD instance;
    private Connection conn;

    private HDCRUD()
    {
        connect();
    }
    
    public static HDCRUD get()
    {
        if(instance == null)
        {
            instance = new HDCRUD();
        }
        return instance;
    }

    private void connect()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Semesterprojekt";
            String user = "postgres";
            String pass = "u7e98d22";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex)
        {
            System.err.println(ex);
        }

    }

    @Override
    public int storeData(Meassure m)
    {
        switch(m.getType())
        {
            case TEMPERATURE:
                break;
            case MOISTURE:
                break;
            case WATER_LEVEL:
                break;
        }
        
        return 0;
    }

    @Override
    public int storeError(ErrorTypes e)
    {
        int i = 0;
        try
        {
            String query = "INSERT INTO ... VALUES(" + ");";
            Statement st = conn.createStatement();
            i = st.executeUpdate(query);
            
            return i;
        } catch (SQLException ex)
        {
            System.err.println(ex);
        }
        
        return i;
    }

    @Override
    public void storeOrder()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
