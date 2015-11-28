package crud;

import data.BatchError;
import data.Meassure;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            case TEMPERATURE_INSIDE:
                break;
            case TEMPERATURE_OUTSIDE:
                break;
            case MOISTURE:
                break;
            case WATER_LEVEL:
                break;
        }
        
        return 0;
    }

    @Override
    public int storeError(BatchError e)
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

    @Override
    public List<BatchError> getDailyErrors(Date from, Date to)
    {
        List<BatchError> list = new ArrayList<>();
        try
        {
            Timestamp f = new Timestamp(from.getTime());
            Timestamp t = new Timestamp(to.getTime());
            String query = "SELECT * FROM Error WHERE created > " + f + " AND created < + " + t + " ORDER BY created";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next())
            {
                list.add(new BatchError(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getDate(6)));
            }
        } catch (SQLException ex)
        {
            
        }
        
        return list;
    }
}
