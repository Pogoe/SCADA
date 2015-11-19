package server;

import Controller.IMesReciever;
import batchserver.IBatchExporter;
import data.BatchError;
import data.Meassure;
import controller.SCADAController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.Order;

public class RecieverImpl extends UnicastRemoteObject implements IMesReciever, IBatchReciever
{

    private SCADAController controller = new SCADAController();

    public RecieverImpl() throws RemoteException
    {

    }

    @Override
    public boolean queueOrder(Order o) throws RemoteException
    {
        AtomicBoolean b = new AtomicBoolean();
        new Thread(() ->
        {
            b.set(controller.startOrder(o));
        }).start();

        return b.get();
    }

    @Override
    public List<BatchError> getDailyErrors(Date from, Date to) throws RemoteException
    {
        return controller.getCrud().getDailyErrors(from, to);
    }

    @Override
    public int getMaxCapacity() throws RemoteException
    {
        return controller.clients.get(0).getCapacity();
    }

    @Override
    public Map<String, Integer> getCurrentCapacity(Order o)
    {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        controller.clients.parallelStream().forEach((c) ->
        {
            try
            {
                if (c.getCurrentOrder() == o.getRecipe().toHashMap())
                {
                    map.put(c.getName(), c.getCurrentCapacity());
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RecieverImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return map;
    }

    @Override
    public int getRemovedUnits(Order o)
    {
        AtomicInteger removed = new AtomicInteger();
        controller.clients.parallelStream().forEach((c) ->
        {
            try
            {
                if (c.getCurrentOrder() == o.getRecipe().toHashMap())                
                {
                    removed.set(removed.get() + c.getRemovedUnits());
                }
            } catch (RemoteException ex)
            {
                System.err.println("aæsdofihasid");
            }
        });
        
        return removed.get();
    }

    @Override
    public void sendTemp1(Meassure temp)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendTemp2(Meassure temp)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMoist(Meassure moist)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendWaterLevel(Meassure level)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendError(BatchError error)
    {
        controller.handleError(error);
    }

    @Override
    public void connectToServer(IBatchExporter bc)
    {
        controller.clients.add(bc);
    }
}
