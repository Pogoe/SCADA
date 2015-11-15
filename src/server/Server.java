package server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{
    public static final int PORT = 7000;
    public static final String NAME = "SCADA";
    
    public static void main(String[] args)
    {
        try
        {
            LocateRegistry.createRegistry(PORT).bind(NAME, new RecieverImpl());
        } catch(AccessException e)
        {
            System.err.println("Unable to bind object: " + e);
        } catch (RemoteException ex)
        {
            System.err.println("Remote communication error: " + ex);
        } catch (AlreadyBoundException exc)
        {
            System.err.println("Unable to bind object: " + exc);
        }
    }
}
