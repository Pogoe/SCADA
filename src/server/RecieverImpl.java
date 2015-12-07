package server;

import Controller.IMesReciever;
import batchserver.IBatchExporter;
import controller.IBatchReciever;
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
        return SCADAController.clients.get(0).getCapacity();
    }

    @Override
    public Map<String, Integer> getCurrentCapacity(Order o) throws RemoteException
    {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        SCADAController.clients.parallelStream().forEach((c) ->
        {
            try
            {
                if (c.getCurrentOrder() == o.getRecipe().toHashMap())
                {
                    map.put(c.getName(), c.getCurrentCapacity());
                }
            } catch (RemoteException ex)
            {
                System.err.println("Still need to find a way to do this!");
            }
        });

        return map;
    }

    @Override
    public int getRemovedUnits(Order o) throws RemoteException
    {
        AtomicInteger removed = new AtomicInteger();
        SCADAController.clients.parallelStream().forEach((c) ->
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
    public void sendTemp1(Meassure temp) throws RemoteException
    {
        //Send to GUI
        controller.setTemp(temp.getValue());
    }

    @Override
    public void sendTemp2(Meassure temp) throws RemoteException
    {
        //Send to GUI
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMoist(Meassure moist) throws RemoteException
    {
        //Send to GUI
        controller.setMoist(moist.getValue());
    }

    @Override
    public void sendWaterLevel(Meassure level) throws RemoteException
    {
        //Send to GUI
        controller.setWaterLevel(level.getValue());
    }

    @Override
    public void sendError(BatchError error) throws RemoteException
    {
        controller.handleError(error);
    }

    @Override
    public void connectToServer(IBatchExporter bc) throws RemoteException
    {
        SCADAController.clients.add(bc);
    }
}