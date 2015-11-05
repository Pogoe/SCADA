package crud;

import controller.ErrorTypes;
import controller.Meassure;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD implements IBatchCRUD, ISCADAControllerCRUD, IMESCRUD
{
    public static CRUD instance;
    private Connection conn;

    private CRUD()
    {
        connect();
    }
    
    public static CRUD get()
    {
        if(instance == null)
        {
            instance = new CRUD();
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
    public void storeData(Meassure m)
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
    public void storeTask()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
