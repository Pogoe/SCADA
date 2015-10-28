package controller;

import crud.CRUD;
import crud.ISCADAControllerCRUD;
import java.util.Queue;

public class SCADAController
{
    private int tries = 0;

    private ISCADAControllerCRUD crud = CRUD.get();
    private Queue<ErrorTypes> errorCache;

    public void handleData(Meassure m)
    {
        
    }

    public void handleError(ErrorTypes e)
    {
        new Thread(() ->
        {
            int i = crud.storeError(e);
            
            if (i == 0 && tries < 3)
            {
                handleError(e);
            }
            else if (i == 0 && tries > 2)
            {
                errorCache.add(e);
            }
            else
            {
                synchronized(errorCache)
                {
                    if(!errorCache.isEmpty())
                    {
                        errorCache.stream().forEach((error) ->
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
