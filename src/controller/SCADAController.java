package controller;

import batchserver.IBatchExporter;
import data.Meassure;
import crud.HDCRUD;
import crud.ISCADAControllerCRUD;
import data.BatchError;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import order.Order;

public class SCADAController
{
    private int errorTries = 0;
    private int meassureTries = 0;
    private Order queuedOrder;

    public static List<IBatchExporter> clients = new CopyOnWriteArrayList<>();
    private ISCADAControllerCRUD crud = HDCRUD.get();
    private Queue<BatchError> errorCache;
    private Queue<Meassure> meassureCache;

    public void handleData(Meassure m)
    {
        new Thread(() ->
        {
            int i = crud.storeData(m);

            if (i == 0 && meassureTries < 3)
            {
                handleData(m);
            }
            else if (i == 0 && meassureTries > 2)
            {
                meassureCache.add(m);
            }
            else
            {
                synchronized (meassureCache)
                {
                    if (!meassureCache.isEmpty())
                    {
                        meassureCache.parallelStream().forEach((meassure) ->
                        {
                            crud.storeData(meassure);
                        });

                    }
                }
            }
        }).start();
    }

    public void handleError(BatchError e)
    {
        new Thread(() ->
        {
            int i = crud.storeError(e);

            if (i == 0 && errorTries < 3)
            {
                handleError(e);
            }
            else if (i == 0 && errorTries > 2)
            {
                errorCache.add(e);
            }
            else
            {
                synchronized (errorCache)
                {
                    if (!errorCache.isEmpty())
                    {
                        errorCache.parallelStream().forEach((error) ->
                        {
                            crud.storeError(error);
                        });
                        errorCache.clear();
                    }
                }
            }
        }).start();
    }

    /**
     * Not done!
     *
     * @param o
     * @return
     */
    public boolean startOrder(Order o)
    {
        AtomicBoolean b = new AtomicBoolean();

        new Thread(() ->
        {
            Set<IBatchExporter> readyClients = new CopyOnWriteArraySet<>();
            clients.parallelStream().forEach((c) ->
            {
                try
                {
                    if (c.isExecuting())
                    {
                        readyClients.add(c);
                    }
                } catch (RemoteException ex)
                {
                    System.out.println("I dont know what to do with this!!");
                }
            });

            if (!readyClients.isEmpty())
            {
                if (queuedOrder == null)
                {
                    queuedOrder = o;
                    b.set(true);
                }
                else
                {
                    b.set(false);
                }
            }
            else
            {
                AtomicInteger size = new AtomicInteger(o.getQuantity());
                while (size.get() > 0)
                {
                    readyClients.parallelStream().forEach((c) ->
                    {
                        try
                        {
                            c.startOrder(o.getRecipe().toHashMap());
                            size.set(size.get() - c.getCapacity());
                        } catch (RemoteException ex)
                        {
                            System.err.println("I dont know what to do with this!");
                        }
                    });
                }
                b.set(true);
            }
        }).start();

        return b.get();
    }

    public ISCADAControllerCRUD getCrud()
    {
        return crud;
    }
}
