package controller;

import data.Meassure;
import data.ErrorTypes;
import crud.HDCRUD;
import crud.ISCADAControllerCRUD;
import java.util.Queue;

public class SCADAController
{
    private int errorTries = 0;
    private int meassureTries = 0;

    private ISCADAControllerCRUD crud = HDCRUD.get();
    private Queue<ErrorTypes> errorCache;
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

    public void handleError(ErrorTypes e)
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
}
